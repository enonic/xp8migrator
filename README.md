# xp8migrator

Build jar

```
./gradlew shadowJar
```

Usage

```
java -jar ./build/libs/migrator.jar <PROJECT_DIR> [<APP_NAME>]
```

1. `PROJECT_DIR` - path to the project directory. This is a required argument.
2. `APP_NAME` - application name. This argument is optional if a `gradle.properties` file with the `appName` property exists; otherwise, it must be provided as the second argument.
