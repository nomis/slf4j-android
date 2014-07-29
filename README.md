SLF4J binding for the Android logger

* Does not support SLF4J markers, these will be ignored.
* Compatible with API version 1.

### Maven Central

* Available as eu.lp0.slf4j:slf4j-android.

### Configuration

* Loads properties from `/eu/lp0/slf4j/android/config.properties`.
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

### example use with gradle/android studio (tested with AndroidStudio 0.59 and gradle 1.11

Add these to build.gradle

   dependencies {
       compile 'eu.lp0.slf4j:slf4j-android:1.7.7-1'
       compile 'org.slf4j:slf4j-api:1.7.7@jar'
   }

the source code

* app
  * src
    * main
      * java
        * my.name.space
           * ... conraining *.java files in package folder
      * resources
        * eu.lp0.slf4j.android
          * config.properties  this file contains slf4j settings.
   
