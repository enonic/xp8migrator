package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Form;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.schema.xdata.XData;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class XDataDescriptorYml
{
    public LocalizedText displayName;

    public LocalizedText description;

    public Form form;

    public XDataDescriptorYml( final XData descriptor )
    {
        displayName = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        form = descriptor.getForm();
    }
}
