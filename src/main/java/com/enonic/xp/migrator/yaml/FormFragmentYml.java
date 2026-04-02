package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.InlineMixin;

@ReflectiveAccess
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
