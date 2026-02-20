package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yml.CmsDescriptorYml;
import com.enonic.xp.migrator.yml.SiteDescriptorYml;
import com.enonic.xp.resource.ResourceKey;
import com.enonic.xp.site.SiteDescriptor;
import com.enonic.xp.site.mapping.ControllerMappingDescriptor;
import com.enonic.xp.site.mapping.ControllerMappingDescriptors;
import com.enonic.xp.xml.parser.XmlSiteParser;

public class SiteMigrator
    extends DescriptorMigrator
{

    protected SiteMigrator( final MigrationParams params )
    {
        super( params );
    }

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final SiteDescriptor.Builder builder = SiteDescriptor.create();

        final XmlSiteParser parser = new XmlSiteParser();
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.siteDescriptorBuilder( builder );
        parser.parse();

        builder.applicationKey( currentApplication );

        final SiteDescriptor siteDescriptor = builder.build();

        final Path cmsPath = resourcesDir.resolve( "site" ).resolve( "cms.yml" );
        new CmsMigrator( siteDescriptor, new MigrationParams( currentApplication, resourcesDir, cmsPath ) ).migrate();

        final ControllerMappingDescriptors modifiedMappings = ControllerMappingDescriptors.from(
            siteDescriptor.getMappingDescriptors().stream().map( this::remapMapDescriptor ).collect( Collectors.toList() ) );

        return new SiteDescriptorYml( currentApplication,
                                      SiteDescriptor.copyOf( siteDescriptor ).mappingDescriptors( modifiedMappings ).build() );
    }

    private ControllerMappingDescriptor remapMapDescriptor( final ControllerMappingDescriptor mapping )
    {
        final ControllerMappingDescriptor.Builder builder = ControllerMappingDescriptor.copyOf( mapping );

        if ( mapping.getController() != null)
        {
            builder.controller( updateResourceKey( mapping.getController() ) );
        }

        if ( mapping.getFilter() != null)
        {
            builder.filter( updateResourceKey( mapping.getFilter() ) );
        }

        return builder.build();
    }

    private ResourceKey updateResourceKey( final ResourceKey key )
    {
        if ( key.getPath().startsWith( "/site/" ) )
        {
            return ResourceKey.from( key.getApplicationKey(), key.getPath().replace( "/site/", "/cms/" ) );
        }

        return key;
    }

    private static class CmsMigrator
        extends DescriptorMigrator
    {
        private final SiteDescriptor siteDescriptor;

        private CmsMigrator( final SiteDescriptor siteDescriptor, final MigrationParams params )
        {
            super( params );
            this.siteDescriptor = siteDescriptor;
        }

        @Override
        public Object doMigrate( final ApplicationKey currentApplication, final Path source )
            throws IOException
        {
            return new CmsDescriptorYml( siteDescriptor );
        }
    }
}
