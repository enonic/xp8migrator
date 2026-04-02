package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.stream.Collectors;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.webapp.WebappDescriptor;

public class WebappDescriptorYml
{
    public List<String> apis;

    public WebappDescriptorYml( final ApplicationKey currentApplication, final WebappDescriptor descriptor )
    {
        if ( descriptor.getApiMounts() != null && !descriptor.getApiMounts().isEmpty() )
        {
            final String prefix = currentApplication + ":";
            apis = descriptor.getApiMounts().stream().map( DescriptorKey::toString ).map(
                api -> api.startsWith( prefix ) ? api.replace( prefix, "" ) : api ).collect( Collectors.toList() );
        }
    }
}
