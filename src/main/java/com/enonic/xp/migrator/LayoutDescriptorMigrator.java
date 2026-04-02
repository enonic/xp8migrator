package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.yaml.LayoutDescriptorYml;
import com.enonic.xp.region.LayoutDescriptor;
import com.enonic.xp.xml.parser.XmlLayoutDescriptorParser;

public class LayoutDescriptorMigrator
    extends DescriptorMigrator
{
    protected LayoutDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final LayoutDescriptor.Builder builder = LayoutDescriptor.create();

        final XmlLayoutDescriptorParser parser = new XmlLayoutDescriptorParser();
        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        builder.key( DescriptorKey.from( currentApplication, "_TEMP_" ) );

        final LayoutDescriptor descriptor = builder.build();
        return new LayoutDescriptorYml( descriptor );
    }
}
