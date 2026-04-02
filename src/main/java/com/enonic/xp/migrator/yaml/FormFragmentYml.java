package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.InlineMixin;

import io.micronaut.core.annotation.Introspected;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
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
