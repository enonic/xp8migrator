package com.enonic.xp.migrator.yaml.input;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class OptionYml
{
    private final Map<String, Object> attributes = new LinkedHashMap<>();

    @JsonAnySetter
    public void addAttribute( final String key, final Object value )
    {
        attributes.put( key, value );
    }

    @JsonAnyGetter
    public Map<String, Object> getAttributes()
    {
        return attributes;
    }

}
