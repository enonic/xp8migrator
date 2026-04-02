package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.app.ApplicationDescriptor;

@JsonPropertyOrder({"kind", "description"})
public class ApplicationDescriptorYml
{
    public final String kind = "Application";

    public String description;

    public ApplicationDescriptorYml( final ApplicationDescriptor source )
    {
        this.description = source.getDescription();
    }
}
