package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Form;
import com.enonic.xp.idprovider.IdProviderDescriptor;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdProviderDescriptorYml
{
    public String mode;

    public Form form;

    public IdProviderDescriptorYml( final IdProviderDescriptor descriptor )
    {
        if ( descriptor.getMode() != null )
        {
            mode = descriptor.getMode().toString();
        }

        if ( descriptor.getConfig() != null && descriptor.getConfig().iterator().hasNext() )
        {
            form = descriptor.getConfig();
        }
    }
}
