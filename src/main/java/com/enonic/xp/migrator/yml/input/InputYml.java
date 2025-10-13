package com.enonic.xp.migrator.yml.input;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.enonic.xp.form.Input;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeDefault;
import com.enonic.xp.inputtype.InputTypeProperty;
import com.enonic.xp.schema.LocalizedText;

import static com.enonic.xp.migrator.yml.LocalizeHelper.localizeProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputYml<T>
{
    public String type;

    public String name;

    public LocalizedText label;

    public LocalizedText helpText;

    public Occurrences occurrences;

    @JsonProperty("default")
    public Object defaultValue;

    public Map<String, Object> config;

    public InputYml( final Input source, final Class<T> inputType )
    {
        type = resolveInputTypeName( source );
        name = source.getName();
        label = localizeProperty( source.getLabel(), source.getLabelI18nKey() );
        helpText = localizeProperty( source.getHelpText(), source.getHelpTextI18nKey() );

        if ( source.getOccurrences() != null )
        {
            occurrences = source.getOccurrences();
        }

        final InputTypeDefault inputDefaultValue = source.getDefaultValue();
        if ( inputDefaultValue != null )
        {
            defaultValue = inputDefaultValue.getValue( "default", inputType );
        }
    }

    @JsonIgnore
    protected final void setConfig( final Input source, final String... excludeProperties )
    {
        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        final List<String> exclusionList = Arrays.asList( Objects.requireNonNullElseGet( excludeProperties, () -> new String[]{} ) );

        final Set<String> propertyNames =
            inputTypeConfig.getNames().stream().filter( name -> !exclusionList.contains( name ) ).collect( Collectors.toSet() );

        if ( !propertyNames.isEmpty() )
        {
            config = config != null ? config : new LinkedHashMap<>();

            propertyNames.forEach( propertyName -> {
                final Set<InputTypeProperty> properties = inputTypeConfig.getProperties( propertyName );
                if ( properties.size() == 1 )
                {
                    config.put( propertyName, properties.iterator().next().getValue() );
                }
                else
                {
                    config.put( propertyName, properties.stream().map( InputTypeProperty::getValue ).collect( Collectors.toSet() ) );
                }
            } );
        }
    }

    protected String resolveInputTypeName( final Input source )
    {
        return source.getInputType().toString();
    }
}
