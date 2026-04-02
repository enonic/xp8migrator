package com.enonic.xp.migrator.yaml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Form;
import com.enonic.xp.task.TaskDescriptor;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDescriptorYml
{
    public String description;

    public Form form;

    public TaskDescriptorYml( final TaskDescriptor descriptor )
    {
        this.description = descriptor.getDescription();
        this.form = descriptor.getConfig();
    }
}
