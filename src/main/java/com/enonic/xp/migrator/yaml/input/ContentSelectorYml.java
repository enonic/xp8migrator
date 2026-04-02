package com.enonic.xp.migrator.yaml.input;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentSelectorYml
    extends InputYml<String>
{
    public List<String> allowContentType;

    public List<String> allowPath;

    public Boolean treeMode;

    public Boolean hideToggleIcon;

    public ContentSelectorYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
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

            if ( inputTypeConfig.getValue( "treeMode" ) != null )
            {
                treeMode = inputTypeConfig.getValue( "treeMode", Boolean.class );
            }

            if ( inputTypeConfig.getValue( "hideToggleIcon" ) != null )
            {
                hideToggleIcon = inputTypeConfig.getValue( "hideToggleIcon", Boolean.class );
            }

            setAttributes( source, "allowContentType", "allowPath", "treeMode", "hideToggleIcon" );
        }
    }
}
