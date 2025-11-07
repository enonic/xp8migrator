package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record SiteDirPostMigrator(Path resourcesDir)
{
    public void migrate()
    {
        final Path siteDir = resourcesDir.resolve( "site" );
        final Path cmsDir = resourcesDir.resolve( "cms" );

        if ( Files.exists( siteDir ) ) {
            try
            {
                Files.move( siteDir, cmsDir );
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        }
    }
}

