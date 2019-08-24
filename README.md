SLF4J binding for the Android logger

* Does not support SLF4J markers, these will be ignored.
* Compatible with API version 1.

### Configuration

* Loads properties from `/uk/uuid/slf4j/android/config.properties`.
* Configuration can be applied per logger prefix or set the default by omitting the logger prefix.
    * Set the tag for the specified logger prefix:
      `tag.logger-prefix=TagName`
    * Set the log level for the specified logger prefix:
      `level.logger-prefix=SUPPRESS|ERROR|WARN|INFO|DEBUG|VERBOSE|NATIVE`
    * Show the logger name in short, compact, long format, or show caller stack frame:
      `showName.logger-prefix=false|short|compact|long|caller`
    * Show the current thread:
      `showThread.*=true|false`
* With no tag configured, logger names are automatically compacted to fit the Android 23 character tag limit. 
* The default configuration does not show the logger name or the current thread.
* The default log level is NATIVE (use the Android log level for the tag).

#### Example Configuration File

``` properties
tag=ExampleApplication
showThread=true
showName=short
```

### Maven Central

* Available as `uk.uuid.slf4j:slf4j-android`.
* The configuration file when using the standard project layout should be located at `src/main/resources/uk/uuid/slf4j/android/config.properties`.
* Note: There is a bug in [android-maven-plugin](https://code.google.com/p/maven-android-plugin/issues/detail?id=365) that makes the runtime scope unusable.

#### Maven Usage Example
pom.xml:

``` xml
<dependency>
   <groupId>org.slf4j</groupId>
   <artifactId>slf4j-api</artifactId>
   <version>1.7.28</version>
</dependency>

<dependency>
   <groupId>uk.uuid.slf4j</groupId>
   <artifactId>slf4j-android</artifactId>
   <version>1.7.28-0</version>
</dependency>
```

#### Gradle Example
build.gradle:

``` groovy
dependencies {
   compile 'org.slf4j:slf4j-api:1.7.28'
   compile 'uk.uuid.slf4j:slf4j-android:1.7.28-0'
}
```
