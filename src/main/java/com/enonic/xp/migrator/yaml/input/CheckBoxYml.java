package com.enonic.xp.migrator.yaml.input;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@ReflectiveAccess
public class CheckBoxYml
    extends InputYml<String>
{
    public String alignment;

    public CheckBoxYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            if ( inputTypeConfig.getValue( "alignment" ) != null )
            {
                alignment = inputTypeConfig.getValue( "alignment" );
            }
        }

        setAttributes( source );
    }
}
