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

import static uk.uuid.slf4j.android.MockUtil.createTag;
import static uk.uuid.slf4j.android.MockUtil.mockLogLevel;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = MockUtilTest.class, fullyQualifiedNames = "android.util.Log")
public class MockUtilTest {
	@Before
	public void mockLog() {
		mockStatic(Log.class);
	}

	@Test
	public void testMethodName1() {
		Assert.assertEquals("testMethodName1", MockUtil.currentMethodName());
	}

	@Test
	public void testMethodName2() {
		Assert.assertEquals("testMethodName2", MockUtil.currentMethodName());
	}

	@Test
	public void testMethodName3() {
		Assert.assertEquals("testMethodName3", MockUtil.currentMethodName());
	}

	@Test
	public void testTagName1() {
		Assert.assertEquals("uk.uuid.slf4j.android.MockUtilTest___testTagName1", MockUtil.createTag(0));
	}

	@Test
	public void testTagName2() {
		Assert.assertEquals("uk.uuid.slf4j.android.MockUtilTest___testTagName2", MockUtil.createTag(0));
	}

	@Test
	public void testTagName3() {
		Assert.assertEquals("uk.uuid.slf4j.android.MockUtilTest___testTagName3", MockUtil.createTag(0));
	}

	@Test
	public void testNativeLevelSuppress() {
		mockLogLevel(LogLevel.SUPPRESS);

		Assert.assertFalse(Log.isLoggable(createTag(0), Log.ERROR));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.WARN));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.INFO));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.DEBUG));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.VERBOSE));
	}

	@Test
	public void testNativeLevelError() {
		mockLogLevel(LogLevel.ERROR);

		Assert.assertTrue(Log.isLoggable(createTag(0), Log.ERROR));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.WARN));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.INFO));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.DEBUG));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.VERBOSE));
	}

	@Test
	public void testNativeLevelWarn() {
		mockLogLevel(LogLevel.WARN);

		Assert.assertTrue(Log.isLoggable(createTag(0), Log.ERROR));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.WARN));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.INFO));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.DEBUG));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.VERBOSE));
	}

	@Test
	public void testNativeLevelInfo() {
		mockLogLevel(LogLevel.INFO);

		Assert.assertTrue(Log.isLoggable(createTag(0), Log.ERROR));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.WARN));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.INFO));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.DEBUG));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.VERBOSE));
	}

	@Test
	public void testNativeLevelDebug() {
		mockLogLevel(LogLevel.DEBUG);

		Assert.assertTrue(Log.isLoggable(createTag(0), Log.ERROR));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.WARN));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.INFO));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.DEBUG));
		Assert.assertFalse(Log.isLoggable(createTag(0), Log.VERBOSE));
	}

	@Test
	public void testNativeLevelVerbose() {
		mockLogLevel(LogLevel.VERBOSE);

		Assert.assertTrue(Log.isLoggable(createTag(0), Log.ERROR));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.WARN));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.INFO));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.DEBUG));
		Assert.assertTrue(Log.isLoggable(createTag(0), Log.VERBOSE));
	}
}
