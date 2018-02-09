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
import static uk.uuid.slf4j.android.MockUtil.mockConfigCompact;
import static uk.uuid.slf4j.android.MockUtil.mockLogLevelRestricted;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = CompactNamesTest.class, fullyQualifiedNames = { "android.util.Log", "uk.uuid.slf4j.android.LoggerFactory" })
public class CompactNamesTest {
	@Before
	public void mockLog() {
		mockStatic(Log.class);
	}

	@Test
	public void testSimple() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("logger.name.here", mockConfigCompact()).error("Message 1");

		verifyStatic();
		Log.e(createTag(0), "l.n.here: Message 1");
	}

	@Test
	public void testMultipleDots1() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("logger..name.here", mockConfigCompact()).error("Message 2");

		verifyStatic();
		Log.e(createTag(0), "l.n.here: Message 2");
	}

	@Test
	public void testMultipleDots2() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("logger.name..here", mockConfigCompact()).error("Message 3");

		verifyStatic();
		Log.e(createTag(0), "l.n.here: Message 3");
	}

	@Test
	public void testMultipleDots3() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("logger..name..here", mockConfigCompact()).error("Message 4");

		verifyStatic();
		Log.e(createTag(0), "l.n.here: Message 4");
	}

	@Test
	public void testDotStart1() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter(".logger.name.here", mockConfigCompact()).error("Message 5");

		verifyStatic();
		Log.e(createTag(0), ".l.n.here: Message 5");
	}

	@Test
	public void testDotStart2() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("..logger.name.here", mockConfigCompact()).error("Message 6");

		verifyStatic();
		Log.e(createTag(0), ".l.n.here: Message 6");
	}

	@Test
	public void testDotStart3() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("...logger.name.here", mockConfigCompact()).error("Message 7");

		verifyStatic();
		Log.e(createTag(0), ".l.n.here: Message 7");
	}

	@Test
	public void testDotEnd1() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("logger.name.here.", mockConfigCompact()).error("Message 8");

		verifyStatic();
		Log.e(createTag(0), "l.n.h.: Message 8");
	}

	@Test
	public void testDotEnd2() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("logger.name.here..", mockConfigCompact()).error("Message 9");

		verifyStatic();
		Log.e(createTag(0), "l.n.h.: Message 9");
	}

	@Test
	public void testDotEnd3() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("logger.name.here...", mockConfigCompact()).error("Message 10");

		verifyStatic();
		Log.e(createTag(0), "l.n.h.: Message 10");
	}
}
