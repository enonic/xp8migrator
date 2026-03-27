package com.enonic.xp.migrator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MigratorTest
{
    @Test
    public void testMigrator()
        throws IOException, URISyntaxException
    {
        Path appDir = Paths.get( getClass().getClassLoader().getResource( "myapp" ).toURI() );
        Path migratorDir = appDir.resolve( ".." ).resolve( "myappMigrated" );

        if ( Files.exists( migratorDir ) )
        {
            deleteRecursively( migratorDir );
        }

        Files.walkFileTree( appDir, new SimpleFileVisitor<>()
        {
            @Override
            public FileVisitResult preVisitDirectory( Path dir, BasicFileAttributes attrs )
                throws IOException
            {
                Path targetDir = migratorDir.resolve( appDir.relativize( dir ) );
                Files.createDirectories( targetDir );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile( Path file, BasicFileAttributes attrs )
                throws IOException
            {
                Files.copy( file, migratorDir.resolve( appDir.relativize( file ) ), StandardCopyOption.REPLACE_EXISTING );
                return FileVisitResult.CONTINUE;
            }
        } );

        int exitCode = new CommandLine( new Main() ).execute( migratorDir.toString() );
        assertEquals( 0, exitCode );
    }

    private void deleteRecursively( Path path )
        throws IOException
    {
        Files.walkFileTree( path, new SimpleFileVisitor<>()
        {
            @Override
            public FileVisitResult visitFile( Path file, BasicFileAttributes attrs )
                throws IOException
            {
                Files.delete( file );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory( Path dir, IOException exc )
                throws IOException
            {
                Files.delete( dir );
                return FileVisitResult.CONTINUE;
            }
        } );
    }
}
