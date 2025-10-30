package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record WidgetsDirPostMigrator(Path resourcesDir)
{
    public void migrate()
    {
        final Path mixinDir = resourcesDir.resolve( "admin" ).resolve( "widgets" );

        if ( Files.exists( mixinDir ) )
        {
            try
            {
                final Path formFragmentsDir = resourcesDir.resolve( "admin" ).resolve( "extensions" );
                Files.move( mixinDir, formFragmentsDir );
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        }
    }
}
