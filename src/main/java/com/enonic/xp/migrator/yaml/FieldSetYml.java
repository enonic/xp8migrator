package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.enonic.xp.form.FieldSet;
import com.enonic.xp.form.FormItem;
import com.enonic.xp.schema.LocalizedText;

import static com.enonic.xp.migrator.yaml.LocalizeHelper.localizeProperty;

public class FieldSetYml
    extends FormItemYml
{
    public LocalizedText label;

    public List<FormItem> items;

    public FieldSetYml( final FieldSet source )
    {
        super( "FieldSet" );

        label = localizeProperty( source.getLabel(), source.getLabelI18nKey() );

        final Iterator<FormItem> iterator = source.iterator();
        if ( iterator.hasNext() )
        {
            items = new ArrayList<>();
            iterator.forEachRemaining( items::add );
        }
    }
}
