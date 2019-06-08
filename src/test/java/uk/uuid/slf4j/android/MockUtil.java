/**
 * Copyright 2013,2016  Simon Arlott
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Assert;
import org.mockito.Mockito;

import android.util.Log;

public class MockUtil {
	private static final LoggerConfig DEFAULT_CONFIG = new LoggerConfig();
	static {
		DEFAULT_CONFIG.merge(LoggerConfig.DEFAULT);
	}

	private static final LoggerConfig SHORT_CONFIG = new LoggerConfig();
	static {
		SHORT_CONFIG.showName = LoggerConfig.ShowName.SHORT;
		SHORT_CONFIG.merge(DEFAULT_CONFIG);
	}

	private static final LoggerConfig COMPACT_CONFIG = new LoggerConfig();
	static {
		COMPACT_CONFIG.showName = LoggerConfig.ShowName.COMPACT;
		COMPACT_CONFIG.merge(DEFAULT_CONFIG);
	}

	private static final LoggerConfig LONG_CONFIG = new LoggerConfig();
	static {
		LONG_CONFIG.showName = LoggerConfig.ShowName.LONG;
		LONG_CONFIG.merge(DEFAULT_CONFIG);
	}

	private static final LoggerConfig CALLER_CONFIG = new LoggerConfig();
	static {
		CALLER_CONFIG.showName = LoggerConfig.ShowName.CALLER;
		CALLER_CONFIG.merge(DEFAULT_CONFIG);
	}

	private static final LoggerConfig THREAD_CONFIG = new LoggerConfig();
	static {
		THREAD_CONFIG.showThread = true;
		THREAD_CONFIG.merge(DEFAULT_CONFIG);
	}

	private static final LoggerConfig THREAD_SHORT_CONFIG = new LoggerConfig();
	static {
		THREAD_SHORT_CONFIG.showName = LoggerConfig.ShowName.SHORT;
		THREAD_SHORT_CONFIG.merge(THREAD_CONFIG);
	}

	private static final LoggerConfig THREAD_COMPACT_CONFIG = new LoggerConfig();
	static {
		THREAD_COMPACT_CONFIG.showName = LoggerConfig.ShowName.COMPACT;
		THREAD_COMPACT_CONFIG.merge(THREAD_CONFIG);
	}

	private static final LoggerConfig THREAD_LONG_CONFIG = new LoggerConfig();
	static {
		THREAD_LONG_CONFIG.showName = LoggerConfig.ShowName.LONG;
		THREAD_LONG_CONFIG.merge(THREAD_CONFIG);
	}

	private static final LoggerConfig THREAD_CALLER_CONFIG = new LoggerConfig();
	static {
		THREAD_CALLER_CONFIG.showName = LoggerConfig.ShowName.CALLER;
		THREAD_CALLER_CONFIG.merge(THREAD_CONFIG);
	}

	/**
	 * Create a unique tag for the current test
	 */
	public static String createTag(int frames) {
		StackTraceElement ste = new CallerStackTrace(frames + 1).get();
		return ste.getClassName() + "___" + ste.getMethodName();
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
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(DEFAULT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with overridden log level
	 */
	public static LoggerConfig mockConfigDefault(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(DEFAULT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=short
	 */
	public static LoggerConfig mockConfigShort() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(SHORT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=short and overridden log level
	 */
	public static LoggerConfig mockConfigShort(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(SHORT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=compact
	 */
	public static LoggerConfig mockConfigCompact() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(COMPACT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=compact and overridden log level
	 */
	public static LoggerConfig mockConfigCompact(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(COMPACT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=long
	 */
	public static LoggerConfig mockConfigLong() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(LONG_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=long and overridden log level
	 */
	public static LoggerConfig mockConfigLong(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(LONG_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=caller
	 */
	public static LoggerConfig mockConfigCaller() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(CALLER_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=caller and overridden log level
	 */
	public static LoggerConfig mockConfigCaller(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(CALLER_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showThread=true enabled
	 */
	public static LoggerConfig mockConfigThread() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(THREAD_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showThread=true and overridden log level
	 */
	public static LoggerConfig mockConfigThread(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(THREAD_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=short and showThread=true
	 */
	public static LoggerConfig mockConfigThreadShort() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(THREAD_SHORT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=short, showThread enabled and overridden log level
	 */
	public static LoggerConfig mockConfigThreadShort(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(THREAD_SHORT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=compact and showThread=true
	 */
	public static LoggerConfig mockConfigThreadCompact() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(THREAD_COMPACT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=compact, showThread enabled and overridden log level
	 */
	public static LoggerConfig mockConfigThreadCompact(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(THREAD_COMPACT_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=long and showThread=true
	 */
	public static LoggerConfig mockConfigThreadLong() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(THREAD_LONG_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=long, showThread enabled and overridden log level
	 */
	public static LoggerConfig mockConfigThreadLong(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(THREAD_LONG_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=caller and showThread=true
	 */
	public static LoggerConfig mockConfigThreadCaller() {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.merge(THREAD_CALLER_CONFIG);
		return config;
	}

	/**
	 * Create a mock logger config for the current test with showName=caller, showThread enabled and overridden log level
	 */
	public static LoggerConfig mockConfigThreadCaller(LogLevel override) {
		LoggerConfig config = new LoggerConfig(createTag(1));
		config.level = override;
		config.merge(THREAD_CALLER_CONFIG);
		return config;
	}

	/**
	 * Mock the log level for the current test
	 */
	public static void mockLogLevel(LogLevel level) {
		mockLogLevel(createTag(1), level);
	}

	/**
	 * Mock the log level for a specified tag
	 */
	public static void mockLogLevel(String tag, LogLevel level) {
		when(Log.isLoggable(eq(tag), anyInt())).then(new MockLogLevelAnswer(level));
	}

	/**
	 * Mock the log level for the current test, and restrict logging calls to only that level
	 */
	public static void mockLogLevelRestricted(LogLevel level) {
		mockLogLevelRestricted(createTag(1), level);
	}

	/**
	 * Mock the log level for a specified tag, and restrict logging calls to only that level
	 */
	public static void mockLogLevelRestricted(String tag, LogLevel level) {
		when(Log.isLoggable(eq(tag), anyInt())).then(new MockLogLevelAnswer(level));

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

	/**
	 * Replicate some native behaviour
	 */
	@SuppressWarnings("unchecked")
	public static void mockNativeBehaviour() {
		// Not used
		Mockito.when(Log.getStackTraceString(any(Throwable.class))).thenThrow(AssertionError.class);
		Mockito.when(Log.println(anyInt(), anyString(), anyString())).thenThrow(AssertionError.class);

		// Throws NPEs
		Mockito.when(Log.e(anyString(), isNull(String.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.e(anyString(), isNull(String.class), any(Throwable.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.w(anyString(), isNull(String.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.w(anyString(), isNull(String.class), any(Throwable.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.i(anyString(), isNull(String.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.i(anyString(), isNull(String.class), any(Throwable.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.d(anyString(), isNull(String.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.d(anyString(), isNull(String.class), any(Throwable.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.v(anyString(), isNull(String.class))).thenThrow(NullPointerException.class);
		Mockito.when(Log.v(anyString(), isNull(String.class), any(Throwable.class))).thenThrow(NullPointerException.class);
	}

	/**
	 * Check that no messages were logged
	 */
	public static void verifyNoLog() {
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), Mockito.any(Throwable.class));

		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), Mockito.any(Throwable.class));

		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), Mockito.any(Throwable.class));

		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), Mockito.any(Throwable.class));

		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), Mockito.any(Throwable.class));
	}
}
