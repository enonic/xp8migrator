package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstantYml
    extends InputYml<String>
{
    public InstantYml( final Input source )
    {
        super( source, String.class );
        setAttributes( source, "timezone" );
    }

    @Override
    protected String resolveInputTypeName( final Input source )
    {
        return "Instant";
    }
}
