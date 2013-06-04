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
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;

/**
 * Loads properties from {@code /eu/lp0/slf4j/android/config.properties}.
 * 
 * <dl>
 * <dt>{@code tag}<strong>.logger.package.name</strong><strong>.logger.class.name</strong>{@code =}<strong>TagName</strong></dt>
 * <dd>Set the tag for the specified logger prefix.</dd>
 * <dt>{@code level}<strong>.logger.package.name</strong><strong>.logger.class.name</strong>{@code =}<strong>LEVEL</strong></dt>
 * <dd>Override the {@linkplain LogLevel log level} for the specified logger prefix.</dd>
 * </dl>
 * 
 * With no configuration, logger names are automatically compacted to fit the Android 23 character tag limit.
 * 
 * @author Simon Arlott
 */
public final class Config {
	private final CategoryList<String> tag = new CategoryList<String>();
	private final CategoryList<LogLevel> level = new CategoryList<LogLevel>();

	Config(final Logger log) {
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
						tag.put("", value);
					}
				} else if (key.charAt(3) == '.') {
					if (value.isEmpty() || value.length() > LoggerFactory.MAX_TAG_LEN) {
						log.warn("Ignoring invalid tag {} for {}", value, key.substring(4));
					} else {
						tag.put(key.substring(4), value);
					}
				}
			} else if (key.startsWith("level")) {
				if (key.length() == 5) {
					try {
						level.put("", LogLevel.valueOf(value));
					} catch (IllegalArgumentException e) {
						log.warn("Ignoring invalid default log level {}", value);
					}
				} else if (key.charAt(5) == '.') {
					try {
						level.put(key.substring(6), LogLevel.valueOf(value));
					} catch (IllegalArgumentException e) {
						log.warn("Ignoring invalid log level {} for {}", value, key.substring(6));
					}
				}
			}
		}
	}

	final String getTag(String name) {
		return tag.get(name);
	}

	final LogLevel getLevel(String name) {
		return level.get(name);
	}
}
