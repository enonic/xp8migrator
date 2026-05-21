package com.enonic.xp.migrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Callable;

import io.micronaut.configuration.picocli.PicocliRunner;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.enonic.xp.app.ApplicationKey;

@Command(name = "migrator", description = "Migrates XP project descriptors to XP8 format.", mixinStandardHelpOptions = true)
public class Main
    implements Callable<Integer>
{
    @Parameters(index = "0", arity = "0..1", defaultValue = ".", description = "Path to the project directory. Defaults to current directory.")
    private Path projectPath;

    @Option(names = {"-a", "--app-name"}, description = "Application name. Resolved from gradle.properties if not provided.")
    private String appName;

    @Option(names = {"-x",
        "--delete-migrated-xml"}, description = "Delete original XML/SVG files and the now-redundant site/ and admin/widgets/ directories after successful migration.")
    private boolean deleteMigratedXml;

    @Option(names = {"-e",
        "--on-exists"}, defaultValue = "ask", description = "Strategy when a target file already exists: ask (default), overwrite, skip.")
    private OnExistsStrategy onExists;

    @Override
    public Integer call()
    {
        final ApplicationKey currentApplication = resolveApplicationKey();

        System.out.printf( "Start the migration of all descriptors for the project: %s%n", projectPath );
        System.out.printf( "Resolved applicationKey: %s%n", currentApplication );

        final MigrationResult migrationResult = new MigrationExecutor( projectPath, currentApplication, onExists ).migrate();

        migrationResult.getEntries()
            .forEach( ( entry ) -> System.out.println( ( entry.migrated() ? "SUCCESS" : "FAILURE" ) + " " + entry.source() ) );

        if ( deleteMigratedXml )
        {
            deleteMigratedSourceFiles( migrationResult );
        }

        System.out.println( "Migration completed successfully." );
        return 0;
    }

    private static void deleteMigratedSourceFiles( final MigrationResult migrationResult )
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

        migrationResult.getConsumedSourceDirs().forEach( Main::deleteRecursively );
    }

    private static void deleteRecursively( final Path dir )
    {
        if ( !Files.exists( dir ) )
        {
            return;
        }
        try
        {
            Files.walkFileTree( dir, new SimpleFileVisitor<>()
            {
                @Override
                public FileVisitResult visitFile( final Path file, final BasicFileAttributes attrs )
                    throws IOException
                {
                    Files.delete( file );
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory( final Path subDir, final IOException exc )
                    throws IOException
                {
                    if ( exc != null )
                    {
                        throw exc;
                    }
                    Files.delete( subDir );
                    return FileVisitResult.CONTINUE;
                }
            } );
            System.out.println( "DELETED " + dir );
        }
        catch ( IOException e )
        {
            System.err.println( "Failed to delete " + dir + ": " + e.getMessage() );
        }
    }

    private ApplicationKey resolveApplicationKey()
    {
        if ( appName != null )
        {
            return ApplicationKey.from( appName );
        }

        final Path gradlePropertiesPath = projectPath.resolve( "gradle.properties" );
        if ( Files.exists( gradlePropertiesPath ) )
        {
            final Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream( gradlePropertiesPath.toFile() ))
            {
                props.load( fis );
                final String name = props.getProperty( "appName" );
                if ( name != null && !name.isEmpty() )
                {
                    return ApplicationKey.from( name );
                }
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( "Failed to read the gradle.properties file", e );
            }
        }

        System.out.print( "App name not found. Enter app name: " );
        final Scanner scanner = new Scanner( System.in );
        final String name = scanner.nextLine().trim();
        if ( name.isEmpty() )
        {
            System.err.println( "App name is required." );
            System.exit( 1 );
        }
        return ApplicationKey.from( name );
    }

    static void main( String[] args )
    {
        PicocliRunner.execute( Main.class, args );
    }
}
