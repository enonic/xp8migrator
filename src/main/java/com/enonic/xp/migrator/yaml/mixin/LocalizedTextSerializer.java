package com.enonic.xp.migrator.yaml.mixin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.enonic.xp.schema.LocalizedText;

public final class LocalizedTextSerializer
    extends JsonSerializer<LocalizedText>
{
    @Override
    public void serialize( final LocalizedText value, final JsonGenerator gen, final SerializerProvider serializers )
        throws IOException
    {
        if ( value.i18n() == null || value.i18n().isEmpty() )
        {
            gen.writeString( value.text() );
        }
        else
        {
            gen.writeStartObject();
            gen.writeStringField( "text", value.text() );
            gen.writeStringField( "i18n", value.i18n() );
            gen.writeEndObject();
        }
    }
}
