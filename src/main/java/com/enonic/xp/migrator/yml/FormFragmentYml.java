package com.enonic.xp.migrator.yml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.InlineMixin;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormFragmentYml
    extends FormItemYml
{
    public String name;

    public FormFragmentYml( final InlineMixin source )
    {
        super( "FormFragment" );

        name = source.getName();
    }
}
