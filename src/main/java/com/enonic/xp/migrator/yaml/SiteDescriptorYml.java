package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.descriptor.DescriptorKeys;
import com.enonic.xp.site.SiteDescriptor;
import com.enonic.xp.site.mapping.ControllerMappingDescriptors;
import com.enonic.xp.site.processor.ResponseProcessorDescriptors;

import io.micronaut.core.annotation.Introspected;

@JsonPropertyOrder({"kind", "processors", "mappings", "apis"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Introspected
public class SiteDescriptorYml
{
    public final String kind = "Site";

    public List<Processor> processors;

    public List<Mapping> mappings;

    public List<String> apis;

    public SiteDescriptorYml( final ApplicationKey currentApplication, final SiteDescriptor descriptor )
    {
        final ResponseProcessorDescriptors responseProcessors = descriptor.getResponseProcessors();
        if ( responseProcessors != null && responseProcessors.isNotEmpty() )
        {
            processors = new ArrayList<>();

            responseProcessors.forEach( p -> {
                final Processor processor = new Processor();
                processor.name = p.getName();
                processor.order = p.getOrder();
                processors.add( processor );
            } );
        }

        final ControllerMappingDescriptors mappingDescriptors = descriptor.getMappingDescriptors();
        if ( mappingDescriptors != null && mappingDescriptors.isNotEmpty() )
        {
            mappings = new ArrayList<>();

            mappingDescriptors.forEach( p -> {
                final Mapping mapping = new Mapping();

                if ( p.getFilter() != null )
                {
                    mapping.filter = p.getFilter().getPath();
                }
                if ( p.getController() != null )
                {
                    mapping.controller = p.getController().getPath();
                }
                if ( p.getContentConstraint() != null )
                {
                    mapping.match = p.getContentConstraint().toString();
                }
                else
                {
                    if ( p.getPattern() != null )
                    {
                        mapping.pattern = p.getPattern().pattern();
                    }
                    mapping.invertPattern = p.invertPattern();
                }

                mapping.service = p.getService();
                mapping.order = p.getOrder();

                mappings.add( mapping );
            } );
        }

        final DescriptorKeys apiMounts = descriptor.getApiMounts();
        if ( apiMounts != null && apiMounts.isNotEmpty() )
        {
            final String prefix = currentApplication + ":";
            apis = apiMounts.stream().map( DescriptorKey::toString ).map(
                api -> api.startsWith( prefix ) ? api.replace( prefix, "" ) : api ).collect( Collectors.toList() );
        }
    }

    @JsonPropertyOrder({"name", "order"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Introspected
    public static class Processor
    {
        public String name;

        public Integer order;
    }

    @JsonPropertyOrder({"controller", "filter", "service", "order", "match", "pattern", "invertPattern"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Introspected
    public static class Mapping
    {
        public String filter;

        public String service;

        public String controller;

        public Integer order;

        public Boolean invertPattern;

        public String pattern;

        public String match;
    }
}
