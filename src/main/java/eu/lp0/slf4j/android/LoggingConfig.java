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
import java.net.URL;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

/**
 * Loads properties from {@code /eu/lp0/slf4j/android/config.properties}.
 * 
 * @author Simon Arlott
 */
final class LoggingConfig {
	public static final String DEFAULT_FILENAME = "config.properties";
	private final CategoryMap map = new CategoryMap();

	LoggingConfig(final String configFileName, final Logger log) {
		final long start = log.isTraceEnabled() ? System.nanoTime() : 0;
		final Properties props = new Properties();
		final URL url = getClass().getResource(configFileName);

		if (url != null) {
			log.debug("Loading properties file from {}", url);

			try {
				props.load(url.openStream());
			} catch (IOException e) {
				log.error("Error loading properties file from {}", url, e);
				props.clear();
			}
		} else {
			log.debug("No config file");
		}

		for (final Entry<Object, Object> entry : props.entrySet()) {
			String key = (String)entry.getKey();
			final String value = (String)entry.getValue();

			if (key.startsWith("tag")) {
				if (key.length() == 3) {
					key = "";
				} else if (key.charAt(3) == '.') {
					key = key.substring(4);
				} else {
					continue;
				}

				if (value.length() > LoggerFactory.MAX_TAG_LEN) {
					if (key.length() == 0) {
						log.warn("Ignoring invalid default tag {}", value);
					} else {
						log.warn("Ignoring invalid tag {} for {}", value, key);
					}
				} else {
					map.put(key, new LoggerConfig(value));
				}
			} else if (key.startsWith("level")) {
				if (key.length() == 5) {
					key = "";
				} else if (key.charAt(5) == '.') {
					key = key.substring(6);
				} else {
					continue;
				}

				try {
					map.put(key, new LoggerConfig(LogLevel.valueOf(value.toUpperCase(Locale.ENGLISH))));
				} catch (IllegalArgumentException e) {
					if (key.length() == 0) {
						log.warn("Ignoring invalid default log level {}", value);
					} else {
						log.warn("Ignoring invalid log level {} for {}", value, key);
					}
				}
			} else if (key.startsWith("showName")) {
				if (key.length() == 8) {
					key = "";
				} else if (key.charAt(8) == '.') {
					key = key.substring(9);
				} else {
					continue;
				}

				try {
					map.put(key, new LoggerConfig(LoggerConfig.ShowName.valueOf(value.toUpperCase(Locale.ENGLISH))));
				} catch (IllegalArgumentException e) {
					if (key.length() == 0) {
						log.warn("Ignoring invalid default show name setting {}", value);
					} else {
						log.warn("Ignoring invalid show name setting {} for {}", value, key);
					}
				}
			} else if (key.startsWith("showThread")) {
				if (key.length() == 10) {
					key = "";
				} else if (key.charAt(10) == '.') {
					key = key.substring(11);
				} else {
					continue;
				}

				LoggerConfig config = new LoggerConfig();
				config.showThread = Boolean.valueOf(value);
				map.put(key, config);
			}
		}

		if (log.isTraceEnabled()) {
			final long stop = System.nanoTime();
			log.trace("Config processing completed in {}µs", TimeUnit.NANOSECONDS.toMicros(stop - start));
		}
	}

	final LoggerConfig get(String name) {
		return map.get(name);
	}
}
