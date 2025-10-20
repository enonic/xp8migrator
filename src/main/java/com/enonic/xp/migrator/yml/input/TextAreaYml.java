package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextAreaYml
    extends InputYml<String>
{
    public Integer maxLength;

    public TextAreaYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getValue( "maxLength" ) != null )
        {
            maxLength = inputTypeConfig.getValue( "maxLength", Integer.class );
        }

        if ( inputTypeConfig.getValue( "showCounter" ) != null )
        {
            config.put( "showCounter", inputTypeConfig.getValue( "showCounter", Boolean.class ) );
        }

        setConfig( source, "maxLength", "showCounter" );
    }
}
