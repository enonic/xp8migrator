package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.admin.tool.AdminToolDescriptor;
import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.yaml.AdminToolDescriptorYml;
import com.enonic.xp.xml.parser.XmlAdminToolDescriptorParser;

public class AdminToolDescriptorMigrator
    extends DescriptorMigrator
{
    protected AdminToolDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final AdminToolDescriptor.Builder builder = AdminToolDescriptor.create().key( DescriptorKey.from( currentApplication, "_TEMP_" ) );

        final XmlAdminToolDescriptorParser parser = new XmlAdminToolDescriptorParser();

        parser.currentApplication( currentApplication );
        parser.builder( builder );
        parser.source( Files.readString( source ) );
        parser.parse();

        final AdminToolDescriptor descriptor = builder.build();

        return new AdminToolDescriptorYml( currentApplication, descriptor );
    }
}
