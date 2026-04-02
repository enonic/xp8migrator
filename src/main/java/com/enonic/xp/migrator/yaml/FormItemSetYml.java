package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.FormItemSet;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.schema.LocalizedText;

import static com.enonic.xp.migrator.yaml.LocalizeHelper.localizeProperty;

public class FormItemSetYml
    extends FormItemYml
{
    public String name;

    public LocalizedText label;

    public LocalizedText helpText;

    public Occurrences occurrences;

    public List<FormItem> items;

    public FormItemSetYml( final FormItemSet source )
    {
        super( "ItemSet" );

        name = source.getName();
        label = localizeProperty( source.getLabel(), source.getLabelI18nKey() );
        helpText = localizeProperty( source.getHelpText(), source.getHelpTextI18nKey() );

        if ( source.getOccurrences() != null )
        {
            occurrences = source.getOccurrences();
        }

        final Iterator<FormItem> iterator = source.iterator();
        if ( iterator.hasNext() )
        {
            items = new ArrayList<>();
            iterator.forEachRemaining( items::add );
        }
    }
}
