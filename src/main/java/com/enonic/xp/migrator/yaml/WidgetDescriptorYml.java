package com.enonic.xp.migrator.yaml;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.admin.widget.WidgetDescriptor;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WidgetDescriptorYml
{
    public LocalizedText displayName;

    public LocalizedText description;

    public List<String> allow;

    public List<String> interfaces;

    public Map<String, String> config;

    public WidgetDescriptorYml( final WidgetDescriptor descriptor )
    {
        displayName = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
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

        if ( descriptor.getConfig() != null && !descriptor.getConfig().isEmpty() )
        {
            config = descriptor.getConfig();
        }
    }
}
