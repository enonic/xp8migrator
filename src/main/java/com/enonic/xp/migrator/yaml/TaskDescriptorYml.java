package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.task.TaskDescriptor;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "description", "form"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class TaskDescriptorYml
{
    public final String kind = "Task";

    public String description;

    public Form form;

    public TaskDescriptorYml( final TaskDescriptor descriptor )
    {
        this.description = descriptor.getDescription();
        this.form = descriptor.getConfig();
    }
}
