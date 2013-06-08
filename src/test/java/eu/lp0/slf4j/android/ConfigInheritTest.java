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

import static eu.lp0.slf4j.android.MockUtil.mockConfig;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = ConfigInheritTest.class, fullyQualifiedNames = { "android.util.Log", "eu.lp0.slf4j.android.LoggerFactory" })
public class ConfigInheritTest {
	@Before
	public void mockLog() {
		mockStatic(Log.class);
	}

	@Test
	public void default_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get(null).tag);
		Assert.assertEquals(LogLevel.INFO, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get(null).showName);
		Assert.assertEquals(true, config.get(null).showThread);

		Assert.assertEquals("DefaultTag", config.get("").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("").showName);
		Assert.assertEquals(true, config.get("").showThread);

		Assert.assertEquals("DefaultTag", config.get("test").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test").showName);
		Assert.assertEquals(true, config.get("test").showThread);
	}

	@Test
	public void default_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get(null).tag);
		Assert.assertEquals(LogLevel.INFO, config.get(null).level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get(null).showName);
		Assert.assertEquals(true, config.get(null).showThread);

		Assert.assertEquals("DefaultTag", config.get("").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("").showName);
		Assert.assertEquals(true, config.get("").showThread);

		Assert.assertEquals("DefaultTag", config.get("test").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test").showName);
		Assert.assertEquals(true, config.get("test").showThread);
	}

	/* 0000 */

	@Test
	public void TLNT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLNT", config.get("test.TLNT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TLNT").showName);
		Assert.assertEquals(false, config.get("test.TLNT").showThread);
	}

	@Test
	public void TLNT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLNT", config.get("test.TLNT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TLNT").showName);
		Assert.assertEquals(false, config.get("test.TLNT").showThread);
	}

	/* 1000 */

	@Test
	public void tLNT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLNT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tLNT").showName);
		Assert.assertEquals(false, config.get("test.tLNT").showThread);
	}

	@Test
	public void tLNT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLNT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tLNT").showName);
		Assert.assertEquals(false, config.get("test.tLNT").showThread);
	}

	/* 0100 */

	@Test
	public void TlNT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TlNT", config.get("test.TlNT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.TlNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TlNT").showName);
		Assert.assertEquals(false, config.get("test.TlNT").showThread);
	}

	@Test
	public void TlNT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TlNT", config.get("test.TlNT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.TlNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TlNT").showName);
		Assert.assertEquals(false, config.get("test.TlNT").showThread);
	}

	/* 1100 */

	@Test
	public void tlNT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlNT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tlNT").showName);
		Assert.assertEquals(false, config.get("test.tlNT").showThread);
	}

	@Test
	public void tlNT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlNT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlNT").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tlNT").showName);
		Assert.assertEquals(false, config.get("test.tlNT").showThread);
	}

	/* 0010 */

	@Test
	public void TLnT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLnT", config.get("test.TLnT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.TLnT").showName);
		Assert.assertEquals(false, config.get("test.TLnT").showThread);
	}

	@Test
	public void TLnT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLnT", config.get("test.TLnT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.TLnT").showName);
		Assert.assertEquals(false, config.get("test.TLnT").showThread);
	}

	/* 1010 */

	@Test
	public void tLnT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLnT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tLnT").showName);
		Assert.assertEquals(false, config.get("test.tLnT").showThread);
	}

	@Test
	public void tLnT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLnT").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tLnT").showName);
		Assert.assertEquals(false, config.get("test.tLnT").showThread);
	}

	/* 0110 */

	@Test
	public void TlnT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TlnT", config.get("test.TlnT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.TlnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.TlnT").showName);
		Assert.assertEquals(false, config.get("test.TlnT").showThread);
	}

	@Test
	public void TlnT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TlnT", config.get("test.TlnT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.TlnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.TlnT").showName);
		Assert.assertEquals(false, config.get("test.TlnT").showThread);
	}

	/* 1110 */

	@Test
	public void tlnT_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlnT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tlnT").showName);
		Assert.assertEquals(false, config.get("test.tlnT").showThread);
	}

	@Test
	public void tlnT_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlnT").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlnT").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tlnT").showName);
		Assert.assertEquals(false, config.get("test.tlnT").showThread);
	}

	/* 0001 */

	@Test
	public void TLNt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLNt", config.get("test.TLNt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TLNt").showName);
		Assert.assertEquals(true, config.get("test.TLNt").showThread);
	}

	@Test
	public void TLNt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLNt", config.get("test.TLNt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TLNt").showName);
		Assert.assertEquals(true, config.get("test.TLNt").showThread);
	}

	/* 1001 */

	@Test
	public void tLNt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLNt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tLNt").showName);
		Assert.assertEquals(true, config.get("test.tLNt").showThread);
	}

	@Test
	public void tLNt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLNt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tLNt").showName);
		Assert.assertEquals(true, config.get("test.tLNt").showThread);
	}

	/* 0101 */

	@Test
	public void TlNt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TlNt", config.get("test.TlNt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.TlNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TlNt").showName);
		Assert.assertEquals(true, config.get("test.TlNt").showThread);
	}

	@Test
	public void TlNt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TlNt", config.get("test.TlNt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.TlNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.TlNt").showName);
		Assert.assertEquals(true, config.get("test.TlNt").showThread);
	}

	/* 1101 */

	@Test
	public void tlNt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlNt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tlNt").showName);
		Assert.assertEquals(true, config.get("test.tlNt").showThread);
	}

	@Test
	public void tlNt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlNt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlNt").level);
		Assert.assertEquals(LoggerConfig.ShowName.LONG, config.get("test.tlNt").showName);
		Assert.assertEquals(true, config.get("test.tlNt").showThread);
	}

	/* 0011 */

	@Test
	public void TLnt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLnt", config.get("test.TLnt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.TLnt").showName);
		Assert.assertEquals(true, config.get("test.TLnt").showThread);
	}

	@Test
	public void TLnt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("TLnt", config.get("test.TLnt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.TLnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.TLnt").showName);
		Assert.assertEquals(true, config.get("test.TLnt").showThread);
	}

	/* 1011 */

	@Test
	public void tLnt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLnt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tLnt").showName);
		Assert.assertEquals(true, config.get("test.tLnt").showThread);
	}

	@Test
	public void tLnt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tLnt").tag);
		Assert.assertEquals(LogLevel.WARN, config.get("test.tLnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tLnt").showName);
		Assert.assertEquals(true, config.get("test.tLnt").showThread);
	}

	/* 0111 */

	@Test
	public void Tlnt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("Tlnt", config.get("test.Tlnt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.Tlnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.Tlnt").showName);
		Assert.assertEquals(true, config.get("test.Tlnt").showThread);
	}

	@Test
	public void Tlnt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("Tlnt", config.get("test.Tlnt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.Tlnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.Tlnt").showName);
		Assert.assertEquals(true, config.get("test.Tlnt").showThread);
	}

	/* 1111 */

	@Test
	public void tlnt_NoLogging() {
		MockUtil.mockLogLevelRestricted(LogLevel.SUPPRESS);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlnt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tlnt").showName);
		Assert.assertEquals(true, config.get("test.tlnt").showThread);
	}

	@Test
	public void tlnt_WithLogging() {
		MockUtil.mockLogLevel(LogLevel.VERBOSE);
		LoggingConfig config = new LoggingConfig("configTest3.properties", new LogAdapter("N/A", mockConfig()));

		Assert.assertEquals("DefaultTag", config.get("test.tlnt").tag);
		Assert.assertEquals(LogLevel.INFO, config.get("test.tlnt").level);
		Assert.assertEquals(LoggerConfig.ShowName.COMPACT, config.get("test.tlnt").showName);
		Assert.assertEquals(true, config.get("test.tlnt").showThread);
	}
}
