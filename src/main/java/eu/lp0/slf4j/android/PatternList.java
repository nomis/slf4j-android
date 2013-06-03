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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Compiles a list of Ant-style patterns to be used to match strings.  
 * 
 * @author Simon Arlott
 */
final class PatternList<V> {
	private final Map<Pattern, V> patterns = new LinkedHashMap<Pattern, V>();

	PatternList() {
	}

	/**
	 * Returns the first matching pattern for the given input, or {code null} if no patterns match.
	 */
	final V get(final String name) {
		for (final Map.Entry<Pattern, V> entry : patterns.entrySet()) {
			if (entry.getKey().matcher(name).matches())
				return entry.getValue();
		}
		return null;
	}

	/**
	 * Append a pattern to the list of patterns to check. 
	 */
	final void put(final String name, final V value) {
		patterns.put(makeAntPattern(name), value);
	}

	/**
	 * Convert an Ant-style pattern into a regular expression.
	 */
	static final Pattern makeAntPattern(final String input) {
		final char[] match = input.toCharArray();
		final int arrayLen = match.length;
		final StringBuilder sb = new StringBuilder(arrayLen + 16);
		boolean escaped = false;

		for (int i = 0; i < arrayLen; i++) {
			switch (match[i]) {
			case '*':
				if (escaped) {
					sb.append("\\E");
					escaped = false;
				}
				if (i + 1 < arrayLen && match[i + 1] == '*') {
					sb.append(".*");
					i++;
				} else {
					sb.append("[^.]*");
				}
				break;
				
			case '?':
				if (escaped) {
					sb.append("\\E");
					escaped = false;
				}
				sb.append(".");
				break;
				
			case '\\':
				if (escaped) {
					sb.append("\\E\\\\\\Q");
				} else {
					sb.append("\\");
				}
				break;
				
			default:
				if (!escaped) {
					sb.append("\\Q");
					escaped = true;
				}
				sb.append(match[i]);
				break;
			}
		}

		return Pattern.compile(sb.toString());
	}
}
