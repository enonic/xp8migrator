package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.site.SiteDescriptor;
import com.enonic.xp.site.XDataMappings;

import io.micronaut.core.annotation.Introspected;

import static com.google.common.base.Strings.nullToEmpty;

@JsonPropertyOrder({"kind", "mixin", "form"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class CmsDescriptorYml
{
    public final String kind = "CMS";

    public List<XData> mixin;

    public Form form;

    public CmsDescriptorYml( final SiteDescriptor descriptor )
    {
        form = descriptor.getForm();

        final XDataMappings xDataMappings = descriptor.getXDataMappings();
        if ( xDataMappings != null && xDataMappings.isNotEmpty() )
        {
            mixin = new ArrayList<>();
            xDataMappings.forEach( xDataMapping -> {
                final XData xData = new XData();

                xData.name = xDataMapping.getXDataName().toString();
                if ( !nullToEmpty( xDataMapping.getAllowContentTypes() ).isEmpty() )
                {
                    xData.allowContentTypes = xDataMapping.getAllowContentTypes();
                }
                xData.optional = xDataMapping.getOptional();

                mixin.add( xData );
            } );
        }
    }

    @JsonPropertyOrder({"name", "allowContentTypes", "optional"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Introspected
    public static class XData
    {
        public String name;

        public String allowContentTypes;

        public boolean optional;
    }
}
