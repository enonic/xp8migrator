package com.enonic.xp.migrator.yaml;

import java.util.Set;

import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

public class SchemaConfigHelper
{
    public static GenericValue toGenericValue( final InputTypeConfig schemaConfig )
    {
        return toGenericValue( schemaConfig, Set.of() );
    }

    public static GenericValue toGenericValue( final InputTypeConfig schemaConfig, final Set<String> excludedNames )
    {
        if ( schemaConfig == null || schemaConfig.getSize() == 0 )
        {
            return GenericValue.newObject().build();
        }

        final GenericValue.ObjectBuilder builder = GenericValue.newObject();
        for ( final String name : schemaConfig.getNames() )
        {
            if ( excludedNames.contains( name ) )
            {
                continue;
            }
            final Set<InputTypeProperty> properties = schemaConfig.getProperties( name );
            if ( properties.size() == 1 )
            {
                builder.put( name, toGenericValue( properties.iterator().next() ) );
            }
            else
            {
                builder.put( name, properties.stream().map( SchemaConfigHelper::toGenericValue ).collect( GenericValue.listCollector() ) );
            }
        }
        return builder.build();
    }

    private static GenericValue toGenericValue( final InputTypeProperty property )
    {
        if ( property.getAttributes().isEmpty() )
        {
            return GenericValue.stringValue( property.getValue() );
        }

        final GenericValue.ObjectBuilder builder = GenericValue.newObject();
        builder.put( "value", property.getValue() );
        property.getAttributes().forEach( builder::put );
        return builder.build();
    }
}
