package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yaml.StyleDescriptorYml;
import com.enonic.xp.style.StyleDescriptor;
import com.enonic.xp.xml.parser.XmlStyleDescriptorParser;

public class StyleDescriptorMigrator
    extends DescriptorMigrator
{
    protected StyleDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final StyleDescriptor.Builder builder = StyleDescriptor.create();
        builder.application( currentApplication );

        final XmlStyleDescriptorParser parser = new XmlStyleDescriptorParser();
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.styleDescriptorBuilder( builder );
        parser.parse();

        final StyleDescriptor styleDescriptor = builder.build();
        return new StyleDescriptorYml( styleDescriptor );
    }

    @Override
    public Path resolveYmlFilePath( final Path source )
    {
        return resourcesDir.resolve( "site" ).resolve( "image.yaml" );
    }
}
