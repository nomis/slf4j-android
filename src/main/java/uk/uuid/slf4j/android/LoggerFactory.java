/**
 * Copyright 2004-2011  QOS.ch
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
package uk.uuid.slf4j.android;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * AndroidLoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriate named {@link LogAdapter} instance.
 * 
 * @author Simon Arlott
 */
public final class LoggerFactory implements ILoggerFactory {
	private static final Logger LOG;
	private static final boolean TRACE;
	static {
		LoggerConfig config = new LoggerConfig("slf4j-android");
		config.showThread = true;
		config.merge(LoggerConfig.DEFAULT);
		LOG = new LogAdapter("uk.uuid.slf4j.android", config);
		TRACE = LOG.isTraceEnabled();
	}

	private final ConcurrentMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();
	private final LoggingConfig loggingConfig = new LoggingConfig(LoggingConfig.DEFAULT_FILENAME, LOG);

	@Override
	public final Logger getLogger(final String name) {
		final long start = TRACE ? System.nanoTime() : 0;
		final Logger logger = loggerMap.get(name);
		if (logger != null) {
			if (TRACE) {
				final long stop = System.nanoTime();
				LOG.trace("Found logger {} in {}µs", name, TimeUnit.NANOSECONDS.toMicros(stop - start));
			}
			return logger;
		} else {
			final Logger newInstance = new LogAdapter(name, getConfig(name));
			final Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
			if (TRACE) {
				final long stop = System.nanoTime();
				if (oldInstance == null) {
					LOG.trace("Created logger {} in {}µs", name, TimeUnit.NANOSECONDS.toMicros(stop - start));
				} else {
					LOG.trace("Found existing logger {} in {}µs", name, TimeUnit.NANOSECONDS.toMicros(stop - start));
				}
			}
			return oldInstance == null ? newInstance : oldInstance;
		}
	}

	/**
	 * Maximum length of a tag in the Android logging system.
	 * 
	 * This constant is not defined in the API but longer tags will cause exceptions in native code.
	 */
	static final int MAX_TAG_LEN = 23;

	/**
	 * Create a compatible logging tag for Android based on the logger name.
	 */
	static final String createTag(final String name) {
		if (name.length() <= MAX_TAG_LEN) {
			return name;
		}

		final char[] tag = name.toCharArray();
		final int arrayLen = tag.length;
		int len = 0;
		int mark = 0;

		for (int i = 0; i < arrayLen; i++, len++) {
			if (tag[i] == '.') {
				len = mark;

				if (tag[len] != '.') {
					len++;
				}

				mark = len;

				if (i + 1 < arrayLen && tag[i + 1] != '.') {
					mark++;
				}
			}

			tag[len] = tag[i];
		}

		if (len > MAX_TAG_LEN) {
			int i = 0;

			mark--;

			for (int j = 0; j < len; j++) {
				if (tag[j] == '.' && ((j != mark) || (i >= MAX_TAG_LEN - 1))) {
					continue;
				}

				tag[i++] = tag[j];
			}

			len = i;

			if (len > MAX_TAG_LEN) {
				len = MAX_TAG_LEN;
			}
		}

		return new String(tag, 0, len);
	}

	private final LoggerConfig getConfig(final String name) {
		final long start = TRACE ? System.nanoTime() : 0;
		final LoggerConfig config = loggingConfig.get(name);

		if (config.tag.length() == 0) {
			config.tag = createTag(name);
			if (TRACE) {
				LOG.trace("Created tag {} for {}", config.tag, name);
			}
		}

		if (TRACE) {
			final long stop = System.nanoTime();
			LOG.trace("Retrieved config for {} in {}µs", name, TimeUnit.NANOSECONDS.toMicros(stop - start));
		}

		return config;
	}
}
