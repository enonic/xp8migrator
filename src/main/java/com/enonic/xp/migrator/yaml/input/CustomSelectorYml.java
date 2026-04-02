package com.enonic.xp.migrator.yaml.input;

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
    public String service;

    public Map<String, String> params;

    public Boolean galleryMode;

    public CustomSelectorYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            final InputTypeProperty serviceProperty = inputTypeConfig.getProperty( "service" );
            if ( serviceProperty != null )
            {
                service = serviceProperty.getValue();
            }

            final Set<InputTypeProperty> paramProperties = inputTypeConfig.getProperties( "param" );

            if ( !paramProperties.isEmpty() )
            {
                params = new LinkedHashMap<>();
                paramProperties.forEach( param -> params.put( param.getAttribute( "value" ), param.getValue() ) );
            }

            if ( inputTypeConfig.getValue( "galleryMode" ) != null )
            {
                galleryMode = inputTypeConfig.getValue( "galleryMode", Boolean.class );
            }

            setAttributes( source, "service", "param", "galleryMode" );
        }
    }
}
