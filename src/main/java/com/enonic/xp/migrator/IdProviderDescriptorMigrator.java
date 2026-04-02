package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.idprovider.IdProviderDescriptor;
import com.enonic.xp.migrator.yaml.IdProviderDescriptorYml;
import com.enonic.xp.xml.parser.XmlIdProviderDescriptorParser;

public class IdProviderDescriptorMigrator
    extends DescriptorMigrator
{
    protected IdProviderDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final IdProviderDescriptor.Builder builder = IdProviderDescriptor.create().key( currentApplication );

        final XmlIdProviderDescriptorParser parser = new XmlIdProviderDescriptorParser();
        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final IdProviderDescriptor descriptor = builder.build();

        return new IdProviderDescriptorYml( descriptor );
    }
}
