package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.yaml.ServiceDescriptorYml;
import com.enonic.xp.service.ServiceDescriptor;
import com.enonic.xp.xml.parser.XmlServiceDescriptorParser;

public class ServiceDescriptorMigrator
    extends DescriptorMigrator
{
    protected ServiceDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final ServiceDescriptor.Builder builder = ServiceDescriptor.create().key( DescriptorKey.from( currentApplication, "_TEMP_" ) );

        final XmlServiceDescriptorParser parser = new XmlServiceDescriptorParser();

        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) ).parse();

        final ServiceDescriptor descriptor = builder.build();

        return new ServiceDescriptorYml( descriptor );
    }
}
