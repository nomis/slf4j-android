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
package eu.lp0.slf4j.android;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * AndroidLoggerFactory is an implementation of {@link ILoggerFactory} returning
 * the appropriate named {@link LogAdapter} instance.
 * 
 * @author Simon Arlott
 */
public final class LoggerFactory implements ILoggerFactory {
	private static final Logger LOG = new LogAdapter("slf4j-android", null);
	private final ConcurrentHashMap<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();
	private final Config config = new Config(LOG);

	@Override
	public final Logger getLogger(final String name) {
		final Logger logger = loggerMap.get(name);
		if (logger != null) {
			return logger;
		} else {
			final Logger newInstance = new LogAdapter(getTag(name), config.getLevel(name));
			final Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
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
		final char[] tag = name.toCharArray();
		final int arrayLen = tag.length;
		int len = 0;
		int mark = 0;

		if (arrayLen <= MAX_TAG_LEN)
			return name;

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
				if (tag[j] == '.' && (((j & 1) == 1 && j != mark) || (i >= MAX_TAG_LEN - 1)))
					continue;

				tag[i++] = tag[j];
			}

			len = i;

			if (len > MAX_TAG_LEN) {
				len = MAX_TAG_LEN;
			}
		}

		return new String(tag, 0, len);
	}

	private final String getTag(final String name) {
		final String tag = config.getTag(name);
		if (tag != null) {
			return tag;
		}
		return createTag(name);
	}
}
