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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Assert;
import org.mockito.Mockito;

import android.util.Log;

public class MockUtil {
	/**
	 * Create a unique tag for the current test
	 */
	public static String createTag(int frames) {
		StackTraceElement ste = new CallerStackTrace(frames + 1).get();
		return ste.getClassName() + "." + ste.getMethodName();
	}

	/**
	 * Get current method name
	 */
	public static String currentMethodName() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		return ste.getMethodName();
	}

	/**
	 * Create a mock logger config for the current test
	 */
	public static LoggerConfig mockConfigDefault() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with overridden log level
	 */
	public static LoggerConfig mockConfigDefault(LogLevel override) {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.level = override;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=short
	 */
	public static LoggerConfig mockConfigShort() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.showName = LoggerConfig.ShowName.SHORT;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=short and overridden log level
	 */
	public static LoggerConfig mockConfigShort(LogLevel override) {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.level = override;
		config.showName = LoggerConfig.ShowName.SHORT;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=compact
	 */
	public static LoggerConfig mockConfigCompact() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.showName = LoggerConfig.ShowName.COMPACT;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=compact and overridden log level
	 */
	public static LoggerConfig mockConfigCompact(LogLevel override) {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.level = override;
		config.showName = LoggerConfig.ShowName.COMPACT;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=long
	 */
	public static LoggerConfig mockConfigLong() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.showName = LoggerConfig.ShowName.LONG;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=long and overridden log level
	 */
	public static LoggerConfig mockConfigLong(LogLevel override) {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.level = override;
		config.showName = LoggerConfig.ShowName.LONG;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=caller
	 */
	public static LoggerConfig mockConfigCaller() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.showName = LoggerConfig.ShowName.CALLER;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=caller and overridden log level
	 */
	public static LoggerConfig mockConfigCaller(LogLevel override) {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.level = override;
		config.showName = LoggerConfig.ShowName.CALLER;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showThread enabled
	 */
	public static LoggerConfig mockConfigThread() {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.showThread = true;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showThread enabled and overridden log level
	 */
	public static LoggerConfig mockConfigThread(LogLevel override) {
		StackTraceElement ste = new CallerStackTrace(1).get();
		String tag = ste.getClassName() + "." + ste.getMethodName();

		LoggerConfig config = new LoggerConfig();
		config.tag = tag;
		config.level = override;
		config.showThread = true;
		config.merge(LoggerConfig.DEFAULT);
		return config;
	}

	/**
	 * Mock the log level for the current test
	 */
	public static void mockLogLevel(LogLevel level) {
		when(Log.isLoggable(eq(createTag(1)), anyInt())).then(new MockLogLevelAnswer(level));
	}

	/**
	 * Mock the log level for the current test
	 */
	public static void mockLogLevelRestricted(LogLevel level) {
		when(Log.isLoggable(eq(createTag(1)), anyInt())).then(new MockLogLevelAnswer(level));

		restrictLogCalls(level);
	}

	/**
	 * Restrict logging calls to the specified level by throwing an exception for everything else
	 */
	@SuppressWarnings("unchecked")
	public static void restrictLogCalls(LogLevel level) {
		switch (level) {
		case SUPPRESS:
			Mockito.when(Log.e(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString())).thenThrow(AssertionError.class);

			Mockito.when(Log.e(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			break;

		case ERROR:
			// Mockito.when(Log.e(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString())).thenThrow(AssertionError.class);

			// Mockito.when(Log.e(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			break;

		case WARN:
			Mockito.when(Log.e(anyString(), anyString())).thenThrow(AssertionError.class);
			// Mockito.when(Log.w(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString())).thenThrow(AssertionError.class);

			Mockito.when(Log.e(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			// Mockito.when(Log.w(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			break;

		case INFO:
			Mockito.when(Log.e(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString())).thenThrow(AssertionError.class);
			// Mockito.when(Log.i(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString())).thenThrow(AssertionError.class);

			Mockito.when(Log.e(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			// Mockito.when(Log.i(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			break;

		case DEBUG:
			Mockito.when(Log.e(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString())).thenThrow(AssertionError.class);
			// Mockito.when(Log.d(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString())).thenThrow(AssertionError.class);

			Mockito.when(Log.e(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			// Mockito.when(Log.d(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.v(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			break;

		case VERBOSE:
			Mockito.when(Log.e(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString())).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString())).thenThrow(AssertionError.class);
			// Mockito.when(Log.v(anyString(), anyString())).thenThrow(AssertionError.class);

			Mockito.when(Log.e(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.w(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.i(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			Mockito.when(Log.d(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			// Mockito.when(Log.v(anyString(), anyString(), any(Throwable.class))).thenThrow(AssertionError.class);
			break;

		case NATIVE:
			Assert.fail();
			break;
		}
	}
}
