package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.api.ApiDescriptor;
import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;

@JsonPropertyOrder({"kind", "title", "description", "documentationUrl", "allow", "mount"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiDescriptorYml
{
    public final String kind = "API";

    public List<String> allow;

    public String title;

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
        title = descriptor.getDisplayName();
        description = descriptor.getDescription();
        documentationUrl = descriptor.getDocumentationUrl();
        mount = descriptor.isMount() ? Set.of( "xp" ) : null;
    }
}
