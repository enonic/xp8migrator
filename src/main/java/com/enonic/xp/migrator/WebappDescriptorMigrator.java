package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yaml.WebappDescriptorYml;
import com.enonic.xp.webapp.WebappDescriptor;
import com.enonic.xp.xml.parser.XmlWebappDescriptorParser;

public class WebappDescriptorMigrator
    extends DescriptorMigrator
{
    protected WebappDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final WebappDescriptor.Builder builder = WebappDescriptor.create();
        builder.applicationKey( currentApplication );

        final XmlWebappDescriptorParser parser = new XmlWebappDescriptorParser();

        parser.descriptorBuilder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final WebappDescriptor descriptor = builder.build();

        return new WebappDescriptorYml( currentApplication, descriptor );
    }
}
