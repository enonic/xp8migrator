package com.enonic.xp.migrator.yaml;

import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.app.ApplicationDescriptor;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "title", "description", "url", "vendorName", "vendorUrl"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class ApplicationDescriptorYml
{
    public final String kind = "Application";

    public String title;

    public String description;

    public String url;

    public String vendorName;

    public String vendorUrl;

    public ApplicationDescriptorYml( final ApplicationDescriptor source, final Properties gradleProperties )
    {
        this.description = source.getDescription();
        this.title = trimToNull( gradleProperties.getProperty( "appDisplayName" ) );
        this.url = trimToNull( gradleProperties.getProperty( "appUrl" ) );
        this.vendorName = trimToNull( gradleProperties.getProperty( "vendorName" ) );
        this.vendorUrl = trimToNull( gradleProperties.getProperty( "vendorUrl" ) );
    }

    private static String trimToNull( final String value )
    {
        if ( value == null )
        {
            return null;
        }
        final String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
