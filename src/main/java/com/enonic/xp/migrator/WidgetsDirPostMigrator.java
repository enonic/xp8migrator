package com.enonic.xp.migrator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public record WidgetsDirPostMigrator(Path resourcesDir, AtomicReference<OnExistsStrategy> onExists)
{
    public void migrate( final MigrationResult result )
    {
        final Path widgetsDir = resourcesDir.resolve( "admin" ).resolve( "widgets" );

        if ( Files.exists( widgetsDir ) )
        {
            final Path extensionsDir = resourcesDir.resolve( "admin" ).resolve( "extensions" );
            FileUtils.copyDirExcludingXml( widgetsDir, extensionsDir, onExists );
            result.addConsumedSourceDir( widgetsDir );
        }
    }
}