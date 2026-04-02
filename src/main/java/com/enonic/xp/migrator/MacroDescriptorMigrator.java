package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.macro.MacroDescriptor;
import com.enonic.xp.macro.MacroKey;
import com.enonic.xp.migrator.yaml.MacroDescriptorYml;
import com.enonic.xp.xml.parser.XmlMacroDescriptorParser;

public class MacroDescriptorMigrator
    extends DescriptorMigrator
{
    protected MacroDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final MacroDescriptor.Builder builder = MacroDescriptor.create();
        builder.key( MacroKey.from( currentApplication, "_TEMP_" ) );

        final XmlMacroDescriptorParser parser = new XmlMacroDescriptorParser();
        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final MacroDescriptor macroDescriptor = builder.build();
        return new MacroDescriptorYml( macroDescriptor );
    }
}
