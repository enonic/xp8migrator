package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.style.ElementStyle;
import com.enonic.xp.style.ImageStyle;
import com.enonic.xp.style.StyleDescriptor;

import static com.google.common.base.Strings.nullToEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StyleDescriptorYml
{
    public final String kind = "Style";

    public String css;

    public List<Image> image;


    public StyleDescriptorYml( final StyleDescriptor styleDescriptor )
    {
        if ( !nullToEmpty( styleDescriptor.getCssPath() ).isEmpty() )
        {
            css = styleDescriptor.getCssPath();
        }

        final List<ElementStyle> elements = styleDescriptor.getElements();

        if ( elements != null && !elements.isEmpty() )
        {
            image = new ArrayList<>();

            elements.forEach( style -> {
                final ImageStyle imageStyle = (ImageStyle) style;

                final Image img = new Image();

                img.name = imageStyle.getName();
                img.displayName = LocalizeHelper.localizeProperty( imageStyle.getDisplayName(), imageStyle.getDisplayNameI18nKey() );
                img.aspectRatio = imageStyle.getAspectRatio();
                img.filter = imageStyle.getFilter();

                image.add( img );
            } );
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Image
    {
        public String name;

        public LocalizedText displayName;

        public String aspectRatio;

        public String filter;
    }
}
