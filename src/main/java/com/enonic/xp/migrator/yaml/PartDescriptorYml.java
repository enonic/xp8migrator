package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Form;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.region.PartDescriptor;
import com.enonic.xp.schema.LocalizedText;

@ReflectiveAccess
@JsonPropertyOrder({"kind", "title", "description", "form", "config"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartDescriptorYml
{
    public final String kind = "Part";

    public LocalizedText title;

    public LocalizedText description;

    public Form form;

    public GenericValue config;

    public PartDescriptorYml( final PartDescriptor source )
    {
        this.title = LocalizeHelper.localizeProperty( source.getDisplayName(), source.getDisplayNameI18nKey() );
        this.description = LocalizeHelper.localizeProperty( source.getDescription(), source.getDescriptionI18nKey() );
        this.form = source.getConfig();

        final InputTypeConfig schemaConfig = source.getSchemaConfig();
        if ( schemaConfig != null && schemaConfig.getSize() > 0 )
        {
            config = SchemaConfigHelper.toGenericValue( schemaConfig );
        }
    }
}
