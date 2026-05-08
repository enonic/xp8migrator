package com.enonic.xp.migrator;

import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MigratorTest
{
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

    private static Path copyTestAppToTempDir()
        throws IOException
    {
        final Path sourceAppDir = Path.of( System.getProperty( "testAppDir" ) );
        final Path appDir = Files.createTempDirectory( "xp8migrator-test-" );
        copyDirectory( sourceAppDir, appDir );
        return appDir;
    }

    private static void copyDirectory( final Path source, final Path target )
        throws IOException
    {
        try (Stream<Path> stream = Files.walk( source ))
        {
            stream.forEach( sourcePath -> {
                try
                {
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
                catch ( IOException e )
                {
                    throw new UncheckedIOException( e );
                }
            } );
        }
    }
}
