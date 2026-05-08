package com.enonic.xp.migrator;

import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MigratorTest
{
    @TempDir
    Path tempDir;

    @Test
    public void testMigrator()
        throws IOException
    {
        final Path appDir = copyTestAppToTempDir();

        final int exitCode = new CommandLine( new Main() ).execute( "-e", "overwrite", appDir.toString() );
        assertEquals( 0, exitCode );
    }

    @Test
    public void testMigratorDeleteMigratedXml()
        throws IOException
    {
        final Path appDir = copyTestAppToTempDir();

        final int exitCode = new CommandLine( new Main() ).execute( "-x", "-e", "overwrite", appDir.toString() );

        assertEquals( 0, exitCode );
        assertFalse( Files.exists( appDir.resolve( "src/main/resources/application.xml" ) ) );
        assertTrue( Files.exists( appDir.resolve( "src/main/resources/application.yaml" ) ) );
    }

    private Path copyTestAppToTempDir()
        throws IOException
    {
        final Path sourceAppDir = Path.of( System.getProperty( "testAppDir" ) );
        final Path appDir = tempDir.resolve( "myapp" );
        copyDirectory( sourceAppDir, appDir );
        return appDir;
    }

    private static void copyDirectory( final Path source, final Path target )
        throws IOException
    {
        try (Stream<Path> stream = Files.walk( source ))
        {
            final Iterator<Path> iterator = stream.iterator();
            while ( iterator.hasNext() )
            {
                final Path sourcePath = iterator.next();
                final Path relative = source.relativize( sourcePath );
                final Path targetPath = target.resolve( relative );
                if ( Files.isDirectory( sourcePath ) )
                {
                    Files.createDirectories( targetPath );
                }
                else
                {
                    Files.createDirectories( targetPath.getParent() );
                    Files.copy( sourcePath, targetPath );
                }
            }
        }
    }
}
