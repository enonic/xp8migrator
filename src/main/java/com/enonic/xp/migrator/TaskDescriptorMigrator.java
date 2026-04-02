package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.yaml.TaskDescriptorYml;
import com.enonic.xp.task.TaskDescriptor;
import com.enonic.xp.xml.parser.XmlTaskDescriptorParser;

public class TaskDescriptorMigrator
    extends DescriptorMigrator
{
    protected TaskDescriptorMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final TaskDescriptor.Builder builder = TaskDescriptor.create();
        builder.key( DescriptorKey.from( currentApplication, "_TEMP_" ) );

        final XmlTaskDescriptorParser parser = new XmlTaskDescriptorParser();
        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();

        final TaskDescriptor descriptor = builder.build();

        return new TaskDescriptorYml( descriptor );
    }
}
