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

public class CallerStackTraceTest {
	@Test
	public void unknown() {
		CallerStackTrace cst = new CallerStackTrace(-1);
		Assert.assertEquals("<unknown class>", cst.get().getClassName());
		Assert.assertEquals("<unknown method>", cst.get().getMethodName());
		Assert.assertNull(cst.get().getFileName());
		Assert.assertEquals(-1, cst.get().getLineNumber());
	}

	@Test
	public void string() {
		CallerStackTrace cst = new CallerStackTrace(0); /* Line 40 */
		Assert.assertEquals("eu.lp0.slf4j.android.CallerStackTraceTest.string(CallerStackTraceTest.java:40)", cst.toString());
	}

	@Test
	public void test0() {
		CallerStackTrace cst = new CallerStackTrace(0);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test0", cst.get().getMethodName());
	}

	private CallerStackTrace testX(int frames) {
		if (frames == 1) {
			return new CallerStackTrace(frames);
		} else {
			return testY(frames, frames - 1);
		}
	}

	private CallerStackTrace testY(int frames, int remaining) {
		if (--remaining == 0) {
			return new CallerStackTrace(frames);
		} else {
			return testY(frames, remaining);
		}
	}

	@Test
	public void test1() {
		CallerStackTrace cst = testX(1);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test1", cst.get().getMethodName());
	}

	@Test
	public void test2() {
		CallerStackTrace cst = testX(2);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test2", cst.get().getMethodName());
	}

	@Test
	public void test3() {
		CallerStackTrace cst = testX(3);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test3", cst.get().getMethodName());
	}

	@Test
	public void test4() {
		CallerStackTrace cst = testX(4);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test4", cst.get().getMethodName());
	}

	@Test
	public void test5() {
		CallerStackTrace cst = testX(5);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test5", cst.get().getMethodName());
	}

	@Test
	public void test6() {
		CallerStackTrace cst = testX(6);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test6", cst.get().getMethodName());
	}

	@Test
	public void test7() {
		CallerStackTrace cst = testX(7);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test7", cst.get().getMethodName());
	}

	@Test
	public void test8() {
		CallerStackTrace cst = testX(8);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test8", cst.get().getMethodName());
	}

	@Test
	public void test9() {
		CallerStackTrace cst = testX(9);
		Assert.assertEquals(getClass().getName(), cst.get().getClassName());
		Assert.assertEquals("test9", cst.get().getMethodName());
	}
}
