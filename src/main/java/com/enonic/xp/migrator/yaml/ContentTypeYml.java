package com.enonic.xp.migrator.yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.schema.content.ContentType;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "superType", "abstract", "final", "allowChildContent", "title", "description", "displayNamePlaceholder",
    "displayNameExpression", "displayNameListExpression", "form", "config"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class ContentTypeYml
{
    public final String kind = "ContentType";

    public String superType;

    @JsonProperty("abstract")
    public Boolean abstractValue;

    @JsonProperty("final")
    public Boolean finalValue;

    public Boolean allowChildContent;

    public LocalizedText title;

    public LocalizedText description;

    public List<String> allowChildContentType;

    public LocalizedText displayNamePlaceholder;

    public String displayNameExpression;

    public String displayNameListExpression;

    public Map<String, Object> config;

    public Form form;

    public ContentTypeYml( final ContentType descriptor )
    {
        superType = descriptor.getSuperType().toString();
        title = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        abstractValue = descriptor.isAbstract() ? Boolean.TRUE : null;
        finalValue = descriptor.isFinal() ? Boolean.TRUE : null;
        allowChildContent = descriptor.allowChildContent() ? null : Boolean.FALSE;
        form = descriptor.getForm();

        final List<String> allowChildContentTypes = descriptor.getAllowChildContentType();
        if ( allowChildContentTypes != null && !allowChildContentTypes.isEmpty() )
        {
            allowChildContentType = allowChildContentTypes;
        }

        displayNamePlaceholder =
            LocalizeHelper.localizeProperty( descriptor.getDisplayNameLabel(), descriptor.getDisplayNameLabelI18nKey() );

        displayNameExpression = descriptor.getDisplayNameExpression();

        final InputTypeConfig schemaConfig = descriptor.getSchemaConfig();
        if ( schemaConfig != null && schemaConfig.getSize() > 0 )
        {
            schemaConfig.iterator().forEachRemaining( property -> {
                if ( "listTitleExpression".equals( property.getName() ) )
                {
                    displayNameListExpression = property.getValue();
                }
                else
                {
                    config = config != null ? config : new LinkedHashMap<>();
                    config.put( property.getName(), property.getValue() );
                }
            } );
        }
    }
}
