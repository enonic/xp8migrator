package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Form;
import com.enonic.xp.macro.MacroDescriptor;
import com.enonic.xp.schema.LocalizedText;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MacroDescriptorYml
{
    public LocalizedText displayName;

    public LocalizedText description;

    public Form form;

    public MacroDescriptorYml( final MacroDescriptor descriptor )
    {
        displayName = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        form = descriptor.getForm();
    }
}
