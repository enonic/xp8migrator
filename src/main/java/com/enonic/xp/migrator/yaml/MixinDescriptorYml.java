package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.schema.mixin.Mixin;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "title", "description", "form"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class MixinDescriptorYml
{
    public final String kind = "FormFragment";

    public LocalizedText title;

    public LocalizedText description;

    public Form form;

    public MixinDescriptorYml( final Mixin descriptor )
    {
        title = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        form = descriptor.getForm();
    }
}
