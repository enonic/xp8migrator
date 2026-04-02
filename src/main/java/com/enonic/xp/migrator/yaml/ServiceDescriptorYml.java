package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;
import com.enonic.xp.service.ServiceDescriptor;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDescriptorYml
{
    public final String kind = "Service";

    public List<String> allow;

    public ServiceDescriptorYml( final ServiceDescriptor descriptor )
    {
        final PrincipalKeys allowedPrincipals = descriptor.getAllowedPrincipals();
        if ( allowedPrincipals != null && allowedPrincipals.isNotEmpty() )
        {
            allow = allowedPrincipals.stream().map( PrincipalKey::toString ).collect( Collectors.toList() );
        }
    }
}
