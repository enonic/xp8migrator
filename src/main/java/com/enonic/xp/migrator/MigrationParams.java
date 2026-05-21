package com.enonic.xp.migrator;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import com.enonic.xp.app.ApplicationKey;

public record MigrationParams(ApplicationKey currentApplication, Path resourcesDir, Path source, AtomicReference<OnExistsStrategy> onExists)
{
}
