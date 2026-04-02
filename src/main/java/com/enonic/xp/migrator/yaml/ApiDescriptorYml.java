package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.api.ApiDescriptor;
import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiDescriptorYml
{
    public final String kind = "API";

    public List<String> allow;

    public String displayName;

    public String description;

    public String documentationUrl;

    public Set<String> mount;

    public ApiDescriptorYml( final ApiDescriptor descriptor )
    {
        final PrincipalKeys allowedPrincipals = descriptor.getAllowedPrincipals();
        if ( allowedPrincipals != null && allowedPrincipals.isNotEmpty() )
        {
            allow = allowedPrincipals.stream().map( PrincipalKey::toString ).collect( Collectors.toList() );
        }
        displayName = descriptor.getDisplayName();
        description = descriptor.getDescription();
        documentationUrl = descriptor.getDocumentationUrl();
        mount = descriptor.isMount() ? Set.of( "xp" ) : Set.of();
    }
}
