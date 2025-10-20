package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HtmlAreaYml
    extends InputYml<String>
{
    public HtmlAreaYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            if ( inputTypeConfig.getValue( "exclude" ) != null )
            {
                config.put( "exclude", inputTypeConfig.getValue( "exclude" ) );
            }

            if ( inputTypeConfig.getValue( "include" ) != null )
            {
                config.put( "include", inputTypeConfig.getValue( "include" ) );
            }

            if ( inputTypeConfig.getValue( "allowHeadings" ) != null )
            {
                config.put( "allowHeadings", inputTypeConfig.getValue( "allowHeadings" ) );
            }

            setConfig( source, "exclude", "include", "allowHeadings" );
        }
    }
}
