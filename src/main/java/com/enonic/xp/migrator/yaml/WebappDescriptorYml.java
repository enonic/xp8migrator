package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.webapp.WebappDescriptor;

@ReflectiveAccess
@JsonPropertyOrder({"kind", "apis"})
public class WebappDescriptorYml
{
    public final String kind = "WebApp";

    public List<String> apis;

    public WebappDescriptorYml( final ApplicationKey currentApplication, final WebappDescriptor descriptor )
    {
        if ( descriptor.getApiMounts() != null && !descriptor.getApiMounts().isEmpty() )
        {
            final String prefix = currentApplication + ":";
            apis = descriptor.getApiMounts()
                .stream()
                .map( DescriptorKey::toString )
                .map( api -> api.startsWith( prefix ) ? api.replace( prefix, "" ) : api )
                .collect( Collectors.toList() );
        }
    }
}
