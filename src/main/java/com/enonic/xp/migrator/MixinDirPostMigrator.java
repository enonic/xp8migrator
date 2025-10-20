package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record MixinDirPostMigrator(Path resourceDir)
{
    public void migrate()
    {
        final Path mixinDir = resourceDir.resolve( "site" ).resolve( "mixins" );

        if ( Files.exists( mixinDir ) )
        {
            try
            {
                final Path formFragmentsDir = resourceDir.resolve( "site" ).resolve( "form-fragments" );
                Files.move( mixinDir, formFragmentsDir );
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        }
    }
}
