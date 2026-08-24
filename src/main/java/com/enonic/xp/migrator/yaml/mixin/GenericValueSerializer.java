package com.enonic.xp.migrator.yaml.mixin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.enonic.xp.migrator.yaml.GenericValue;

public final class GenericValueSerializer
    extends JsonSerializer<GenericValue>
{
    @Override
    public void serialize( final GenericValue value, final JsonGenerator gen, final SerializerProvider serializers )
        throws IOException
    {
        serializers.defaultSerializeValue( value.toRawJava(), gen );
    }
}
