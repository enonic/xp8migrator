package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextLineYml
    extends InputYml<String>
{
    public Integer maxLength;

    public String regexp;

    public Boolean showCounter;

    public TextLineYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getValue( "maxLength" ) != null )
        {
            maxLength = inputTypeConfig.getValue( "maxLength", Integer.class );
        }

        if ( inputTypeConfig.getValue( "regexp" ) != null )
        {
            regexp = inputTypeConfig.getValue( "regexp" );
        }

        if ( inputTypeConfig.getValue( "showCounter" ) != null )
        {
            showCounter = inputTypeConfig.getValue( "showCounter", Boolean.class );
        }

        setConfig( source, "maxLength", "regexp", "showCounter" );
    }
}
