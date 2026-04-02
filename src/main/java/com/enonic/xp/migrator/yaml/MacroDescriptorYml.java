package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.macro.MacroDescriptor;
import com.enonic.xp.schema.LocalizedText;

@JsonPropertyOrder({"kind", "title", "description", "form"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MacroDescriptorYml
{
    public final String kind = "Macro";

    public LocalizedText title;

    public LocalizedText description;

    public Form form;

    public MacroDescriptorYml( final MacroDescriptor descriptor )
    {
        title = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        form = descriptor.getForm();
    }
}
