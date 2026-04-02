package com.enonic.xp.migrator.yaml.input;

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

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeDefault;
import com.enonic.xp.inputtype.InputTypeProperty;
import com.enonic.xp.schema.LocalizedText;

import static com.enonic.xp.migrator.yaml.LocalizeHelper.localizeProperty;

@ReflectiveAccess
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputYml<T>
{
    public String type;

    public String name;

    public LocalizedText label;

    public LocalizedText helpText;

    public Occurrences occurrences;

    @JsonProperty("default")
    public T defaultValue;

    public Map<String, Object> attributes;

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
        resolveDefaultValue( inputDefaultValue, inputType );
    }

    @JsonIgnore
    protected final void setAttributes( final Input source, final String... excludeProperties )
    {
        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        final List<String> exclusionList = Arrays.asList( Objects.requireNonNullElseGet( excludeProperties, () -> new String[]{} ) );

        final Set<String> propertyNames =
            inputTypeConfig.getNames().stream().filter( name -> !exclusionList.contains( name ) ).collect( Collectors.toSet() );

        if ( !propertyNames.isEmpty() )
        {
            attributes = new LinkedHashMap<>();
            propertyNames.forEach( propertyName -> {
                final Set<InputTypeProperty> properties = inputTypeConfig.getProperties( propertyName );
                if ( properties.size() == 1 )
                {
                    attributes.put( propertyName, properties.iterator().next().getValue() );
                }
                else
                {
                    attributes.put( propertyName, properties.stream().map( InputTypeProperty::getValue ).collect( Collectors.toSet() ) );
                }
            } );
        }
    }

    protected void resolveDefaultValue( final InputTypeDefault inputDefaultValue, final Class<T> inputType )
    {
        if ( inputDefaultValue != null )
        {
            this.defaultValue = inputDefaultValue.getValue( "default", inputType );
        }
    }

    protected String resolveInputTypeName( final Input source )
    {
        return InputTypeNameResolver.resolve( source );
    }

    private static final class InputTypeNameResolver
    {
        private static final Map<String, String> INPUT_TYPES_MAPPING = new LinkedHashMap<>();

        static
        {
            INPUT_TYPES_MAPPING.put( "textline", "TextLine" );
            INPUT_TYPES_MAPPING.put( "checkbox", "CheckBox" );
            INPUT_TYPES_MAPPING.put( "combobox", "ComboBox" );
            INPUT_TYPES_MAPPING.put( "contenttypefilter", "ContentTypeFilter" );
            INPUT_TYPES_MAPPING.put( "customselector", "CustomSelector" );
            INPUT_TYPES_MAPPING.put( "datetime", "DateTime" );
            INPUT_TYPES_MAPPING.put( "date", "Date" );
            INPUT_TYPES_MAPPING.put( "attachmentuploader", "AttachmentUploader" );
            INPUT_TYPES_MAPPING.put( "contentselector", "ContentSelector" );
            INPUT_TYPES_MAPPING.put( "double", "Double" );
            INPUT_TYPES_MAPPING.put( "geopoint", "GeoPoint" );
            INPUT_TYPES_MAPPING.put( "htmlarea", "HtmlArea" );
            INPUT_TYPES_MAPPING.put( "imageselector", "ImageSelector" );
            INPUT_TYPES_MAPPING.put( "long", "Long" );
            INPUT_TYPES_MAPPING.put( "mediaselector", "MediaSelector" );
            INPUT_TYPES_MAPPING.put( "radiobutton", "RadioButton" );
            INPUT_TYPES_MAPPING.put( "tag", "Tag" );
            INPUT_TYPES_MAPPING.put( "textarea", "TextArea" );
            INPUT_TYPES_MAPPING.put( "time", "Time" );
            INPUT_TYPES_MAPPING.put( "principalselector", "PrincipalSelector" );
        }

        static String resolve( final Input source )
        {
            return INPUT_TYPES_MAPPING.get( source.getInputType().toString().toLowerCase() );
        }
    }
}
