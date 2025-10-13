package com.enonic.xp.migrator.yml.mixin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.enonic.xp.form.Occurrences;

public class OccurrencesSerializer
    extends JsonSerializer<Occurrences>
{
    @Override
    public void serialize( final Occurrences occurrences, final JsonGenerator gen, final SerializerProvider serializers )
        throws IOException
    {
        gen.writeStartObject();
        gen.writeNumberField( "min", occurrences.getMinimum() );
        gen.writeNumberField( "max", occurrences.getMaximum() );
        gen.writeEndObject();
    }
}
