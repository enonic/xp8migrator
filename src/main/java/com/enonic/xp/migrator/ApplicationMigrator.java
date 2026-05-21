package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enonic.xp.app.ApplicationDescriptor;
import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yaml.ApplicationDescriptorYml;
import com.enonic.xp.xml.parser.XmlApplicationParser;

public class ApplicationMigrator
    extends DescriptorMigrator
{
    private static final Logger LOG = LoggerFactory.getLogger( ApplicationMigrator.class );

    private Path migratedSvgSource;

    protected ApplicationMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public void migrate()
    {
        super.migrate();
        copyApplicationSvg();
    }

    private void copyApplicationSvg()
    {
        final Path svgSource = resourcesDir.resolve( "application.svg" );
        if ( !Files.exists( svgSource ) )
        {
            return;
        }

        final Path svgTarget = resourcesDir.resolve( "enonic.svg" );
        if ( Files.exists( svgTarget ) )
        {
            final OnExistsStrategy strategy = FileUtils.resolveStrategy( onExists, svgTarget );
            if ( strategy == OnExistsStrategy.skip )
            {
                return;
            }
        }

        try
        {
            Files.copy( svgSource, svgTarget, StandardCopyOption.REPLACE_EXISTING );
            migratedSvgSource = svgSource;
        }
        catch ( IOException e )
        {
            LOG.warn( "Failed to copy {} to {}: {}", svgSource, svgTarget, e.getMessage() );
        }
    }

    @Override
    public List<Path> additionalMigratedSources()
    {
        return migratedSvgSource == null ? List.of() : List.of( migratedSvgSource );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final ApplicationDescriptor.Builder appDescriptorBuilder = ApplicationDescriptor.create();

        final XmlApplicationParser parser = new XmlApplicationParser();

        parser.appDescriptorBuilder( appDescriptorBuilder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final ApplicationDescriptor descriptor = appDescriptorBuilder.build();

        final Properties gradleProperties = loadGradleProperties();

        return new ApplicationDescriptorYml( descriptor, gradleProperties );
    }

    @Override
    public Path resolveYmlFilePath( final Path source )
    {
        return resourcesDir.resolve( "enonic.yaml" );
    }

    private Properties loadGradleProperties()
        throws IOException
    {
        final Path gradlePropertiesPath = resourcesDir.resolve( "../../../gradle.properties" ).normalize();
        final Properties properties = new Properties();
        if ( Files.exists( gradlePropertiesPath ) )
        {
            try (Reader reader = Files.newBufferedReader( gradlePropertiesPath ))
            {
                properties.load( reader );
            }
        }
        return properties;
    }
}
