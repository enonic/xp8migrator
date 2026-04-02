package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeYml
    extends InputYml<String>
{
    public TimeYml( final Input source )
    {
        super( source, String.class );
        setAttributes( source );
    }
}
