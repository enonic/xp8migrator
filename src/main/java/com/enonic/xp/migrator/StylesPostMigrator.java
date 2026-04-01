package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record StylesPostMigrator(Path resourcesDir)
{
    public void migrate()
    {
        moveFile( "image.yml" );
    }

    private void moveFile( final String fileName )
    {
        final Path sourceFile = resourcesDir.resolve( "site" ).resolve( fileName );

        if ( Files.exists( sourceFile ) )
        {
            try
            {
                final Path targetDir = resourcesDir.resolve( "site" ).resolve( "styles" );
                final Path targetFile = targetDir.resolve( fileName );
                if ( !Files.exists( targetDir ) )
                {
                    Files.createDirectory( targetDir );
                }
                Files.move( sourceFile, targetFile );
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        }
    }
}
