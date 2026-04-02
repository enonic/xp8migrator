package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HtmlAreaYml
    extends InputYml<String>
{
    public String exclude;

    public String include;

    public String allowHeadings;

    public HtmlAreaYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            if ( inputTypeConfig.getValue( "exclude" ) != null )
            {
                exclude = inputTypeConfig.getValue( "exclude" );
            }

            if ( inputTypeConfig.getValue( "include" ) != null )
            {
                include = inputTypeConfig.getValue( "include" );
            }

            if ( inputTypeConfig.getValue( "allowHeadings" ) != null )
            {
                allowHeadings = inputTypeConfig.getValue( "allowHeadings" );
            }

            setAttributes( source, "exclude", "include", "allowHeadings" );
        }
    }
}
