package com.enonic.xp.migrator.yaml.mixin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.enonic.xp.form.Form;
import com.enonic.xp.form.FormItem;

public class FormSerializer
    extends JsonSerializer<Form>
{
    @Override
    public void serialize( final Form form, final JsonGenerator gen, final SerializerProvider serializers )
        throws IOException
    {
        gen.writeStartArray();
        for ( FormItem item : form )
        {
            gen.writeObject( item );
        }
        gen.writeEndArray();
    }
}
