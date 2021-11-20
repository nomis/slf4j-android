/**
 * Copyright 2013,2016,2019,2021  Simon Arlott
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

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static uk.uuid.slf4j.android.MockUtil.createTag;
import static uk.uuid.slf4j.android.MockUtil.mockConfigThread;
import static uk.uuid.slf4j.android.MockUtil.mockLogLevelRestricted;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Marker;

import android.util.Log;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = ThreadLoggerTest.class, fullyQualifiedNames = { "android.util.Log" })
public class ThreadLoggerTest {
	@Mock
	private Throwable throwable;

	@Mock
	private Marker marker;

	@Before
	public void mockLog() {
		mockStatic(Log.class);
		MockUtil.mockNativeBehaviour();
		Thread.currentThread().setName(getClass().getSimpleName() + "Thread");
	}

	/* Name, Levels */

	@Test
	public void testName() {
		Assert.assertEquals("Logger Name!", new LogAdapter("Logger Name!", mockConfigThread()).getName());
	}

	@Test
	public void testLevel_Native_SUPPRESS() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread());

		Assert.assertFalse(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertFalse(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_ERROR() {
		mockLogLevelRestricted(LogLevel.ERROR);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_WARN() {
		mockLogLevelRestricted(LogLevel.WARN);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_INFO() {
		mockLogLevelRestricted(LogLevel.INFO);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_DEBUG() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Native_VERBOSE() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread());

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertTrue(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertTrue(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_SUPPRESS() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread(LogLevel.SUPPRESS));

		Assert.assertFalse(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertFalse(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_ERROR() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread(LogLevel.ERROR));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertFalse(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertFalse(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_WARN() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread(LogLevel.WARN));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertFalse(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertFalse(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_INFO() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread(LogLevel.INFO));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertFalse(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertFalse(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_DEBUG() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread(LogLevel.DEBUG));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertFalse(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertFalse(log.isTraceEnabled(marker));
	}

	@Test
	public void testLevel_Override_VERBOSE() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		LogAdapter log = new LogAdapter("N/A", mockConfigThread(LogLevel.VERBOSE));

		Assert.assertTrue(log.isErrorEnabled());
		Assert.assertTrue(log.isWarnEnabled());
		Assert.assertTrue(log.isInfoEnabled());
		Assert.assertTrue(log.isDebugEnabled());
		Assert.assertTrue(log.isTraceEnabled());

		Assert.assertTrue(log.isErrorEnabled(marker));
		Assert.assertTrue(log.isWarnEnabled(marker));
		Assert.assertTrue(log.isInfoEnabled(marker));
		Assert.assertTrue(log.isDebugEnabled(marker));
		Assert.assertTrue(log.isTraceEnabled(marker));
	}

	/* Error Enabled */

	@Test
	public void testERROR_errorEnabled() {
		mockLogLevelRestricted(LogLevel.ERROR);
		Assert.assertTrue(new LogAdapter("N/A", mockConfigThread()).isErrorEnabled());
	}

	@Test
	public void testERROR_error_Msg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 1");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 1");
	}

	@Test
	public void testERROR_error_MsgNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testERROR_error_MsgArg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 2 {}", "arg");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 2 arg");
	}

	@Test
	public void testERROR_error_Msg2Args() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 3 arg1 arg2");
	}

	@Test
	public void testERROR_error_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testERROR_error_MsgExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 5", throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 5", throwable);
	}

	@Test
	public void testERROR_error_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 6", (Throwable)null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 6");
	}

	@Test
	public void testERROR_error_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 7", (Object)throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 7", throwable);
	}

	@Test
	public void testERROR_error_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 8", (Object)null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 8");
	}

	@Test
	public void testERROR_error_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 9 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 9 arg1", throwable);
	}

	@Test
	public void testERROR_error_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 10 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 10 arg1");
	}

	@Test
	public void testERROR_error_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testERROR_error_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 12 arg1 arg2");
	}

	@Test
	public void testERROR_error_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 13");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 13");
	}

	@Test
	public void testERROR_error_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testERROR_error_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 14 {}", "arg");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 14 arg");
	}

	@Test
	public void testERROR_error_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 15 arg1 arg2");
	}

	@Test
	public void testERROR_error_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testERROR_error_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 17", throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 17", throwable);
	}

	@Test
	public void testERROR_error_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 18", (Throwable)null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 18");
	}

	@Test
	public void testERROR_error_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 19", (Object)throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 19", throwable);
	}

	@Test
	public void testERROR_error_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 20", (Object)null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 20");
	}

	@Test
	public void testERROR_error_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 21 arg1", throwable);
	}

	@Test
	public void testERROR_error_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 22 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 22 arg1");
	}

	@Test
	public void testERROR_error_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testERROR_error_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.e(createTag(0), "[ThreadLoggerTestThread] Message 24 arg1 arg2");
	}

	/* Error Disabled */

	@Test
	public void testSUPPRESS_errorEnabled() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		Assert.assertFalse(new LogAdapter("N/A", mockConfigThread()).isErrorEnabled());
	}

	@Test
	public void testSUPPRESS_error_Msg() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 1");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_MsgNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_MsgArg() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 2 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Msg2Args() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 3 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_MsgExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 5", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 6", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 7", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 8", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 9 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 10 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 11 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error("Message 12 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 13");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 14 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 15 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 17", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 18", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 19", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 20", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 21 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 22 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testSUPPRESS_error_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.SUPPRESS);
		new LogAdapter("N/A", mockConfigThread()).error(marker, "Message 24 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	/* Warn Enabled */

	@Test
	public void testWARN_warnEnabled() {
		mockLogLevelRestricted(LogLevel.WARN);
		Assert.assertTrue(new LogAdapter("N/A", mockConfigThread()).isWarnEnabled());
	}

	@Test
	public void testWARN_warn_Msg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 1");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 1");
	}

	@Test
	public void testWARN_warn_MsgNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testWARN_warn_MsgArg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 2 {}", "arg");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 2 arg");
	}

	@Test
	public void testWARN_warn_Msg2Args() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 3 arg1 arg2");
	}

	@Test
	public void testWARN_warn_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testWARN_warn_MsgExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 5", throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 5", throwable);
	}

	@Test
	public void testWARN_warn_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 6", (Throwable)null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 6");
	}

	@Test
	public void testWARN_warn_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 7", (Object)throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 7", throwable);
	}

	@Test
	public void testWARN_warn_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 8", (Object)null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 8");
	}

	@Test
	public void testWARN_warn_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 9 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 9 arg1", throwable);
	}

	@Test
	public void testWARN_warn_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 10 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 10 arg1");
	}

	@Test
	public void testWARN_warn_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testWARN_warn_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 12 arg1 arg2");
	}

	@Test
	public void testWARN_warn_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 13");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 13");
	}

	@Test
	public void testWARN_warn_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testWARN_warn_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 14 {}", "arg");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 14 arg");
	}

	@Test
	public void testWARN_warn_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 15 arg1 arg2");
	}

	@Test
	public void testWARN_warn_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testWARN_warn_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 17", throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 17", throwable);
	}

	@Test
	public void testWARN_warn_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 18", (Throwable)null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 18");
	}

	@Test
	public void testWARN_warn_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 19", (Object)throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 19", throwable);
	}

	@Test
	public void testWARN_warn_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 20", (Object)null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 20");
	}

	@Test
	public void testWARN_warn_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 21 arg1", throwable);
	}

	@Test
	public void testWARN_warn_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 22 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 22 arg1");
	}

	@Test
	public void testWARN_warn_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testWARN_warn_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.w(createTag(0), "[ThreadLoggerTestThread] Message 24 arg1 arg2");
	}

	/* Warn Disabled */

	@Test
	public void testERROR_warnEnabled() {
		mockLogLevelRestricted(LogLevel.ERROR);
		Assert.assertFalse(new LogAdapter("N/A", mockConfigThread()).isWarnEnabled());
	}

	@Test
	public void testERROR_warn_Msg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 1");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_MsgNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_MsgArg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 2 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Msg2Args() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 3 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_MsgExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 5", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 6", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 7", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 8", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 9 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 10 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 11 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn("Message 12 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 13");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 14 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 15 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 17", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 18", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 19", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 20", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 21 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 22 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testERROR_warn_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.ERROR);
		new LogAdapter("N/A", mockConfigThread()).warn(marker, "Message 24 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	/* Info Enabled */

	@Test
	public void testINFO_warnEnabled() {
		mockLogLevelRestricted(LogLevel.INFO);
		Assert.assertTrue(new LogAdapter("N/A", mockConfigThread()).isInfoEnabled());
	}

	@Test
	public void testINFO_info_Msg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 1");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 1");
	}

	@Test
	public void testINFO_info_MsgNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testINFO_info_MsgArg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 2 {}", "arg");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 2 arg");
	}

	@Test
	public void testINFO_info_Msg2Args() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 3 arg1 arg2");
	}

	@Test
	public void testINFO_info_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testINFO_info_MsgExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 5", throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 5", throwable);
	}

	@Test
	public void testINFO_info_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 6", (Throwable)null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 6");
	}

	@Test
	public void testINFO_info_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 7", (Object)throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 7", throwable);
	}

	@Test
	public void testINFO_info_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 8", (Object)null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 8");
	}

	@Test
	public void testINFO_info_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 9 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 9 arg1", throwable);
	}

	@Test
	public void testINFO_info_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 10 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 10 arg1");
	}

	@Test
	public void testINFO_info_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testINFO_info_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 12 arg1 arg2");
	}

	@Test
	public void testINFO_info_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 13");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 13");
	}

	@Test
	public void testINFO_info_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testINFO_info_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 14 {}", "arg");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 14 arg");
	}

	@Test
	public void testINFO_info_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 15 arg1 arg2");
	}

	@Test
	public void testINFO_info_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testINFO_info_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 17", throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 17", throwable);
	}

	@Test
	public void testINFO_info_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 18", (Throwable)null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 18");
	}

	@Test
	public void testINFO_info_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 19", (Object)throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 19", throwable);
	}

	@Test
	public void testINFO_info_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 20", (Object)null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 20");
	}

	@Test
	public void testINFO_info_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 21 arg1", throwable);
	}

	@Test
	public void testINFO_info_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 22 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 22 arg1");
	}

	@Test
	public void testINFO_info_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testINFO_info_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.i(createTag(0), "[ThreadLoggerTestThread] Message 24 arg1 arg2");
	}

	/* Info Disabled */

	@Test
	public void testWARN_infoEnabled() {
		mockLogLevelRestricted(LogLevel.WARN);
		Assert.assertFalse(new LogAdapter("N/A", mockConfigThread()).isInfoEnabled());
	}

	@Test
	public void testWARN_info_Msg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 1");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_MsgNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_MsgArg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 2 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Msg2Args() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 3 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_MsgExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 5", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 6", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 7", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 8", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 9 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 10 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 11 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info("Message 12 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 13");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 14 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 15 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 17", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 18", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 19", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 20", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 21 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 22 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testWARN_info_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.WARN);
		new LogAdapter("N/A", mockConfigThread()).info(marker, "Message 24 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	/* Debug Enabled */

	@Test
	public void testDEBUG_warnEnabled() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		Assert.assertTrue(new LogAdapter("N/A", mockConfigThread()).isDebugEnabled());
	}

	@Test
	public void testDEBUG_debug_Msg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 1");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 1");
	}

	@Test
	public void testDEBUG_debug_MsgNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testDEBUG_debug_MsgArg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 2 {}", "arg");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 2 arg");
	}

	@Test
	public void testDEBUG_debug_Msg2Args() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 3 arg1 arg2");
	}

	@Test
	public void testDEBUG_debug_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testDEBUG_debug_MsgExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 5", throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 5", throwable);
	}

	@Test
	public void testDEBUG_debug_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 6", (Throwable)null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 6");
	}

	@Test
	public void testDEBUG_debug_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 7", (Object)throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 7", throwable);
	}

	@Test
	public void testDEBUG_debug_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 8", (Object)null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 8");
	}

	@Test
	public void testDEBUG_debug_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 9 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 9 arg1", throwable);
	}

	@Test
	public void testDEBUG_debug_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 10 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 10 arg1");
	}

	@Test
	public void testDEBUG_debug_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testDEBUG_debug_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 12 arg1 arg2");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 13");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 13");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 14 {}", "arg");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 14 arg");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 15 arg1 arg2");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 17", throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 17", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 18", (Throwable)null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 18");
	}

	@Test
	public void testDEBUG_debug_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 19", (Object)throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 19", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 20", (Object)null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 20");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 21 arg1", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 22 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 22 arg1");
	}

	@Test
	public void testDEBUG_debug_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testDEBUG_debug_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.d(createTag(0), "[ThreadLoggerTestThread] Message 24 arg1 arg2");
	}

	/* Debug Disabled */

	@Test
	public void testINFO_debugEnabled() {
		mockLogLevelRestricted(LogLevel.INFO);
		Assert.assertFalse(new LogAdapter("N/A", mockConfigThread()).isDebugEnabled());
	}

	@Test
	public void testINFO_debug_Msg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 1");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_MsgNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_MsgArg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 2 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Msg2Args() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 3 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_MsgExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 5", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 6", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 7", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 8", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 9 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 10 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 11 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug("Message 12 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 13");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 14 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 15 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 17", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 18", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 19", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 20", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 21 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 22 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testINFO_debug_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.INFO);
		new LogAdapter("N/A", mockConfigThread()).debug(marker, "Message 24 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	/* Trace Enabled */

	@Test
	public void testTRACE_warnEnabled() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		Assert.assertTrue(new LogAdapter("N/A", mockConfigThread()).isTraceEnabled());
	}

	@Test
	public void testTRACE_trace_Msg() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 1");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 1");
	}

	@Test
	public void testTRACE_trace_MsgNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testTRACE_trace_MsgArg() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 2 {}", "arg");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 2 arg");
	}

	@Test
	public void testTRACE_trace_Msg2Args() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 3 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 3 arg1 arg2");
	}

	@Test
	public void testTRACE_trace_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 4 arg1 arg2 arg3");
	}

	@Test
	public void testTRACE_trace_MsgExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 5", throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 5", throwable);
	}

	@Test
	public void testTRACE_trace_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 6", (Throwable)null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 6");
	}

	@Test
	public void testTRACE_trace_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 7", (Object)throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 7", throwable);
	}

	@Test
	public void testTRACE_trace_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 8", (Object)null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 8");
	}

	@Test
	public void testTRACE_trace_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 9 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 9 arg1", throwable);
	}

	@Test
	public void testTRACE_trace_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 10 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 10 arg1");
	}

	@Test
	public void testTRACE_trace_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 11 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 11 arg1 arg2", throwable);
	}

	@Test
	public void testTRACE_trace_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 12 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 12 arg1 arg2");
	}

	@Test
	public void testTRACE_trace_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 13");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 13");
	}

	@Test
	public void testTRACE_trace_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] null");
	}

	@Test
	public void testTRACE_trace_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 14 {}", "arg");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 14 arg");
	}

	@Test
	public void testTRACE_trace_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 15 {} {}", "arg1", "arg2");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 15 arg1 arg2");
	}

	@Test
	public void testTRACE_trace_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 16 arg1 arg2 arg3");
	}

	@Test
	public void testTRACE_trace_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 17", throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 17", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 18", (Throwable)null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 18");
	}

	@Test
	public void testTRACE_trace_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 19", (Object)throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 19", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 20", (Object)null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 20");
	}

	@Test
	public void testTRACE_trace_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 21 {}", "arg1", throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 21 arg1", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 22 {}", "arg1", null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 22 arg1");
	}

	@Test
	public void testTRACE_trace_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 23 arg1 arg2", throwable);
	}

	@Test
	public void testTRACE_trace_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.VERBOSE);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 24 {} {}", "arg1", "arg2", null);

		verifyStatic(Log.class);
		Log.v(createTag(0), "[ThreadLoggerTestThread] Message 24 arg1 arg2");
	}

	/* Trace Disabled */

	@Test
	public void testDEBUG_traceEnabled() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		Assert.assertFalse(new LogAdapter("N/A", mockConfigThread()).isTraceEnabled());
	}

	@Test
	public void testDEBUG_trace_Msg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 1");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_MsgNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_MsgArg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 2 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Msg2Args() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 3 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 4 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_MsgExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 5", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 6", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 7", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 8", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 9 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 10 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 11 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace("Message 12 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_Msg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 13");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_MsgNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_MsgArg() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 14 {}", "arg");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_Msg2Args() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 15 {} {}", "arg1", "arg2");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_MsgManyArgs() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 16 {} {} {}", "arg1", "arg2", "arg3");

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_MsgExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 17", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_MsgNullExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 18", (Throwable)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_MsgObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 19", (Object)throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_MsgObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 20", (Object)null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_Msg2ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 21 {}", "arg1", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_Msg2ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 22 {}", "arg1", null);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_Msg3ObjExc() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 23 {} {}", "arg1", "arg2", throwable);

		MockUtil.verifyNoLog();
	}

	@Test
	public void testDEBUG_trace_Marker_Msg3ObjNull() {
		mockLogLevelRestricted(LogLevel.DEBUG);
		new LogAdapter("N/A", mockConfigThread()).trace(marker, "Message 24 {} {}", "arg1", "arg2", null);

		MockUtil.verifyNoLog();
	}
}
