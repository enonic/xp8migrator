package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.admin.tool.AdminToolDescriptor;
import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.descriptor.DescriptorKeys;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "title", "description", "allow", "apis", "interfaces"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class AdminToolDescriptorYml
{
    public final String kind = "AdminTool";

    public LocalizedText title;

    public LocalizedText description;

    public List<String> allow;

    public List<String> apis;

    public List<String> interfaces;

    public AdminToolDescriptorYml( final ApplicationKey currentApplication, final AdminToolDescriptor descriptor )
    {
        title = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );

        final PrincipalKeys allowedPrincipals = descriptor.getAllowedPrincipals();
        if ( allowedPrincipals != null && allowedPrincipals.isNotEmpty() )
        {
            allow = allowedPrincipals.stream().map( PrincipalKey::toString ).collect( Collectors.toList() );
        }

        final DescriptorKeys apiMounts = descriptor.getApiMounts();
        if ( apiMounts != null && apiMounts.isNotEmpty() )
        {
            final String prefix = currentApplication + ":";
            apis = apiMounts.stream().map( DescriptorKey::toString ).map(
                api -> api.startsWith( prefix ) ? api.replace( prefix, "" ) : api ).collect( Collectors.toList() );
        }

        final Set<String> interfaceList = descriptor.getInterfaces();
        if ( interfaceList != null && !interfaceList.isEmpty() )
        {
            interfaces = interfaceList.stream().toList();
        }
    }
}
