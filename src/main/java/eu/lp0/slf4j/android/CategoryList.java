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
 * Compiles a list of Ant-style patterns to be used to match strings.  
 * 
 * @author Simon Arlott
 */
final class CategoryList<V> {
	private final Map<String, V> categories = new HashMap<String, V>();

	CategoryList() {
	}

	/**
	 * Returns the first matching category name for the given input, or {code null} if no categories match.
	 */
	final V get(String name) {
		V value;
		
		if (categories.isEmpty())
			return null;
		
		value = categories.get(name);
		
		while (value == null) {
			final int index = name.lastIndexOf('.');
			
			if (index == -1) {
				return categories.get("");
			} else {
				name = name.substring(0, index);
			}
			
			value = categories.get(name);
		}
		
		return value;
	}

	/**
	 * Append a pattern to the list of patterns to check. 
	 */
	final void put(final String name, final V value) {
		categories.put(name, value);
	}
}
