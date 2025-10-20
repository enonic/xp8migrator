package com.enonic.xp.migrator.yml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentTypeFilterYml
    extends InputYml<String>
{
    public ContentTypeFilterYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            if ( inputTypeConfig.getValue( "context" ) != null )
            {
                config.put( "context", inputTypeConfig.getValue( "context", Boolean.class ) );
            }

            setConfig( source, "context" );
        }
    }
}
