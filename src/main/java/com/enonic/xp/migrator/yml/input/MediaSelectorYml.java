package com.enonic.xp.migrator.yml.input;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaSelectorYml
    extends InputYml<String>
{
    public MediaSelectorYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            if ( inputTypeConfig.getValue( "treeMode" ) != null )
            {
                config.put( "treeMode", inputTypeConfig.getValue( "treeMode", Boolean.class ) );
            }

            if ( inputTypeConfig.getValue( "hideToggleIcon" ) != null )
            {
                config.put( "hideToggleIcon", inputTypeConfig.getValue( "hideToggleIcon", Boolean.class ) );
            }

            final Set<InputTypeProperty> allowContentTypeValues = inputTypeConfig.getProperties( "allowContentType" );
            if ( !allowContentTypeValues.isEmpty() )
            {
                config.put( "allowContentType",
                            allowContentTypeValues.stream().map( InputTypeProperty::getValue ).collect( Collectors.toList() ) );
            }

            final Set<InputTypeProperty> allowPathValues = inputTypeConfig.getProperties( "allowPath" );
            if ( !allowPathValues.isEmpty() )
            {
                config.put( "allowPath", allowPathValues.stream().map( InputTypeProperty::getValue ).collect( Collectors.toList() ) );
            }

            setConfig( source, "treeMode", "hideToggleIcon", "allowContentType", "allowPath" );
        }
    }
}
