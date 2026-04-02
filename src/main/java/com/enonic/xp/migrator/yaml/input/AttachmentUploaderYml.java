package com.enonic.xp.migrator.yaml.input;

import com.enonic.xp.form.Input;

public class AttachmentUploaderYml
    extends InputYml<String>
{
    public AttachmentUploaderYml( final Input source )
    {
        super( source, String.class );
        setAttributes( source );
    }
}
