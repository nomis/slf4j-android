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
package uk.uuid.slf4j.android;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = { "android.util.Log", "uk.uuid.slf4j.android.LoggerFactory" })
public class AutoTagTest {
	@BeforeClass
	public static void mockLog() {
		PowerMockito.mockStatic(Log.class);
	}

	@Test
	public void name1() {
		Assert.assertEquals("o.e.p.MyClass", LoggerFactory.createTag("org.example.project.MyClass"));
	}

	@Test
	public void name2() {
		Assert.assertEquals("o.e.p.s.MyClass", LoggerFactory.createTag("org.example.project.subproject.MyClass"));
	}

	@Test
	public void name3() {
		Assert.assertEquals("oe.MyQuiteLongNamedClas", LoggerFactory.createTag("org.example.MyQuiteLongNamedClassOfTooMuchCharacters"));
	}

	@Test
	public void name4() {
		Assert.assertEquals("o.e.p.s.MyClass", LoggerFactory.createTag("o.e.project.subproject.MyClass"));
	}

	@Test
	public void name5() {
		Assert.assertEquals("MyQuiteLongNamedClassNo", LoggerFactory.createTag("MyQuiteLongNamedClassNotInAPackage"));
	}

	@Test
	public void empty() {
		Assert.assertEquals("", LoggerFactory.createTag(""));
	}

	@Test
	public void dot1() {
		Assert.assertEquals(".", LoggerFactory.createTag("."));
	}

	@Test
	public void dot2() {
		Assert.assertEquals("..", LoggerFactory.createTag(".."));
	}

	@Test
	public void dot3() {
		Assert.assertEquals("...", LoggerFactory.createTag("..."));
	}

	@Test
	public void dot23() {
		Assert.assertEquals(".......................", LoggerFactory.createTag("......................."));
	}

	@Test
	public void dot24() {
		Assert.assertEquals(".", LoggerFactory.createTag("........................"));
	}

	@Test
	public void tooLong1() {
		Assert.assertEquals("tqbfjotldlidsamamnhbm.J",
				LoggerFactory.createTag("the.quick.brown.fox.jumps.over.the.lazy.dog.lorem.ipsum.dolor.sit.amet.more.and.more.names.here.blah.moo.Java"));
	}

	@Test
	public void tooLong2() {
		Assert.assertEquals("tqbfjotldlidsamamnhbmjT",
				LoggerFactory.createTag("the.quick.brown.fox.jumps.over.the.lazy.dog.lorem.ipsum.dolor.sit.amet.more.and.more.names.here.blah.moo.java.T"));
	}

	@Test
	public void tooLong3() {
		Assert.assertEquals("tqbfjotldlidsamamnhbmjT",
				LoggerFactory.createTag("the.quick.brown.fox.jumps.over.the.lazy.dog.lorem.ipsum.dolor.sit.amet.more.and.more.names.here.blah.moo.java.Test"));
	}

	@Test
	public void tooLong4() {
		Assert.assertEquals("tqbfjotldlidsamamnhbm.T",
				LoggerFactory.createTag(".the.quick.brown.fox.jumps.over.the.lazy.dog.lorem.ipsum.dolor.sit.amet.more.and.more.names.here.blah.moo.Test"));
	}

	@Test
	public void tooLong5() {
		Assert.assertEquals("tqbfjotldlidsamamnhbmjT",
				LoggerFactory.createTag(".the.quick.brown.fox.jumps.over.the.lazy.dog.lorem.ipsum.dolor.sit.amet.more.and.more.names.here.blah.moo.java.Test"));
	}

	@Test
	public void unusal1() {
		Assert.assertEquals(".j.l.t.c.moo", LoggerFactory.createTag(".java.lang.test.class.moo"));
	}

	@Test
	public void unusal2() {
		Assert.assertEquals(".j.l.t.c.moo", LoggerFactory.createTag("..java..lang..test..class..moo"));
	}

	@Test
	public void unusal3() {
		Assert.assertEquals(".j.l.t.c.moo", LoggerFactory.createTag("...java...lang...test...class...moo"));
	}

	@Test
	public void unusal4() {
		Assert.assertEquals(".j.l.t.c.", LoggerFactory.createTag("...java...lang...test...class..."));
	}
}
