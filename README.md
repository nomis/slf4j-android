SLF4J binding for the Android logger

* Does not support SLF4J markers, these will be ignored.
* Compatible with API version 1.

### Configuration

* Loads properties from `/eu/lp0/slf4j/android/config.properties`.
    * Set the tag for the specified logger prefix: `tag.logger.package.name.logger.class.name=TagName`
    * Override the log level for the specified logger prefix: `level.logger.package.name.logger.class.name=LEVEL`
* With no configuration, logger names are automatically compacted to fit the Android 23 character tag limit. 
