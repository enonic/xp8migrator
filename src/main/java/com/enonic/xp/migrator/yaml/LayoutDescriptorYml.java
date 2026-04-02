package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.region.LayoutDescriptor;
import com.enonic.xp.region.RegionDescriptors;
import com.enonic.xp.schema.LocalizedText;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "title", "description", "form", "regions", "config"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class LayoutDescriptorYml
{
    public final String kind = "Layout";

    public LocalizedText title;

    public LocalizedText description;

    public Form form;

    public List<String> regions = List.of();

    public List<Map<String, String>> config;

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
        if ( schemaConfig != null && schemaConfig.iterator().hasNext() )
        {
            config = new ArrayList<>();
            schemaConfig.iterator().forEachRemaining( property -> {
                final Map<String, String> propAsMap = new LinkedHashMap<>();

                propAsMap.put( "name", property.getName() );
                propAsMap.put( "value", property.getValue() );
                propAsMap.putAll( property.getAttributes() );

                config.add( propAsMap );
            } );
        }
    }
}
