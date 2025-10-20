package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomSelectorYml
    extends InputYml<String>
{
    public CustomSelectorYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            final InputTypeProperty serviceProperty = inputTypeConfig.getProperty( "service" );
            if ( serviceProperty != null )
            {
                config.put( "service", serviceProperty.getValue() );
            }

            final Set<InputTypeProperty> paramProperties = inputTypeConfig.getProperties( "param" );

            if ( !paramProperties.isEmpty() )
            {
                final Map<String, String> params = new LinkedHashMap<>();
                paramProperties.forEach( param -> params.put( param.getAttribute( "value" ), param.getValue() ) );
                config.put( "param", params );
            }

            setConfig( source, "service", "param" );
        }
    }
}
