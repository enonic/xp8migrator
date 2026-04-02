package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

final class FileUtils
{
    static void copyDirExcludingXml( final Path source, final Path target )
    {
        copyDirExcludingXml( source, target, Set.of() );
    }

    static void copyDirExcludingXml( final Path source, final Path target, final Set<Path> excludedDirs )
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
                        Files.copy( sourcePath, targetPath );
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
}
