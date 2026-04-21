package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public record SiteDirPostMigrator(Path resourcesDir, AtomicReference<OnExistsStrategy> onExists)
{
    public void migrate()
    {
        final Path siteDir = resourcesDir.resolve( "site" );
        final Path cmsDir = resourcesDir.resolve( "cms" );

        if ( !Files.exists( siteDir ) )
        {
            return;
        }

        final Path styleYaml = siteDir.resolve( "style.yaml" );
        if ( Files.exists( styleYaml ) )
        {
            try
            {
                final Path stylesTarget = cmsDir.resolve( "style" );
                Files.createDirectories( stylesTarget );
                Files.move( styleYaml, stylesTarget.resolve( "style.yaml" ) );
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        }

        final Path mixinsDir = siteDir.resolve( "mixins" );
        final Path xDataDir = siteDir.resolve( "x-data" );

        if ( Files.exists( mixinsDir ) )
        {
            FileUtils.copyDirExcludingXml( mixinsDir, cmsDir.resolve( "form-fragments" ), onExists );
        }
        if ( Files.exists( xDataDir ) )
        {
            FileUtils.copyDirExcludingXml( xDataDir, cmsDir.resolve( "mixins" ), onExists );
        }

        FileUtils.copyDirExcludingXml( siteDir, cmsDir, Set.of( mixinsDir, xDataDir ), onExists );
    }
}

