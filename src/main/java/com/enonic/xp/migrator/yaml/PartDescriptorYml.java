package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.region.PartDescriptor;
import com.enonic.xp.schema.LocalizedText;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "title", "description", "form", "config"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class PartDescriptorYml
{
    public final String kind = "Part";

    public LocalizedText title;

    public LocalizedText description;

    public Form form;

    public List<Map<String, String>> config;

    public PartDescriptorYml( final PartDescriptor source )
    {
        this.title = LocalizeHelper.localizeProperty( source.getDisplayName(), source.getDisplayNameI18nKey() );
        this.description = LocalizeHelper.localizeProperty( source.getDescription(), source.getDescriptionI18nKey() );
        this.form = source.getConfig();

        final InputTypeConfig schemaConfig = source.getSchemaConfig();
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
