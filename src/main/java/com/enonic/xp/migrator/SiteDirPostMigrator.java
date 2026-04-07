package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public record SiteDirPostMigrator(Path resourcesDir)
{
    public void migrate()
    {
        final Path siteDir = resourcesDir.resolve( "site" );
        final Path cmsDir = resourcesDir.resolve( "cms" );

        if ( !Files.exists( siteDir ) )
        {
            return;
        }

        final Path imageYaml = siteDir.resolve( "image.yaml" );
        if ( Files.exists( imageYaml ) )
        {
            try
            {
                final Path stylesTarget = cmsDir.resolve( "styles" );
                Files.createDirectories( stylesTarget );
                Files.move( imageYaml, stylesTarget.resolve( "image.yaml" ) );
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
            FileUtils.copyDirExcludingXml( mixinsDir, cmsDir.resolve( "form-fragments" ) );
        }
        if ( Files.exists( xDataDir ) )
        {
            FileUtils.copyDirExcludingXml( xDataDir, cmsDir.resolve( "mixins" ) );
        }

        FileUtils.copyDirExcludingXml( siteDir, cmsDir, Set.of( mixinsDir, xDataDir ) );
    }
}

