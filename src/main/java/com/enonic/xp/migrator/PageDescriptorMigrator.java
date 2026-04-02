package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.yaml.PageDescriptorYml;
import com.enonic.xp.page.PageDescriptor;
import com.enonic.xp.xml.parser.XmlPageDescriptorParser;

public class PageDescriptorMigrator
    extends DescriptorMigrator
{
    protected PageDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final PageDescriptor.Builder builder = PageDescriptor.create().key( DescriptorKey.from( currentApplication, "_TEMP_" ) );

        final XmlPageDescriptorParser parser = new XmlPageDescriptorParser();
        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final PageDescriptor descriptor = builder.build();

        return new PageDescriptorYml( descriptor );
    }
}
