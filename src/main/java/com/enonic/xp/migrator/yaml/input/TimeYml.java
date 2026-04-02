package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;

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
