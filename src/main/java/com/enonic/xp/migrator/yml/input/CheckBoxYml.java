package com.enonic.xp.migrator.yml.input;

import java.util.Objects;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeDefault;

public class CheckBoxYml
    extends InputYml<Boolean>
{

    public CheckBoxYml( final Input source )
    {
        super( source, Boolean.class );

        final InputTypeDefault inputDefaultValue = source.getDefaultValue();
        if ( inputDefaultValue != null )
        {
            config.put( "default", Objects.equals( "checked", inputDefaultValue.getValue( "default", String.class ) ) );
        }

        setConfig( source );
    }
}
