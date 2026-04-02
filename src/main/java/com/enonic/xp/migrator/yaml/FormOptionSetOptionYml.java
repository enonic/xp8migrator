package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.FormOptionSetOption;
import com.enonic.xp.schema.LocalizedText;

import static com.enonic.xp.migrator.yaml.LocalizeHelper.localizeProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormOptionSetOptionYml
{
    public String name;

    public LocalizedText label;

    public LocalizedText helpText;

    public boolean defaultOption;

    public List<FormItem> items;

    public FormOptionSetOptionYml( final FormOptionSetOption source )
    {
        name = source.getName();
        defaultOption = source.isDefaultOption();
        label = localizeProperty( source.getLabel(), source.getLabelI18nKey() );
        helpText = localizeProperty( source.getHelpText(), source.getHelpTextI18nKey() );

        final Iterator<FormItem> iterator = source.iterator();
        if ( iterator.hasNext() )
        {
            items = new ArrayList<>();
            iterator.forEachRemaining( item -> items.add( item ) );
        }
    }

}
