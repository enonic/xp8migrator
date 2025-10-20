package com.enonic.xp.migrator.yml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateTimeYml
    extends InputYml<String>
{
    public DateTimeYml( final Input source )
    {
        super( source, String.class );
        setConfig( source, "timezone" );
    }
}
