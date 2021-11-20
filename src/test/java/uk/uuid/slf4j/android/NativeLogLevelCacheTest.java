/**
 * Copyright 2013,2021  Simon Arlott
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

import static uk.uuid.slf4j.android.MockUtil.mockLogLevel;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = NativeLogLevelCacheTest.class, fullyQualifiedNames = { "android.util.Log" })
public class NativeLogLevelCacheTest {
	@Before
	public void mockLog() {
		mockStatic(Log.class);
	}

	@Test
	public void change() {
		// Set the native log level
		mockLogLevel("tag1", LogLevel.DEBUG);
		mockLogLevel("tag2", LogLevel.INFO);
		mockLogLevel("tag3", LogLevel.DEBUG);
		mockLogLevel("tag4", LogLevel.INFO);

		// The level for tag1 is DEBUG
		{
			LoggerConfig config = new LoggerConfig("tag1");
			config.merge(LoggerConfig.DEFAULT);

			Logger log = new LogAdapter("test1", config);
			Assert.assertTrue(log.isErrorEnabled());
			Assert.assertTrue(log.isWarnEnabled());
			Assert.assertTrue(log.isInfoEnabled());
			Assert.assertTrue(log.isDebugEnabled());
			Assert.assertFalse(log.isTraceEnabled());
		}

		// The level for tag2 is INFO
		{
			LoggerConfig config = new LoggerConfig("tag2");
			config.merge(LoggerConfig.DEFAULT);

			Logger log = new LogAdapter("test2", config);
			Assert.assertTrue(log.isErrorEnabled());
			Assert.assertTrue(log.isWarnEnabled());
			Assert.assertTrue(log.isInfoEnabled());
			Assert.assertFalse(log.isDebugEnabled());
			Assert.assertFalse(log.isTraceEnabled());
		}

		// Change the used native log levels
		mockLogLevel("tag1", LogLevel.WARN);
		mockLogLevel("tag2", LogLevel.VERBOSE);

		// The level for tag1 is still DEBUG
		{
			LoggerConfig config = new LoggerConfig("tag1");
			config.merge(LoggerConfig.DEFAULT);

			Logger log = new LogAdapter("test3", config);
			Assert.assertTrue(log.isErrorEnabled());
			Assert.assertTrue(log.isWarnEnabled());
			Assert.assertTrue(log.isInfoEnabled());
			Assert.assertTrue(log.isDebugEnabled());
			Assert.assertFalse(log.isTraceEnabled());
		}

		// The level for tag2 is still INFO
		{
			LoggerConfig config = new LoggerConfig("tag2");
			config.merge(LoggerConfig.DEFAULT);

			Logger log = new LogAdapter("test4", config);
			Assert.assertTrue(log.isErrorEnabled());
			Assert.assertTrue(log.isWarnEnabled());
			Assert.assertTrue(log.isInfoEnabled());
			Assert.assertFalse(log.isDebugEnabled());
			Assert.assertFalse(log.isTraceEnabled());
		}

		// Change the unused native log levels
		mockLogLevel("tag3", LogLevel.SUPPRESS);
		mockLogLevel("tag4", LogLevel.ERROR);

		// The level for tag3 is SUPPRESS
		{
			LoggerConfig config = new LoggerConfig("tag3");
			config.merge(LoggerConfig.DEFAULT);

			Logger log = new LogAdapter("test5", config);
			Assert.assertFalse(log.isErrorEnabled());
			Assert.assertFalse(log.isWarnEnabled());
			Assert.assertFalse(log.isInfoEnabled());
			Assert.assertFalse(log.isDebugEnabled());
			Assert.assertFalse(log.isTraceEnabled());
		}

		// The level for tag4 is ERROR
		{
			LoggerConfig config = new LoggerConfig("tag4");
			config.merge(LoggerConfig.DEFAULT);

			Logger log = new LogAdapter("test6", config);
			Assert.assertTrue(log.isErrorEnabled());
			Assert.assertFalse(log.isWarnEnabled());
			Assert.assertFalse(log.isInfoEnabled());
			Assert.assertFalse(log.isDebugEnabled());
			Assert.assertFalse(log.isTraceEnabled());
		}
	}
}
