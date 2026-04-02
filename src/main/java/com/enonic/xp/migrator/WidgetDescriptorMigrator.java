package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.admin.widget.WidgetDescriptor;
import com.enonic.xp.admin.widget.XmlWidgetDescriptorParser;
import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.yaml.WidgetDescriptorYml;

public class WidgetDescriptorMigrator
    extends DescriptorMigrator
{
    protected WidgetDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final WidgetDescriptor.Builder builder = WidgetDescriptor.create();
        builder.key( DescriptorKey.from( currentApplication, "_TEMP_" ) );

        final XmlWidgetDescriptorParser parser = new XmlWidgetDescriptorParser();
        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final WidgetDescriptor descriptor = builder.build();

        return new WidgetDescriptorYml( descriptor );
    }
}
