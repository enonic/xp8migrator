package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

final class FileUtils
{
    static void copyDirExcludingXml( final Path source, final Path target, final AtomicReference<OnExistsStrategy> onExists )
    {
        copyDirExcludingXml( source, target, Set.of(), onExists );
    }

    static void copyDirExcludingXml( final Path source, final Path target, final Set<Path> excludedDirs,
                                     final AtomicReference<OnExistsStrategy> onExists )
    {
        try ( var paths = Files.walk( source ) )
        {
            paths.forEach( sourcePath -> {
                if ( sourcePath.toString().endsWith( ".xml" ) )
                {
                    return;
                }
                if ( excludedDirs.stream().anyMatch( sourcePath::startsWith ) )
                {
                    return;
                }
                final Path relativePath = source.relativize( sourcePath );
                final Path targetPath = target.resolve( relativePath );
                try
                {
                    if ( Files.isDirectory( sourcePath ) )
                    {
                        Files.createDirectories( targetPath );
                    }
                    else
                    {
                        Files.createDirectories( targetPath.getParent() );
                        if ( Files.exists( targetPath ) )
                        {
                            switch ( resolveStrategy( onExists, targetPath ) )
                            {
                                case overwrite:
                                    break;
                                case skip:
                                    return;
                                default:
                                    throw new IllegalStateException( "Unexpected strategy" );
                            }
                        }
                        Files.copy( sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING );
                        if ( sourcePath.toString().endsWith( ".yaml" ) )
                        {
                            Files.delete( sourcePath );
                        }
                    }
                }
                catch ( IOException e )
                {
                    throw new UncheckedIOException( e );
                }
            } );
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( e );
        }
    }

    static OnExistsStrategy resolveStrategy( final AtomicReference<OnExistsStrategy> onExists, final Path targetPath )
    {
        final OnExistsStrategy current = onExists.get();
        if ( current != OnExistsStrategy.ask )
        {
            return current;
        }
        System.out.printf( "File already exists: %s. Overwrite? (y)es / (n)o / (Y)es to all / (N)o to all: ", targetPath );
        final Scanner scanner = new Scanner( System.in );
        final String answer = scanner.nextLine().trim();
        return switch ( answer )
        {
            case "y" -> OnExistsStrategy.overwrite;
            case "Y" ->
            {
                onExists.set( OnExistsStrategy.overwrite );
                yield OnExistsStrategy.overwrite;
            }
            case "N" ->
            {
                onExists.set( OnExistsStrategy.skip );
                yield OnExistsStrategy.skip;
            }
            default -> OnExistsStrategy.skip;
        };
    }
}
