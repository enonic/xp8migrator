package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.enonic.xp.app.ApplicationDescriptor;
import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yaml.ApplicationDescriptorYml;
import com.enonic.xp.xml.parser.XmlApplicationParser;

public class ApplicationMigrator
    extends DescriptorMigrator
{
    protected ApplicationMigrator( final MigrationParams params )
    {
        super( params );
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
