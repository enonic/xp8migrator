package com.enonic.xp.migrator.yml.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

public class ComboBoxYml
    extends InputYml<String>
{
    public List<OptionYml> options;

    public ComboBoxYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        final Set<InputTypeProperty> values = inputTypeConfig.getProperties( "option" );

        if ( !values.isEmpty() )
        {
            options = new ArrayList<>();

            values.forEach( option -> {
                final OptionYml optionYml = new OptionYml();

                optionYml.addAttribute( "value", option.getAttribute( "value" ) );
                if ( option.getAttribute( "i18n" ) != null )
                {
                    optionYml.addAttribute( "label", Map.of( "text", option.getValue(), "i18n", option.getAttribute( "i18n" ) ) );
                }
                else
                {
                    optionYml.addAttribute( "label", option.getValue() );
                }
                option.getAttributes().forEach( ( key, value ) -> {
                    if ( !"value".equals( key ) && !"i18n".equals( key ) )
                    {
                        optionYml.addAttribute( key, value );
                    }
                } );

                options.add( optionYml );
            } );
        }

        setAttributes( source, "option" );
    }

}
