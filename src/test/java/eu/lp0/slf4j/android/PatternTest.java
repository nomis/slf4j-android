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

import org.junit.Assert;
import org.junit.Test;


public class PatternTest {
	@Test
	public void all() {
		CategoryList<String> matcher = new CategoryList<String>();
		matcher.put("", "all");
		
		Assert.assertEquals("all", matcher.get(""));
		Assert.assertEquals("all", matcher.get("test1"));
		Assert.assertEquals("all", matcher.get("test1.test2"));
		Assert.assertEquals("all", matcher.get("test1.test2.test3"));
	}
	
	@Test
	public void none() {
		CategoryList<String> matcher = new CategoryList<String>();
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
	}
	
	@Test
	public void name() {
		CategoryList<String> matcher = new CategoryList<String>();
		matcher.put("java.lang", "name");
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
		Assert.assertEquals("name", matcher.get("java.lang"));
		Assert.assertEquals("name", matcher.get("java.lang.Void"));
		Assert.assertEquals("name", matcher.get("java.lang.String"));
		Assert.assertEquals("name", matcher.get("java.lang.ref.PhantomReference"));
	}
}
