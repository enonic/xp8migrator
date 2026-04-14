package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.form.Form;
import com.enonic.xp.site.SiteDescriptor;
import com.enonic.xp.site.XDataMappings;

import static com.google.common.base.Strings.nullToEmpty;

@ReflectiveAccess
@JsonPropertyOrder({"kind", "mixins", "form"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CmsDescriptorYml
{
    public final String kind = "CMS";

    public List<XData> mixins;

    public Form form;

    public CmsDescriptorYml( final SiteDescriptor descriptor )
    {
        form = descriptor.getForm();

        final XDataMappings xDataMappings = descriptor.getXDataMappings();
        if ( xDataMappings != null && xDataMappings.isNotEmpty() )
        {
            mixins = new ArrayList<>();
            xDataMappings.forEach( xDataMapping -> {
                final XData xData = new XData();

                xData.name = xDataMapping.getXDataName().toString();
                if ( !nullToEmpty( xDataMapping.getAllowContentTypes() ).isEmpty() )
                {
                    xData.allowContentTypes = xDataMapping.getAllowContentTypes();
                }
                xData.optional = xDataMapping.getOptional();

                mixins.add( xData );
            } );
        }
    }

    @ReflectiveAccess
    @JsonPropertyOrder({"name", "allowContentTypes", "optional"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class XData
    {
        public String name;

        public String allowContentTypes;

        public boolean optional;
    }
}
