package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.enonic.xp.app.ApplicationKey;

public final class MigrationExecutor
{
    private static final List<DescriptorConfig> DESCRIPTORS = new ArrayList<>();

    static
    {
        DESCRIPTORS.add( new DirDescriptorConfig( "site/parts", PartMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/content-types", ContentTypeMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/layouts", LayoutDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/mixins", MixinDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/pages", PageDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/x-data", XDataDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/macros", MacroDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "tasks", TaskDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "admin/tools", AdminToolDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "admin/widgets", WidgetDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "services", ServiceDescriptorMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "application.xml", ApplicationMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "site/site.xml", SiteMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "site/styles.xml", StyleDescriptorMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "idprovider/idprovider.xml", IdProviderDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "apis", ApiDescriptorMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "webapp/webapp.xml", WebappDescriptorMigrator::new ) );
    }

    private final Path projectPath;

    private final ApplicationKey currentApplication;

    public MigrationExecutor( final Path projectPath, final ApplicationKey currentApplication )
    {
        this.projectPath = projectPath;
        this.currentApplication = currentApplication;
    }

    public MigrationResult migrate()
    {
        final Path resourcesDir = projectPath.resolve( "src/main/resources" );

        final MigrationResult result = new MigrationResult();

        DESCRIPTORS.forEach( descriptorConfig -> {
            final Path baseDirOrFilePath = resourcesDir.resolve( descriptorConfig.key() );

            if ( !Files.exists( baseDirOrFilePath ) )
            {
                return;
            }

            try
            {
                if ( descriptorConfig instanceof DirDescriptorConfig )
                {
                    try (Stream<Path> paths = Files.walk( baseDirOrFilePath ))
                    {
                        paths.filter( Files::isRegularFile ).filter( p -> {
                            final Path parent = p.getParent();
                            final Path expected = parent.resolve( parent.getFileName().toString() + ".xml" );
                            return p.equals( expected );
                        } ).forEach( descriptor -> {
                            final DescriptorMigrator migrator = descriptorConfig.migratorSupplier().apply(
                                new MigrationParams( currentApplication, resourcesDir, descriptor ) );
                            doMigrate( result, migrator, descriptor );
                        } );
                    }
                }
                else
                {
                    final DescriptorMigrator migrator = descriptorConfig.migratorSupplier().apply(
                        new MigrationParams( currentApplication, resourcesDir, baseDirOrFilePath ) );
                    doMigrate( result, migrator, baseDirOrFilePath );
                }
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        } );

        doPostMigration( resourcesDir );

        return result;
    }

    private void doMigrate( final MigrationResult result, final DescriptorMigrator migrator, final Path descriptor )
    {
        try
        {
            migrator.migrate();
            result.addEntry( descriptor, true );
        }
        catch ( Exception e )
        {
            result.addEntry( descriptor, false );
            throw e;
        }
    }

    private void doPostMigration( final Path resourcesDir )
    {
        new StylesPostMigrator( resourcesDir ).migrate();
        new MixinDirPostMigrator( resourcesDir ).migrate();
        new XDataDirPostMigrator( resourcesDir ).migrate();
        new SiteDirPostMigrator( resourcesDir ).migrate();
        new WidgetsDirPostMigrator( resourcesDir ).migrate();
    }
}
