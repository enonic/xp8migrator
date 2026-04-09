package com.enonic.xp.migrator.yaml.input;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Input;

@ReflectiveAccess
public class AttachmentUploaderYml
    extends InputYml<String>
{
    public AttachmentUploaderYml( final Input source )
    {
        super( source, String.class );
        setAttributes( source );
    }
}
