package com.enonic.xp.migrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.Callable;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.enonic.xp.app.ApplicationKey;

@Command( name = "migrator", description = "Migrates XP project descriptors to XP8 format." )
public class Main
    implements Callable<Integer>
{
    @Parameters( index = "0", description = "Path to the project directory." )
    private Path projectPath;

    @Parameters( index = "1", arity = "0..1", description = "Application name. Resolved from gradle.properties if not provided." )
    private String appName;

    @Option( names = {"-x", "--delete-migrated-xml"}, description = "Delete original XML files after successful migration." )
    private boolean deleteMigratedXml;

    @Override
    public Integer call()
    {
        final ApplicationKey currentApplication = appName != null ? ApplicationKey.from( appName ) : resolveApplicationKey( projectPath );

        System.out.printf( "Start the migration of all descriptors for the project: %s%n", projectPath );
        System.out.printf( "Resolved applicationKey: %s%n", currentApplication );

        final MigrationResult migrationResult = new MigrationExecutor( projectPath, currentApplication ).migrate();

        migrationResult.getEntries().forEach(
            ( entry ) -> System.out.println( ( entry.migrated() ? "SUCCESS" : "FAILURE" ) + " " + entry.source() ) );

        if ( deleteMigratedXml )
        {
            deleteMigratedXmlFiles( migrationResult );
        }

        System.out.println( "Migration completed successfully." );
        return 0;
    }

    private static void deleteMigratedXmlFiles( final MigrationResult migrationResult )
    {
        migrationResult.getEntries().stream().filter( MigrationResult.MigrationEntry::migrated ).forEach( entry -> {
            try
            {
                Files.deleteIfExists( entry.source() );
                System.out.println( "DELETED " + entry.source() );
            }
            catch ( IOException e )
            {
                System.err.println( "Failed to delete " + entry.source() + ": " + e.getMessage() );
            }
        } );
    }

    private static ApplicationKey resolveApplicationKey( final Path projectPath )
    {
        final Path gradlePropertiesPath = projectPath.resolve( "gradle.properties" );
        if ( Files.notExists( gradlePropertiesPath ) )
        {
            System.err.println( "File gradle.properties not found." );
            System.exit( 1 );
        }

        final Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream( gradlePropertiesPath.toFile() ))
        {
            props.load( fis );

            final String appName = props.getProperty( "appName" );
            if ( appName == null || appName.isEmpty() )
            {
                System.err.println( "Missed the \"appName\" property in the gradle.properties file." );
                System.exit( 1 );
            }

            return ApplicationKey.from( appName );
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( "Failed to read the gradle.properties file", e );
        }
    }

    public static void main( String[] args )
    {
        PicocliRunner.execute( Main.class, args );
    }
}
