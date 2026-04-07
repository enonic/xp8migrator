package com.enonic.xp.migrator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public record WidgetsDirPostMigrator(Path resourcesDir, AtomicReference<OnExistsStrategy> onExists)
{
    public void migrate()
    {
        final Path mixinDir = resourcesDir.resolve( "admin" ).resolve( "widgets" );

        if ( Files.exists( mixinDir ) )
        {
            final Path formFragmentsDir = resourcesDir.resolve( "admin" ).resolve( "extensions" );
            FileUtils.copyDirExcludingXml( mixinDir, formFragmentsDir, onExists );
        }
    }
}
