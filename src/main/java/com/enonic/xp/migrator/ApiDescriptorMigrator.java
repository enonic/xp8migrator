package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.api.ApiDescriptor;
import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.yaml.ApiDescriptorYml;
import com.enonic.xp.xml.parser.XmlApiDescriptorParser;

public class ApiDescriptorMigrator
    extends DescriptorMigrator
{
    protected ApiDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final ApiDescriptor.Builder builder = ApiDescriptor.create();
        builder.key( DescriptorKey.from( currentApplication, "_TEMP_" ) );

        final XmlApiDescriptorParser parser = new XmlApiDescriptorParser( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final ApiDescriptor descriptor = builder.build();

        return new ApiDescriptorYml( descriptor );
    }
}
