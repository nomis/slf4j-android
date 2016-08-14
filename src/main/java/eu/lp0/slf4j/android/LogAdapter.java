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
package eu.lp0.slf4j.android;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import android.util.Log;

/**
 * A wrapper for {@link android.util.Log android.util.Log} conforming to the {@link Logger} interface.
 * 
 * <p>
 * Note that the logging levels mentioned in this class refer to those defined in the <a href="http://developer.android.com/reference/android/util/Log.html">
 * <code>android.util.Log</code></a> class.
 * 
 * @author Simon Arlott
 */
final class LogAdapter implements Logger {
	private static final ConcurrentMap<String, LogLevel> nativeLevelMap = new ConcurrentHashMap<String, LogLevel>();
	private static final int DIRECT_FRAMES = 2;
	private static final int FORMAT_FRAMES = 3;
	private final String name;
	private final String tag;
	private final String prefixName;
	private final boolean showThread;
	private final boolean showCaller;
	private final boolean complexRewriteMsg;
	private final boolean ERROR;
	private final boolean WARN;
	private final boolean INFO;
	private final boolean DEBUG;
	private final boolean TRACE;

	LogAdapter(final String name, final LoggerConfig config) {
		this.name = name;
		this.tag = config.tag;

		if (config.level == LogLevel.NATIVE) {
			config.level = getNativeLogLevel();
		}

		TRACE = (config.level == LogLevel.VERBOSE);
		DEBUG = TRACE || (config.level == LogLevel.DEBUG);
		INFO = DEBUG || (config.level == LogLevel.INFO);
		WARN = INFO || (config.level == LogLevel.WARN);
		ERROR = WARN || (config.level == LogLevel.ERROR);

		switch (config.showName) {
		case CALLER:
			prefixName = null;
			showCaller = true;
			break;

		case LONG:
			prefixName = name.concat(": ");
			showCaller = false;
			break;

		case COMPACT:
			prefixName = getCompactName().concat(": ");
			showCaller = false;
			break;

		case SHORT:
			prefixName = name.substring(name.lastIndexOf('.') + 1).concat(": ");
			showCaller = false;
			break;

		case FALSE:
		default:
			showCaller = false;
			prefixName = null;
			break;
		}

		showThread = config.showThread;
		complexRewriteMsg = showThread || showCaller;
	}

	private final LogLevel getNativeLogLevel() {
		LogLevel level = nativeLevelMap.get(tag);
		if (level != null) {
			return level;
		}

		/* Requires no more than 3 calls to isLoggable to find any level */
		if (Log.isLoggable(tag, Log.INFO)) {
			if (Log.isLoggable(tag, Log.DEBUG)) {
				if (Log.isLoggable(tag, Log.VERBOSE)) {
					level = LogLevel.VERBOSE;
				} else {
					level = LogLevel.DEBUG;
				}
			} else {
				/* Default log level */
				level = LogLevel.INFO;
			}
		} else if (Log.isLoggable(tag, Log.WARN)) {
			level = LogLevel.WARN;
		} else if (Log.isLoggable(tag, Log.ERROR)) {
			level = LogLevel.ERROR;
		} else {
			level = LogLevel.SUPPRESS;
		}

		nativeLevelMap.put(tag, level);
		return level;
	}

	private final String getCompactName() {
		final char[] compactName = name.toCharArray();
		final int arrayLen = compactName.length;
		int len = 0;
		int mark = 0;

		for (int i = 0; i < arrayLen; i++, len++) {
			if (compactName[i] == '.') {
				len = mark;

				if (compactName[len] != '.') {
					len++;
				}

				mark = len;

				if (i + 1 < arrayLen && compactName[i + 1] != '.') {
					mark++;
				}
			}

			compactName[len] = compactName[i];
		}

		return new String(compactName, 0, len);
	}

	@Override
	public final String getName() {
		return name;
	}

	private final String rewriteMsg(String msg, final int frames) {
		if (msg == null) {
			msg = "null";
		}

		if (complexRewriteMsg) {
			final StringBuilder sb = new StringBuilder(msg.length() + 64);

			if (showThread) {
				sb.append('[').append(Thread.currentThread().getName()).append("] ");
			}

			if (showCaller) {
				sb.append(new CallerStackTrace(frames).toString()).append(": ");
			} else if (prefixName != null) {
				sb.append(prefixName);
			}

			sb.append(msg);

			return sb.toString();
		} else if (prefixName != null) {
			return prefixName.concat(msg);
		} else {
			return msg;
		}
	}

	/* Trace */

	@Override
	public final boolean isTraceEnabled() {
		return TRACE;
	}

	private final void __trace(final String msg, final Throwable t) {
		if (t == null) {
			Log.v(tag, msg);
		} else {
			Log.v(tag, msg, t);
		}
	}

	private final void __traceFormat(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__trace(rewriteMsg(ft.getMessage(), FORMAT_FRAMES), ft.getThrowable());
	}

	@Override
	public final void trace(final String msg) {
		if (TRACE) {
			Log.v(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void trace(final String format, final Object arg) {
		if (TRACE) {
			__traceFormat(format, arg);
		}
	}

	@Override
	public final void trace(final String format, final Object arg1, final Object arg2) {
		if (TRACE) {
			__traceFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void trace(final String format, final Object... arguments) {
		if (TRACE) {
			__traceFormat(format, arguments);
		}
	}

	@Override
	public final void trace(final String msg, final Throwable t) {
		if (TRACE) {
			__trace(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	@Override
	public final boolean isTraceEnabled(final Marker marker) {
		return TRACE;
	}

	@Override
	public final void trace(final Marker marker, final String msg) {
		if (TRACE) {
			Log.v(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void trace(final Marker marker, final String format, final Object arg) {
		if (TRACE) {
			__traceFormat(format, arg);
		}
	}

	@Override
	public final void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
		if (TRACE) {
			__traceFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void trace(final Marker marker, final String format, final Object... argArray) {
		if (TRACE) {
			__traceFormat(format, argArray);
		}
	}

	@Override
	public final void trace(final Marker marker, final String msg, final Throwable t) {
		if (TRACE) {
			__trace(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	/* Debug */

	@Override
	public final boolean isDebugEnabled() {
		return DEBUG;
	}

	private final void __debug(final String msg, final Throwable t) {
		if (t == null) {
			Log.d(tag, msg);
		} else {
			Log.d(tag, msg, t);
		}
	}

	private final void __debugFormat(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__debug(rewriteMsg(ft.getMessage(), FORMAT_FRAMES), ft.getThrowable());
	}

	@Override
	public final void debug(final String msg) {
		if (DEBUG) {
			Log.d(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void debug(final String format, final Object arg) {
		if (DEBUG) {
			__debugFormat(format, arg);
		}
	}

	@Override
	public final void debug(final String format, final Object arg1, final Object arg2) {
		if (DEBUG) {
			__debugFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void debug(final String format, final Object... arguments) {
		if (DEBUG) {
			__debugFormat(format, arguments);
		}
	}

	@Override
	public final void debug(final String msg, final Throwable t) {
		if (DEBUG) {
			__debug(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	@Override
	public final boolean isDebugEnabled(final Marker marker) {
		return DEBUG;
	}

	@Override
	public final void debug(final Marker marker, final String msg) {
		if (DEBUG) {
			Log.d(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void debug(final Marker marker, final String format, final Object arg) {
		if (DEBUG) {
			__debugFormat(format, arg);
		}
	}

	@Override
	public final void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
		if (DEBUG) {
			__debugFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void debug(final Marker marker, final String format, final Object... argArray) {
		if (DEBUG) {
			__debugFormat(format, argArray);
		}
	}

	@Override
	public final void debug(final Marker marker, final String msg, final Throwable t) {
		if (DEBUG) {
			__debug(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	/* Info */

	@Override
	public final boolean isInfoEnabled() {
		return INFO;
	}

	private final void __info(final String msg, final Throwable t) {
		if (t == null) {
			Log.i(tag, msg);
		} else {
			Log.i(tag, msg, t);
		}
	}

	private final void __infoFormat(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__info(rewriteMsg(ft.getMessage(), FORMAT_FRAMES), ft.getThrowable());
	}

	@Override
	public final void info(final String msg) {
		if (INFO) {
			Log.i(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void info(final String format, final Object arg) {
		if (INFO) {
			__infoFormat(format, arg);
		}
	}

	@Override
	public final void info(final String format, final Object arg1, final Object arg2) {
		if (INFO) {
			__infoFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void info(final String format, final Object... arguments) {
		if (INFO) {
			__infoFormat(format, arguments);
		}
	}

	@Override
	public final void info(final String msg, final Throwable t) {
		if (INFO) {
			__info(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	@Override
	public final boolean isInfoEnabled(final Marker marker) {
		return INFO;
	}

	@Override
	public final void info(final Marker marker, final String msg) {
		if (INFO) {
			Log.i(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void info(final Marker marker, final String format, final Object arg) {
		if (INFO) {
			__infoFormat(format, arg);
		}
	}

	@Override
	public final void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
		if (INFO) {
			__infoFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void info(final Marker marker, final String format, final Object... argArray) {
		if (INFO) {
			__infoFormat(format, argArray);
		}
	}

	@Override
	public final void info(final Marker marker, final String msg, final Throwable t) {
		if (INFO) {
			__info(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	/* Warn */

	@Override
	public final boolean isWarnEnabled() {
		return WARN;
	}

	private final void __warn(final String msg, final Throwable t) {
		if (t == null) {
			Log.w(tag, msg);
		} else {
			Log.w(tag, msg, t);
		}
	}

	private final void __warnFormat(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__warn(rewriteMsg(ft.getMessage(), FORMAT_FRAMES), ft.getThrowable());
	}

	@Override
	public final void warn(final String msg) {
		if (WARN) {
			Log.w(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void warn(final String format, final Object arg) {
		if (WARN) {
			__warnFormat(format, arg);
		}
	}

	@Override
	public final void warn(final String format, final Object arg1, final Object arg2) {
		if (WARN) {
			__warnFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void warn(final String format, final Object... arguments) {
		if (WARN) {
			__warnFormat(format, arguments);
		}
	}

	@Override
	public final void warn(final String msg, final Throwable t) {
		if (WARN) {
			__warn(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	@Override
	public final boolean isWarnEnabled(final Marker marker) {
		return WARN;
	}

	@Override
	public final void warn(final Marker marker, final String msg) {
		if (WARN) {
			Log.w(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void warn(final Marker marker, final String format, final Object arg) {
		if (WARN) {
			__warnFormat(format, arg);
		}
	}

	@Override
	public final void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
		if (WARN) {
			__warnFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void warn(final Marker marker, final String format, final Object... argArray) {
		if (WARN) {
			__warnFormat(format, argArray);
		}
	}

	@Override
	public final void warn(final Marker marker, final String msg, final Throwable t) {
		if (WARN) {
			__warn(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	/* Error */

	@Override
	public final boolean isErrorEnabled() {
		return ERROR;
	}

	private final void __error(final String msg, final Throwable t) {
		if (t == null) {
			Log.e(tag, msg);
		} else {
			Log.e(tag, msg, t);
		}
	}

	private final void __errorFormat(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__error(rewriteMsg(ft.getMessage(), FORMAT_FRAMES), ft.getThrowable());
	}

	@Override
	public final void error(final String msg) {
		if (ERROR) {
			Log.e(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void error(final String format, final Object arg) {
		if (ERROR) {
			__errorFormat(format, arg);
		}
	}

	@Override
	public final void error(final String format, final Object arg1, final Object arg2) {
		if (ERROR) {
			__errorFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void error(final String format, final Object... arguments) {
		if (ERROR) {
			__errorFormat(format, arguments);
		}
	}

	@Override
	public final void error(final String msg, final Throwable t) {
		if (ERROR) {
			__error(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}

	@Override
	public final boolean isErrorEnabled(final Marker marker) {
		return ERROR;
	}

	@Override
	public final void error(final Marker marker, final String msg) {
		if (ERROR) {
			Log.e(tag, rewriteMsg(msg, DIRECT_FRAMES));
		}
	}

	@Override
	public final void error(final Marker marker, final String format, final Object arg) {
		if (ERROR) {
			__errorFormat(format, arg);
		}
	}

	@Override
	public final void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
		if (ERROR) {
			__errorFormat(format, arg1, arg2);
		}
	}

	@Override
	public final void error(final Marker marker, final String format, final Object... argArray) {
		if (ERROR) {
			__errorFormat(format, argArray);
		}
	}

	@Override
	public final void error(final Marker marker, final String msg, final Throwable t) {
		if (ERROR) {
			__error(rewriteMsg(msg, DIRECT_FRAMES), t);
		}
	}
}
