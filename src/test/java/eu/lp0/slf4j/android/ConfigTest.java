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
import org.mockito.Mockito;
import org.slf4j.Logger;

public class ConfigTest {
	@Test
	public void tag() {
		LoggingConfig config = new LoggingConfig(Mockito.mock(Logger.class, Mockito.withSettings().verboseLogging()));
		Assert.assertEquals("JavaApp", config.get("java.net.Socket").tag);
		Assert.assertEquals("JavaLang", config.get("java.lang.Void").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.List").tag);
		Assert.assertEquals("JavaApp", config.get("java.oops.Test").tag);
		Assert.assertEquals("JavaUtil", config.get("java.util.concurrent.locks.ReentrantReadWriteLock").tag);
		Assert.assertEquals("", config.get("javax.swing.JFrame").tag);
	}

	@Test
	public void level() {
		LoggingConfig config = new LoggingConfig(Mockito.mock(Logger.class, Mockito.withSettings().verboseLogging()));
		Assert.assertEquals(LogLevel.DEBUG, config.get("java.net.Socket").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.lang.Void").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.List").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.oops.Test").level);
		Assert.assertEquals(LogLevel.WARN, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").level);
		Assert.assertEquals(LogLevel.WARN, config.get("javax.swing.JFrame").level);
	}

	@Test
	public void showName() {
		LoggingConfig config = new LoggingConfig(Mockito.mock(Logger.class, Mockito.withSettings().verboseLogging()));
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.net.Socket").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.lang.Void").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.List").showName);
		Assert.assertEquals(LoggerConfig.ShowName.SHORT, config.get("java.oops.Test").showName);
		Assert.assertEquals(LoggerConfig.ShowName.FALSE, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showName);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("javax.swing.JFrame").showName);
	}

	@Test
	public void showThread() {
		LoggingConfig config = new LoggingConfig(Mockito.mock(Logger.class, Mockito.withSettings().verboseLogging()));
		Assert.assertEquals(false, config.get("java.net.Socket").showThread);
		Assert.assertEquals(false, config.get("java.lang.Void").showThread);
		Assert.assertEquals(false, config.get("java.util.List").showThread);
		Assert.assertEquals(false, config.get("java.oops.Test").showThread);
		Assert.assertEquals(true, config.get("java.util.concurrent.locks.ReentrantReadWriteLock").showThread);
		Assert.assertEquals(false, config.get("javax.swing.JFrame").showThread);
	}
}
