package com.enonic.xp.migrator.yaml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.micronaut.core.annotation.ReflectiveAccess;

import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.style.ElementStyle;
import com.enonic.xp.style.ImageStyle;
import com.enonic.xp.style.StyleDescriptor;

import static com.google.common.base.Strings.nullToEmpty;

@ReflectiveAccess
@JsonPropertyOrder({"kind", "styles"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StyleDescriptorYml
{
    public final String kind = "Style";

    public List<Image> styles;

    public StyleDescriptorYml( final StyleDescriptor styleDescriptor )
    {
        final String css =
            !nullToEmpty( styleDescriptor.getCssPath() ).isEmpty() ? String.format( "Please migrate your styles from \"%s\" manually",
                                                                                    styleDescriptor.getCssPath() ) : "";
        final Editor editor = new Editor();
        editor.css = css;

        final List<ElementStyle> elements = styleDescriptor.getElements();

        if ( elements != null && !elements.isEmpty() )
        {
            styles = new ArrayList<>();

            elements.forEach( style -> {
                final ImageStyle imageStyle = (ImageStyle) style;

                final Image img = new Image();

                img.type = "Image";
                img.name = imageStyle.getName();
                img.label = LocalizeHelper.localizeProperty( imageStyle.getDisplayName(), imageStyle.getDisplayNameI18nKey() );
                img.aspectRatio = imageStyle.getAspectRatio();
                img.filter = imageStyle.getFilter();
                img.editor = editor;

                styles.add( img );
            } );
        }
    }

    @ReflectiveAccess
    @JsonPropertyOrder({"name", "type", "label", "aspectRatio", "filter", "editor"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Image
    {
        public String name;

        public String type;

        public LocalizedText label;

        public String aspectRatio;

        public String filter;

        public Editor editor;
    }

    @ReflectiveAccess
    @JsonPropertyOrder({"css"})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Editor
    {
        public String css;
    }
}
