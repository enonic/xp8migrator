package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yaml.MixinDescriptorYml;
import com.enonic.xp.schema.mixin.Mixin;
import com.enonic.xp.schema.mixin.MixinName;
import com.enonic.xp.xml.parser.XmlMixinParser;

public class MixinDescriptorMigrator
    extends DescriptorMigrator
{
    protected MixinDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final Mixin.Builder builder = Mixin.create();

        final XmlMixinParser parser = new XmlMixinParser();
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.builder( builder );
        parser.parse();

        builder.name( MixinName.from( currentApplication, "_TEMP_" ) );

        final Mixin descriptor = builder.build();
        return new MixinDescriptorYml( descriptor );
    }
}
