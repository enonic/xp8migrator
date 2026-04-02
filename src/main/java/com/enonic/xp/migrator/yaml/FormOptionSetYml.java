package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.enonic.xp.form.FormOptionSet;
import com.enonic.xp.form.FormOptionSetOption;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.schema.LocalizedText;

import io.micronaut.core.annotation.Introspected;

import static com.enonic.xp.migrator.yaml.LocalizeHelper.localizeProperty;

@Introspected
public class FormOptionSetYml
    extends FormItemYml
{
    public String name;

    public boolean expanded;

    public LocalizedText label;

    public LocalizedText helpText;

    public Occurrences occurrences;

    @JsonProperty("selection")
    public Occurrences multiselection;

    public List<FormOptionSetOptionYml> options;

    public FormOptionSetYml( final FormOptionSet source )
    {
        super( "OptionSet" );

        name = source.getName();
        expanded = source.isExpanded();
        label = localizeProperty( source.getLabel(), source.getLabelI18nKey() );
        helpText = localizeProperty( source.getHelpText(), source.getHelpTextI18nKey() );

        if ( source.getOccurrences() != null )
        {
            occurrences = source.getOccurrences();
        }

        if ( source.getMultiselection() != null )
        {
            multiselection = source.getMultiselection();
        }

        final Iterator<FormOptionSetOption> iterator = source.iterator();
        if ( iterator.hasNext() )
        {
            options = new ArrayList<>();
            iterator.forEachRemaining( option -> options.add( new FormOptionSetOptionYml( option ) ) );
        }
    }
}
