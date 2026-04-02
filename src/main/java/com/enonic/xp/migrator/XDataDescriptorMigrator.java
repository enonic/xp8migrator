package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yaml.XDataDescriptorYml;
import com.enonic.xp.schema.xdata.XData;
import com.enonic.xp.schema.xdata.XDataName;
import com.enonic.xp.xml.parser.XmlXDataParser;

public class XDataDescriptorMigrator
    extends DescriptorMigrator
{
    protected XDataDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final XData.Builder builder = XData.create();

        final XmlXDataParser parser = new XmlXDataParser();
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.builder( builder );
        parser.parse();

        builder.name( XDataName.from( currentApplication, "_TEMP_" ) );

        final XData descriptor = builder.build();
        return new XDataDescriptorYml( descriptor );
    }
}
