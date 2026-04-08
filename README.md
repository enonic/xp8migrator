# xp8migrator

Migrates XP project descriptors to XP 8 format.

## Quick start

### Linux / macOS

Run this from your project directory to download the binary:

```sh
curl -fsSL https://raw.githubusercontent.com/enonic/xp8migrator/main/migrator-install.sh | sh
```

Then run the migration:

```sh
./migrator
```

### Windows

Run this from your project directory in PowerShell:

```powershell
irm https://raw.githubusercontent.com/enonic/xp8migrator/main/migrator-install.ps1 | iex
```

Then run the migration:

```powershell
.\migrator.exe
```

The `migrator` binary can be removed after successful migration.

## Usage

```
./migrator [OPTIONS] [<PROJECT_DIR>]
```

- `PROJECT_DIR` - path to the project directory. Defaults to current directory.

### Options

- `-h`, `--help` - Show help message and exit.
- `-a`, `--app-name` - Application name. Resolved from `gradle.properties` if not provided.
- `-x`, `--delete-migrated-xml` - Delete original XML files after successful migration.
- `-e`, `--on-exists` - Strategy when a target file already exists: `ask` (default), `overwrite`, `skip`.
