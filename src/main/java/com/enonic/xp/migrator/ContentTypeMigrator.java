package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yaml.ContentTypeYml;
import com.enonic.xp.schema.content.ContentType;
import com.enonic.xp.schema.content.ContentTypeName;
import com.enonic.xp.xml.parser.XmlContentTypeParser;

public class ContentTypeMigrator
    extends DescriptorMigrator
{
    protected ContentTypeMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final ContentType.Builder builder = ContentType.create();
        builder.name( ContentTypeName.from( currentApplication, "_TEMP_" ) );

        final XmlContentTypeParser parser = new XmlContentTypeParser();
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.builder( builder );
        parser.parse();

        final ContentType contentType = builder.build();
        return new ContentTypeYml( contentType );
    }
}
