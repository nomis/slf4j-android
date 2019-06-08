/**
 * Copyright 2013,2018  Simon Arlott
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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static uk.uuid.slf4j.android.MockUtil.mockConfigDefault;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { ConfigTest.class, LoggingConfig.class }, fullyQualifiedNames = { "android.util.Log", "uk.uuid.slf4j.android.LoggerFactory" })
public class ConfigTest {
	@Mock()
	private Properties ioErrorProperties;

	@Before
	public void mockLog() {
		mockStatic(Log.class);
	}

	@Test
	public void tag_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("JavaApp", config.get(null).tag);
		Assert.assertEquals("JavaApp", config.get("").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.Socket").tag);
		Assert.assertEquals("JavaLang", config.get("java.lang.Void").tag);
		Assert.assertEquals("OOM", config.get("java.lang.OutOfMemoryError").tag);
		Assert.assertEquals("JavaLang", config.get("java.lang.reflect").tag);
		Assert.assertEquals("Reflect", config.get("java.lang.reflect.Field").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.List").tag);
		Assert.assertEquals("JavaApp", config.get("java.oops.Test").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.concurrent.locks.ReentrantReadWriteLock").tag);
		Assert.assertEquals("", config.get("javax.swing.JFrame").tag);
		Assert.assertEquals("JavaApp", config.get("java.sql.Driver").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test1").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test2.Test3").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.test1").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.more").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.more.test2").tag);
		Assert.assertEquals("JavaApp", config.get("java.test").tag);
		Assert.assertEquals("JavaApp", config.get("java.test.class").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor.igor").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor.test").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor.test.name").tag);
	}

	@Test
	public void tag_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("JavaApp", config.get(null).tag);
		Assert.assertEquals("JavaApp", config.get("").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.Socket").tag);
		Assert.assertEquals("JavaLang", config.get("java.lang.Void").tag);
		Assert.assertEquals("OOM", config.get("java.lang.OutOfMemoryError").tag);
		Assert.assertEquals("JavaLang", config.get("java.lang.reflect").tag);
		Assert.assertEquals("Reflect", config.get("java.lang.reflect.Field").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.List").tag);
		Assert.assertEquals("JavaApp", config.get("java.oops.Test").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.concurrent.locks.ReentrantReadWriteLock").tag);
		Assert.assertEquals("", config.get("javax.swing.JFrame").tag);
		Assert.assertEquals("JavaApp", config.get("java.sql.Driver").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test1").tag);
		Assert.assertEquals("Maven", config.get("org.apache.maven.Test2.Test3").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.test1").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.more").tag);
		Assert.assertEquals("JavaApp", config.get("java.net.more.test2").tag);
		Assert.assertEquals("JavaApp", config.get("java.test").tag);
		Assert.assertEquals("JavaApp", config.get("java.test.class").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor.igor").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor.test").tag);
		Assert.assertEquals("JavaApp", config.get("java.igor.test.name").tag);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	public void level_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals(LogLevel.WARN, config.get(null).level);
		Assert.assertEquals(LogLevel.WARN, config.get("").level);
		Assert.assertEquals(LogLevel.DEBUG, config.get("java.net.Socket").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.Void").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.OutOfMemoryError").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.reflect").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.reflect.Field").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.List").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.oops.Test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").level);
		Assert.assertEquals(LogLevel.WARN, config.get("javax.swing.JFrame").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.sql.Driver").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test1").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test2.Test3").level);
		Assert.assertEquals(LogLevel.INFO, config.get("java.net.test1").level);
		Assert.assertEquals(LogLevel.INFO, config.get("java.net.test1.more").level);
		Assert.assertEquals(LogLevel.ERROR, config.get("java.net.more.test2").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.test.class").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor.igor").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor.test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor.test.name").level);
	}

	@Test
	public void level_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals(LogLevel.WARN, config.get(null).level);
		Assert.assertEquals(LogLevel.WARN, config.get("").level);
		Assert.assertEquals(LogLevel.DEBUG, config.get("java.net.Socket").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.Void").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.OutOfMemoryError").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.reflect").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.reflect.Field").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.List").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.oops.Test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").level);
		Assert.assertEquals(LogLevel.WARN, config.get("javax.swing.JFrame").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.sql.Driver").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test1").level);
		Assert.assertEquals(LogLevel.VERBOSE, config.get("org.apache.maven.Test2.Test3").level);
		Assert.assertEquals(LogLevel.INFO, config.get("java.net.test1").level);
		Assert.assertEquals(LogLevel.INFO, config.get("java.net.test1.more").level);
		Assert.assertEquals(LogLevel.ERROR, config.get("java.net.more.test2").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.test.class").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor.igor").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor.test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.igor.test.name").level);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	public void showName_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get(null).showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.Socket").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.Void").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.OutOfMemoryError").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.reflect").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.reflect.Field").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.List").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.oops.Test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("javax.swing.JFrame").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.sql.Driver").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test1").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test2.Test3").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.test1").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.more").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.more.test2").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("java.test.class").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor.igor").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor.test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor.test.name").showName);
	}

	@Test
	public void showName_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get(null).showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.Socket").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.Void").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.OutOfMemoryError").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.reflect").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.reflect.Field").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.List").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.oops.Test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("javax.swing.JFrame").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.sql.Driver").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test1").showName);
		Assert.assertEquals(LoggerConfig.ShowName.CALLER, config.get("org.apache.maven.Test2.Test3").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.test1").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.more").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.more.test2").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("java.test.class").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor.igor").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor.test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.igor.test.name").showName);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	public void showThread_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals(false, config.get(null).showThread);
		Assert.assertEquals(false, config.get("").showThread);
		Assert.assertEquals(false, config.get("java.net.Socket").showThread);
		Assert.assertEquals(false, config.get("java.lang.Void").showThread);
		Assert.assertEquals(false, config.get("java.lang.OutOfMemoryError").showThread);
		Assert.assertEquals(false, config.get("java.lang.reflect").showThread);
		Assert.assertEquals(false, config.get("java.lang.reflect.Field").showThread);
		Assert.assertEquals(false, config.get("java.util.List").showThread);
		Assert.assertEquals(false, config.get("java.oops.Test").showThread);
		Assert.assertEquals(true, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showThread);
		Assert.assertEquals(false, config.get("javax.swing.JFrame").showThread);
		Assert.assertEquals(true, config.get("java.sql.Driver").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test1").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test2.Test3").showThread);
		Assert.assertEquals(false, config.get("java.net.test1").showThread);
		Assert.assertEquals(false, config.get("java.net.more").showThread);
		Assert.assertEquals(false, config.get("java.net.more.test2").showThread);
		Assert.assertEquals(false, config.get("java.test").showThread);
		Assert.assertEquals(false, config.get("java.test.class").showThread);
		Assert.assertEquals(true, config.get("java.igor").showThread);
		Assert.assertEquals(false, config.get("java.igor.igor").showThread);
		Assert.assertEquals(true, config.get("java.igor.test").showThread);
		Assert.assertEquals(false, config.get("java.igor.test.name").showThread);
	}

	@Test
	public void showThread_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals(false, config.get(null).showThread);
		Assert.assertEquals(false, config.get("").showThread);
		Assert.assertEquals(false, config.get("java.net.Socket").showThread);
		Assert.assertEquals(false, config.get("java.lang.Void").showThread);
		Assert.assertEquals(false, config.get("java.lang.OutOfMemoryError").showThread);
		Assert.assertEquals(false, config.get("java.lang.reflect").showThread);
		Assert.assertEquals(false, config.get("java.lang.reflect.Field").showThread);
		Assert.assertEquals(false, config.get("java.util.List").showThread);
		Assert.assertEquals(false, config.get("java.oops.Test").showThread);
		Assert.assertEquals(true, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showThread);
		Assert.assertEquals(false, config.get("javax.swing.JFrame").showThread);
		Assert.assertEquals(true, config.get("java.sql.Driver").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test1").showThread);
		Assert.assertEquals(true, config.get("org.apache.maven.Test2.Test3").showThread);
		Assert.assertEquals(false, config.get("java.net.test1").showThread);
		Assert.assertEquals(false, config.get("java.net.more").showThread);
		Assert.assertEquals(false, config.get("java.net.more.test2").showThread);
		Assert.assertEquals(false, config.get("java.test").showThread);
		Assert.assertEquals(false, config.get("java.test.class").showThread);
		Assert.assertEquals(true, config.get("java.igor").showThread);
		Assert.assertEquals(false, config.get("java.igor.igor").showThread);
		Assert.assertEquals(true, config.get("java.igor.test").showThread);
		Assert.assertEquals(false, config.get("java.igor.test.name").showThread);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	public void noConfigDefaults_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("noConfig.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void noConfigDefaults_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("noConfig.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	public void invalidDefaults_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest2.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void invalidDefaults_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest2.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);

		// Some debug logging is expected, and warnings for the invalid values
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, times(6)); /* There are 3 values that can have invalid values and they are tested twice */
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	public void ioErrorPropertiesFileDefaults_NoLogging() throws Exception {
		PowerMockito.whenNew(Properties.class).withAnyArguments().thenReturn(ioErrorProperties);
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(InputStream.class));
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(Reader.class));

		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);
	}

	@Test
	public void ioErrorPropertiesFileDefaults_WithLogging() throws Exception {
		PowerMockito.whenNew(Properties.class).withAnyArguments().thenReturn(ioErrorProperties);
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(InputStream.class));
		Mockito.doThrow(IOException.class).when(ioErrorProperties).load(Mockito.any(Reader.class));

		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("", config.get(null).tag);
		Assert.assertEquals(LogLevel.NATIVE, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get(null).showName);
		Assert.assertEquals(false, config.get(null).showThread);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// And an error with an exception
		verifyStatic(Log.class);
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	/**
	 * Config file in old location only.
	 */
	public void oldConfig_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("oldConfigTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("OldConfig", config.get(null).tag);
	}

	@Test
	/**
	 * Config file in old location only.
	 */
	public void oldConfig_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("oldConfigTest1.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("OldConfig", config.get(null).tag);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}

	@Test
	/**
	 * Config file in both new and old locations.
	 */
	public void newConfig_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("oldConfigTest2.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("NewConfig", config.get(null).tag);
	}

	@Test
	/**
	 * Config file in both new and old locations.
	 */
	public void newConfig_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("oldConfigTest2.properties", new LogAdapter("N/A", mockConfigDefault()));

		Assert.assertEquals("NewConfig", config.get(null).tag);

		// Some debug logging is expected
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString());
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString());
		verifyStatic(Log.class, atLeastOnce());
		Log.d(anyString(), anyString());

		// But nothing with exceptions
		verifyStatic(Log.class, never());
		Log.e(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.w(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.i(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.d(anyString(), anyString(), any(Throwable.class));
		verifyStatic(Log.class, never());
		Log.v(anyString(), anyString(), any(Throwable.class));
	}
}
