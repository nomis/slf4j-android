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

import static eu.lp0.slf4j.android.MockUtil.mockLogLevel;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = FactoryWithLoggingTest.class, fullyQualifiedNames = { "android.util.Log", "eu.lp0.slf4j.android.LoggerFactory" })
public class FactoryWithLoggingTest {
	@BeforeClass
	public static void mockLogStatic() {
		mockStatic(Log.class);
		mockLogLevel("slf4j-android", LogLevel.VERBOSE);
	}

	@Before
	public void mockLog() {
		mockLogStatic();
	}

	@Test
	public void getLogger() {
		mockLogLevel("j.l.n.h.test1", LogLevel.INFO);
		mockLogLevel("TagTest", LogLevel.ERROR);

		// Request the logger
		Logger log1 = LoggerFactory.getLogger("java.logger.name.here.test1");
		Assert.assertEquals("java.logger.name.here.test1", log1.getName());
		Assert.assertTrue(log1.isErrorEnabled());
		Assert.assertTrue(log1.isWarnEnabled());
		Assert.assertTrue(log1.isInfoEnabled());
		Assert.assertFalse(log1.isDebugEnabled());
		Assert.assertFalse(log1.isTraceEnabled());

		// Some debug logging is expected
		verifyStatic(never());
		Log.e(eq("slf4j-android"), anyString());
		verifyStatic(never());
		Log.w(eq("slf4j-android"), anyString());
		verifyStatic(never());
		Log.i(eq("slf4j-android"), anyString());
		verifyStatic(atLeastOnce());
		Log.d(eq("slf4j-android"), anyString());
		verifyStatic(atLeastOnce());
		Log.v(eq("slf4j-android"), anyString());

		// But nothing on other tags
		verifyStatic(never());
		Log.e(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.w(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.i(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.d(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.v(not(eq("slf4j-android")), anyString());

		// or with exceptions
		verifyStatic(never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.v(anyString(), anyString(), any(Throwable.class));

		// Request the logger again
		Logger log2 = LoggerFactory.getLogger("java.logger.name.here.test1");
		Assert.assertEquals("java.logger.name.here.test1", log2.getName());
		Assert.assertTrue(log2.isErrorEnabled());
		Assert.assertTrue(log2.isWarnEnabled());
		Assert.assertTrue(log2.isInfoEnabled());
		Assert.assertFalse(log2.isDebugEnabled());
		Assert.assertFalse(log2.isTraceEnabled());

		// Request the logger yet again
		Logger log3 = LoggerFactory.getLogger("java.logger.name.here.test1");
		Assert.assertEquals("java.logger.name.here.test1", log3.getName());
		Assert.assertTrue(log3.isErrorEnabled());
		Assert.assertTrue(log3.isWarnEnabled());
		Assert.assertTrue(log3.isInfoEnabled());
		Assert.assertFalse(log3.isDebugEnabled());
		Assert.assertFalse(log3.isTraceEnabled());

		// Request another logger
		Logger log4 = LoggerFactory.getLogger("java.logger.name.here.test2");
		Assert.assertEquals("java.logger.name.here.test2", log4.getName());
		Assert.assertTrue(log4.isErrorEnabled());
		Assert.assertFalse(log4.isWarnEnabled());
		Assert.assertFalse(log4.isInfoEnabled());
		Assert.assertFalse(log4.isDebugEnabled());
		Assert.assertFalse(log4.isTraceEnabled());

		// Request the logger again
		Logger log5 = LoggerFactory.getLogger("java.logger.name.here.test2");
		Assert.assertEquals("java.logger.name.here.test2", log5.getName());
		Assert.assertTrue(log5.isErrorEnabled());
		Assert.assertFalse(log5.isWarnEnabled());
		Assert.assertFalse(log5.isInfoEnabled());
		Assert.assertFalse(log5.isDebugEnabled());
		Assert.assertFalse(log5.isTraceEnabled());

		// Request the logger yet again
		Logger log6 = LoggerFactory.getLogger("java.logger.name.here.test2");
		Assert.assertEquals("java.logger.name.here.test2", log6.getName());
		Assert.assertTrue(log6.isErrorEnabled());
		Assert.assertFalse(log6.isWarnEnabled());
		Assert.assertFalse(log6.isInfoEnabled());
		Assert.assertFalse(log6.isDebugEnabled());
		Assert.assertFalse(log6.isTraceEnabled());

		// Only debug logging is expected
		verifyStatic(never());
		Log.e(eq("slf4j-android"), anyString());
		verifyStatic(never());
		Log.w(eq("slf4j-android"), anyString());
		verifyStatic(never());
		Log.i(eq("slf4j-android"), anyString());

		// But nothing on other tags
		verifyStatic(never());
		Log.e(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.w(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.i(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.d(not(eq("slf4j-android")), anyString());
		verifyStatic(never());
		Log.v(not(eq("slf4j-android")), anyString());

		// or with exceptions
		verifyStatic(never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(never());
		Log.v(anyString(), anyString(), any(Throwable.class));

		// The loggers should be the same each time
		Assert.assertSame(log1, log2);
		Assert.assertSame(log1, log3);
		Assert.assertSame(log4, log5);
		Assert.assertSame(log4, log6);
	}
}
