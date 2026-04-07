package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.form.Form;
import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.migrator.yaml.mixin.FormItemSerializer;
import com.enonic.xp.migrator.yaml.mixin.FormSerializer;
import com.enonic.xp.migrator.yaml.mixin.LocalizedTextSerializer;
import com.enonic.xp.migrator.yaml.mixin.OccurrencesSerializer;
import com.enonic.xp.schema.LocalizedText;

public abstract class DescriptorMigrator
{
    private static final ObjectMapper MAPPER = new ObjectMapper(
        new YAMLFactory().disable( YAMLGenerator.Feature.WRITE_DOC_START_MARKER ).enable(
            YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR ) );

    static
    {
        final SimpleModule module = new SimpleModule();

        module.addSerializer( Form.class, new FormSerializer() );
        module.addSerializer( FormItem.class, new FormItemSerializer() );
        module.addSerializer( LocalizedText.class, new LocalizedTextSerializer() );
        module.addSerializer( Occurrences.class, new OccurrencesSerializer() );

        MAPPER.registerModule( module );
    }

    protected final ApplicationKey currentApplication;

    protected final Path resourcesDir;

    protected final Path source;

    protected DescriptorMigrator( final MigrationParams params )
    {
        this.currentApplication = params.currentApplication();
        this.resourcesDir = params.resourcesDir();
        this.source = params.source();
    }

    public void migrate()
    {
        try
        {
            final Object yaml = doMigrate( currentApplication, source );
            final Path targetPath = resolveYmlFilePath( source );
            MAPPER.writeValue( targetPath.toFile(), yaml );
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( e );
        }
    }

    public abstract Object doMigrate( ApplicationKey currentApplication, final Path source )
        throws IOException;

    public Path resolveYmlFilePath( final Path source )
        throws IOException
    {
        return source.getParent().resolve( source.getFileName().toString().replace( ".xml", ".yaml" ) );
    }
}
