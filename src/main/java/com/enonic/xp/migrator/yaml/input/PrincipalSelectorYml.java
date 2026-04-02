package com.enonic.xp.migrator.yaml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrincipalSelectorYml
    extends InputYml<String>
{
    public String principalType;

    public PrincipalSelectorYml( final Input source )
    {
        super( source, String.class );
        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            if ( inputTypeConfig.getValue( "principalType" ) != null )
            {
                principalType = inputTypeConfig.getValue( "principalType" );
            }

            setAttributes( source, "principalType" );
        }
    }
}
