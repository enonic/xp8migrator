package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Form;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.region.LayoutDescriptor;
import com.enonic.xp.region.RegionDescriptors;
import com.enonic.xp.schema.LocalizedText;

@ReflectiveAccess
@JsonPropertyOrder({"kind", "title", "description", "form", "regions", "config"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LayoutDescriptorYml
{
    public final String kind = "Layout";

    public LocalizedText title;

    public LocalizedText description;

    public Form form;

    public List<String> regions = List.of();

    public GenericValue config;

    public LayoutDescriptorYml( final LayoutDescriptor descriptor )
    {
        title = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        form = descriptor.getConfig();

        final RegionDescriptors regionList = descriptor.getRegions();
        if ( regionList != null && regionList.iterator().hasNext() )
        {
            regions = new ArrayList<>();
            regionList.iterator().forEachRemaining( region -> regions.add( region.toString() ) );
        }

        final InputTypeConfig schemaConfig = descriptor.getSchemaConfig();
        if ( schemaConfig != null && schemaConfig.getSize() > 0 )
        {
            config = SchemaConfigHelper.toGenericValue( schemaConfig );
        }
    }
}
