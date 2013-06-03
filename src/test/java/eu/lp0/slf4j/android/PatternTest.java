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
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("**", "all");
		
		Assert.assertEquals("all", matcher.get(""));
		Assert.assertEquals("all", matcher.get("test1"));
		Assert.assertEquals("all", matcher.get("test1.test2"));
		Assert.assertEquals("all", matcher.get("test1.test2.test3"));
	}
	
	@Test
	public void none() {
		PatternList<String> matcher = new PatternList<String>();
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
	}
	
	@Test
	public void empty() {
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("", "empty");
		
		Assert.assertEquals("empty", matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
	}
	
	@Test
	public void single() {
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("?", "single");
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
		Assert.assertEquals("single", matcher.get("a"));
		Assert.assertEquals("single", matcher.get("b"));
		Assert.assertEquals("single", matcher.get("c"));
	}
	
	@Test
	public void word() {
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("*", "word");
		
		Assert.assertEquals("word", matcher.get(""));
		Assert.assertEquals("word", matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
		Assert.assertEquals("word", matcher.get("a"));
		Assert.assertEquals("word", matcher.get("b"));
		Assert.assertEquals("word", matcher.get("c"));
	}
	
	@Test
	public void name1() {
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("java.lang.*", "name1");
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
		Assert.assertEquals(null, matcher.get("java.lang"));
		Assert.assertEquals("name1", matcher.get("java.lang.Void"));
		Assert.assertEquals("name1", matcher.get("java.lang.String"));
		Assert.assertEquals(null, matcher.get("java.lang.ref.PhantomReference"));
	}
	
	@Test
	public void name2() {
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("java.lang.**", "name2");
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
		Assert.assertEquals(null, matcher.get("java.lang"));
		Assert.assertEquals("name2", matcher.get("java.lang.Void"));
		Assert.assertEquals("name2", matcher.get("java.lang.String"));
		Assert.assertEquals("name2", matcher.get("java.lang.ref.PhantomReference"));
	}
	
	@Test
	public void name3() {
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("j?v?.lang.**", "name3");
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
		Assert.assertEquals(null, matcher.get("java.lang"));
		Assert.assertEquals("name3", matcher.get("java.lang.Void"));
		Assert.assertEquals("name3", matcher.get("java.lang.String"));
		Assert.assertEquals("name3", matcher.get("java.lang.ref.PhantomReference"));
	}
	
	@Test
	public void name4() {
		PatternList<String> matcher = new PatternList<String>();
		matcher.put("java.*.*e**", "name4");
		
		Assert.assertEquals(null, matcher.get(""));
		Assert.assertEquals(null, matcher.get("test1"));
		Assert.assertEquals(null, matcher.get("test1.test2"));
		Assert.assertEquals(null, matcher.get("test1.test2.test3"));
		Assert.assertEquals(null, matcher.get("java.lang"));
		Assert.assertEquals(null, matcher.get("java.lang.Void"));
		Assert.assertEquals(null, matcher.get("java.lang.String"));
		Assert.assertEquals("name4", matcher.get("java.lang.ref.PhantomReference"));
		Assert.assertEquals("name4", matcher.get("java.lang.Integer"));
	}
}
