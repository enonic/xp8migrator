package com.enonic.xp.migrator.yaml.input;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaSelectorYml
    extends InputYml<String>
{
    public List<String> allowContentType;

    public List<String> allowPath;

    public Boolean treeMode;

    public Boolean hideToggleIcon;

    public MediaSelectorYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            if ( inputTypeConfig.getValue( "treeMode" ) != null )
            {
                treeMode = inputTypeConfig.getValue( "treeMode", Boolean.class );
            }

            if ( inputTypeConfig.getValue( "hideToggleIcon" ) != null )
            {
                hideToggleIcon = inputTypeConfig.getValue( "hideToggleIcon", Boolean.class );
            }

            final Set<InputTypeProperty> allowContentTypeValues = inputTypeConfig.getProperties( "allowContentType" );
            if ( !allowContentTypeValues.isEmpty() )
            {
                allowContentType = allowContentTypeValues.stream().map( InputTypeProperty::getValue ).collect( Collectors.toList() );
            }

            final Set<InputTypeProperty> allowPathValues = inputTypeConfig.getProperties( "allowPath" );
            if ( !allowPathValues.isEmpty() )
            {
                allowPath = allowPathValues.stream().map( InputTypeProperty::getValue ).collect( Collectors.toList() );
            }

            setAttributes( source, "treeMode", "hideToggleIcon", "allowContentType", "allowPath" );
        }
    }
}
