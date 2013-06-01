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
package eu.lp0.slf4j;

import org.junit.Assert;
import org.junit.Test;

/* Copied from http://www.slf4j.org/android/ examples */
public class AndroidTagTests {
	@Test
	public void name1() {
		Assert.assertEquals("o.e.p.MyClass", AndroidLoggerAdapter.createTag("org.example.project.MyClass"));
	}
	
	@Test
	public void name2() {
		Assert.assertEquals("o.e.p.s.MyClass", AndroidLoggerAdapter.createTag("org.example.project.subproject.MyClass"));
	}
	
	@Test
	public void name3() {
		Assert.assertEquals("oe.MyQuiteLongNamedClas", AndroidLoggerAdapter.createTag("org.example.MyQuiteLongNamedClassOfTooMuchCharacters"));
	}
	
	@Test
	public void name4() {
		Assert.assertEquals("o.e.p.s.MyClass", AndroidLoggerAdapter.createTag("o.e.project.subproject.MyClass"));
	}
	
	@Test
	public void name5() {
		Assert.assertEquals("MyQuiteLongNamedClassNo", AndroidLoggerAdapter.createTag("MyQuiteLongNamedClassNotInAPackage"));
	}
	
	@Test
	public void empty() {
		Assert.assertEquals("", AndroidLoggerAdapter.createTag(""));
	}
	
	@Test
	public void dot1() {
		Assert.assertEquals(".", AndroidLoggerAdapter.createTag("."));
	}
	
	@Test
	public void dot2() {
		Assert.assertEquals("..", AndroidLoggerAdapter.createTag(".."));
	}
	
	@Test
	public void dot3() {
		Assert.assertEquals("...", AndroidLoggerAdapter.createTag("..."));
	}
	
	@Test
	public void dot23() {
		Assert.assertEquals(".......................", AndroidLoggerAdapter.createTag("......................."));
	}
	
	@Test
	public void dot24() {
		Assert.assertEquals(".", AndroidLoggerAdapter.createTag("........................"));
	}
	
	@Test
	public void tooLong1() {
		Assert.assertEquals("tqbfjotldlidsamamnhbm.J", AndroidLoggerAdapter.createTag("the.quick.brown.fox.jumps.over.the.lazy.dog.lorem.ipsum.dolor.sit.amet.more.and.more.names.here.blah.moo.Java"));
	}
	
	@Test
	public void tooLong2() {
		Assert.assertEquals("tqbfjotldlidsamamnhbmjT", AndroidLoggerAdapter.createTag("the.quick.brown.fox.jumps.over.the.lazy.dog.lorem.ipsum.dolor.sit.amet.more.and.more.names.here.blah.moo.java.Test"));
	}
	
	@Test
	public void unusal1() {
		Assert.assertEquals(".j.l.t.c.moo", AndroidLoggerAdapter.createTag(".java.lang.test.class.moo"));
	}

	@Test
	public void unusal2() {
		Assert.assertEquals(".j.l.t.c.moo", AndroidLoggerAdapter.createTag("..java..lang..test..class..moo"));
	}
	
	@Test
	public void unusal3() {
		Assert.assertEquals(".j.l.t.c.moo", AndroidLoggerAdapter.createTag("...java...lang...test...class...moo"));
	}
	
	@Test
	public void unusal4() {
		Assert.assertEquals(".j.l.t.c.", AndroidLoggerAdapter.createTag("...java...lang...test...class..."));
	}
}
