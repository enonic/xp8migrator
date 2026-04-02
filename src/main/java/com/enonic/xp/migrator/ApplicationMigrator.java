package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        return new ApplicationDescriptorYml( descriptor );
    }
}
