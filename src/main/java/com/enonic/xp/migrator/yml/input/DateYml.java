package com.enonic.xp.migrator.yml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateYml
    extends InputYml<String>
{
    public DateYml( final Input source )
    {
        super( source, String.class );
        setAttributes( source );
    }
}
