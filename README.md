# xp8migrator

Migrates XP project descriptors to XP8 format.

## Download

Download the latest native binary for your platform from [Releases](../../releases/latest).

## Usage

```
./migrator [OPTIONS] <PROJECT_DIR> [<APP_NAME>]
```

1. `PROJECT_DIR` - path to the project directory. This is a required argument.
2. `APP_NAME` - application name. This argument is optional if a `gradle.properties` file with the `appName` property exists; otherwise, it must be provided as the second argument.

### Options

- `-x`, `--delete-migrated-xml` - Delete original XML files after successful migration.
