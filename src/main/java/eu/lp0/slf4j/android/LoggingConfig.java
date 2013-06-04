/**
 * Copyright 2013  Simon Arlott
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.lp0.slf4j.android;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * Loads properties from {@code /eu/lp0/slf4j/android/config.properties}.
 * 
 * <p>
 * Configuration can be applied per logger prefix or set the default by omitting the logger prefix: 
 * <dl>
 * <dt>{@code tag.*=TagName}</dt>
 * <dd>Set the tag for the specified logger prefix.</dd>
 * <dt>{@code level.*=LEVEL}</dt>
 * <dd>Override the {@linkplain LogLevel log level} for the specified logger prefix.</dd>
 * <dt>{@code showName.*=FALSE|SHORT|LONG}</dt>
 * <dd>Show the logger name in short or long format for the specified logger prefix.</dd>
 * <dt>{@code showThread.*=true|false}</dt>
 * <dd>Show the current thread for the specified logger prefix.</dd>
 * </dl>
 * 
 * With no configuration, logger names are automatically compacted to fit the Android 23 character tag limit.
 * The default configuration does not show the logger name or the current thread. 
 * 
 * @author Simon Arlott
 */
public final class LoggingConfig {
	private final CategoryMap map = new CategoryMap();

	LoggingConfig(final Logger log) {
		final Properties props = new Properties();
		final URL url = getClass().getResource("config.properties");

		if (url != null) {
			log.debug("Loading properties file from {}", url);

			try {
				props.load(new InputStreamReader(url.openStream()));
			} catch (IOException e) {
				log.error("Error loading properties file from {}", url, e);
				props.clear();
			}
		}
		
		for (final Entry<Object, Object> entry : props.entrySet()) {
			final String key = (String)entry.getKey();
			final String value = (String)entry.getValue();

			if (key.startsWith("tag")) {
				if (key.length() == 3) {
					if (value.isEmpty() || value.length() > LoggerFactory.MAX_TAG_LEN) {
						log.warn("Ignoring invalid default tag {}", value);
					} else {
						map.put("", new LoggerConfig(value));
					}
				} else if (key.charAt(3) == '.') {
					if (value.isEmpty() || value.length() > LoggerFactory.MAX_TAG_LEN) {
						log.warn("Ignoring invalid tag {} for {}", value, key.substring(4));
					} else {
						map.put(key.substring(4), new LoggerConfig(value));
					}
				}
			} else if (key.startsWith("level")) {
				if (key.length() == 5) {
					try {
						map.put("", new LoggerConfig(LogLevel.valueOf(value.toUpperCase(Locale.ROOT))));
					} catch (IllegalArgumentException e) {
						log.warn("Ignoring invalid default log level {}", value);
					}
				} else if (key.charAt(5) == '.') {
					try {
						map.put(key.substring(6), new LoggerConfig(LogLevel.valueOf(value.toUpperCase(Locale.ROOT))));
					} catch (IllegalArgumentException e) {
						log.warn("Ignoring invalid log level {} for {}", value, key.substring(6));
					}
				}
			} else if (key.startsWith("showName")) {
				if (key.length() == 8) {
					try {
						map.put("", new LoggerConfig(LoggerConfig.ShowName.valueOf(value.toUpperCase(Locale.ROOT))));
					} catch (IllegalArgumentException e) {
						log.warn("Ignoring invalid default show name setting {}", value);
					}
				} else if (key.charAt(8) == '.') {
					try {
						map.put(key.substring(9), new LoggerConfig(LoggerConfig.ShowName.valueOf(value.toUpperCase(Locale.ROOT))));
					} catch (IllegalArgumentException e) {
						log.warn("Ignoring invalid show name setting {} for {}", value, key.substring(9));
					}
				}
			} else if (key.startsWith("showThread")) {
				if (key.length() == 10) {
					LoggerConfig config = new LoggerConfig();
					config.showThread = Boolean.valueOf(value);
					map.put("", config);
				} else if (key.charAt(10) == '.') {
					LoggerConfig config = new LoggerConfig();
					config.showThread = Boolean.valueOf(value);
					map.put(key.substring(11), config);
				}
			}
		}
	}

	final LoggerConfig get(String name) {
		return map.get(name);
	}
}
