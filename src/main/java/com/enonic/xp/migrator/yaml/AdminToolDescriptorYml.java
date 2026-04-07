package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.admin.tool.AdminToolDescriptor;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;

@ReflectiveAccess
@JsonPropertyOrder({"kind", "title", "description", "allow", "interfaces"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminToolDescriptorYml
{
    public final String kind = "AdminTool";

    public LocalizedText title;

    public LocalizedText description;

    public List<String> allow;

    public List<String> interfaces;

    public AdminToolDescriptorYml( final AdminToolDescriptor descriptor )
    {
        title = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );

        final PrincipalKeys allowedPrincipals = descriptor.getAllowedPrincipals();
        if ( allowedPrincipals != null && allowedPrincipals.isNotEmpty() )
        {
            allow = allowedPrincipals.stream().map( PrincipalKey::toString ).collect( Collectors.toList() );
        }

        final Set<String> interfaceList = descriptor.getInterfaces();
        if ( interfaceList != null && !interfaceList.isEmpty() )
        {
            interfaces = interfaceList.stream().toList();
        }
    }
}
