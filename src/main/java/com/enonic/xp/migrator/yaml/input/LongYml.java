package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LongYml
    extends InputYml<Long>
{
    public Long min;

    public Long max;

    public LongYml( final Input source )
    {
        super( source, Long.class );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "min" ) != null )
        {
            min = config.getValue( "min", Long.class );
        }

        if ( config.getValue( "max" ) != null )
        {
            max = config.getValue( "max", Long.class );
        }

        setAttributes( source, "min", "max" );
    }
}
