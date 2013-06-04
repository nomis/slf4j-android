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

import java.util.HashMap;
import java.util.Map;

/**
 * Compiles a map of logger categories to be used to match logger names.
 * 
 * @author Simon Arlott
 */
final class CategoryMap {
	private final Map<String, LoggerConfig> categories = new HashMap<String, LoggerConfig>();

	CategoryMap() {
	}

	/**
	 * Returns the merged config of all matching category names for the given input, using the defaults if no categories match.
	 */
	final LoggerConfig get(String name) {
		final LoggerConfig config = new LoggerConfig();

		if (categories.isEmpty()) {
			config.merge(LoggerConfig.DEFAULT);
			return config;
		}

		if (name == null) {
			name = "";
		}

		while (true) {
			final int index = name.lastIndexOf('.');

			if (config.merge(categories.get(name)))
				return config;

			if (index != -1) {
				name = name.substring(0, index);
			} else {
				if (!config.merge(categories.get(""))) {
					config.merge(LoggerConfig.DEFAULT);
				}
				return config;
			}
		}
	}

	/**
	 * Add a category to the config map.
	 */
	final void put(final String name, final LoggerConfig value) {
		LoggerConfig config = categories.get(name);
		if (config != null) {
			config.merge(value);
		} else {
			categories.put(name, value);
		}
	}
}
