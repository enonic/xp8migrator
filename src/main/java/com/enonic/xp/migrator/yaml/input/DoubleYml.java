package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoubleYml
    extends InputYml<Double>
{
    public Double min;

    public Double max;

    public DoubleYml( final Input source )
    {
        super( source, Double.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getValue( "min" ) != null )
        {
            min = inputTypeConfig.getValue( "min", Double.class );
        }

        if ( inputTypeConfig.getValue( "max" ) != null )
        {
            max = inputTypeConfig.getValue( "max", Double.class );
        }

        setAttributes( source, "min", "max" );
    }
}
