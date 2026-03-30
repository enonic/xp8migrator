package com.enonic.xp.migrator;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MigratorTest
{
    @Test
    public void testMigrator()
    {
        Path appDir = Path.of( System.getProperty( "testAppDir" ) );

        int exitCode = new CommandLine( new Main() ).execute( appDir.toString() );
        assertEquals( 0, exitCode );
    }
}
