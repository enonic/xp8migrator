package com.enonic.xp.migrator.yaml;

import com.enonic.xp.schema.LocalizedText;

import static com.google.common.base.Strings.nullToEmpty;

public class LocalizeHelper
{
    public static LocalizedText localizeProperty( final String text, final String i18n )
    {
        if ( !nullToEmpty( text ).isEmpty() || !nullToEmpty( i18n ).isEmpty() )
        {
            return new LocalizedText( text, i18n );
        }
        return null;
    }
}
