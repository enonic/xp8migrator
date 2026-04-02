package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class FormItemYml
{
    private final String type;

    protected FormItemYml( final String type )
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
}
