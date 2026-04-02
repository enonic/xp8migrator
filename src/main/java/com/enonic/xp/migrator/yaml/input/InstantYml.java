package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;

import io.micronaut.core.annotation.Introspected;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
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
