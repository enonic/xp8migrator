package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Form;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.schema.xdata.XData;

@ReflectiveAccess
@JsonPropertyOrder({"kind", "title", "description", "form"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XDataDescriptorYml
{
    public final String kind = "Mixin";

    public LocalizedText title;

    public LocalizedText description;

    public Form form;

    public XDataDescriptorYml( final XData descriptor )
    {
        title = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        form = descriptor.getForm();
    }
}
