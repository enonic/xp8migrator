package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record XDataDirPostMigrator(Path resourceDir)
{
    public void migrate()
    {
        final Path mixinDir = resourceDir.resolve( "site" ).resolve( "x-data" );

        if ( Files.exists( mixinDir ) )
        {
            try
            {
                final Path formFragmentsDir = resourceDir.resolve( "site" ).resolve( "mixins" );
                Files.move( mixinDir, formFragmentsDir );
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        }
    }
}

