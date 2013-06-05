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
	private final String name;
	private final String prefixName;
	private final LoggerConfig config;
	private final boolean ERROR;
	private final boolean WARN;
	private final boolean INFO;
	private final boolean DEBUG;
	private final boolean TRACE;

	LogAdapter(final String name, final LoggerConfig config) {
		this.name = name;
		this.config = config;

		if (config.level == LogLevel.NATIVE) {
			ERROR = Log.isLoggable(config.tag, Log.ERROR);
			WARN = ERROR && Log.isLoggable(config.tag, Log.WARN);
			INFO = WARN && Log.isLoggable(config.tag, Log.INFO);
			DEBUG = INFO && Log.isLoggable(config.tag, Log.DEBUG);
			TRACE = DEBUG && Log.isLoggable(config.tag, Log.VERBOSE);
		} else {
			ERROR = config.level.compareTo(LogLevel.ERROR) >= 0;
			WARN = ERROR && config.level.compareTo(LogLevel.WARN) >= 0;
			INFO = WARN && config.level.compareTo(LogLevel.INFO) >= 0;
			DEBUG = INFO && config.level.compareTo(LogLevel.DEBUG) >= 0;
			TRACE = DEBUG && config.level.compareTo(LogLevel.VERBOSE) >= 0;
		}

		switch (config.showName) {
		case LONG:
			prefixName = name.concat(": ");
			break;

		case SHORT:
			prefixName = name.substring(name.lastIndexOf('.') + 1).concat(": ");
			break;

		case FALSE:
		default:
			prefixName = null;
			break;
		}
	}

	@Override
	public final String getName() {
		return name;
	}

	private final String rewriteMsg(final String msg) {
		if (config.showThread) {
			final StringBuilder sb = new StringBuilder(msg.length() + 64);

			sb.append("[").append(Thread.currentThread().getName()).append("] ");

			if (prefixName != null) {
				sb.append(prefixName);
			}

			sb.append(msg);

			return sb.toString();
		} else {
			if (prefixName != null) {
				return prefixName.concat(msg);
			} else {
				return msg;
			}
		}
	}

	/* Trace */

	@Override
	public final boolean isTraceEnabled() {
		return TRACE;
	}

	private final void __trace(final String msg) {
		Log.v(config.tag, rewriteMsg(msg));
	}

	private final void __trace(final String msg, final Throwable t) {
		if (t == null) {
			Log.v(config.tag, rewriteMsg(msg));
		} else {
			Log.v(config.tag, rewriteMsg(msg), t);
		}
	}

	private final void __trace(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__trace(ft.getMessage(), ft.getThrowable());
	}

	@Override
	public final void trace(final String msg) {
		if (TRACE) {
			__trace(msg);
		}
	}

	@Override
	public final void trace(final String format, final Object arg) {
		if (TRACE) {
			__trace(format, arg);
		}
	}

	@Override
	public final void trace(final String format, final Object arg1, final Object arg2) {
		if (TRACE) {
			__trace(format, arg1, arg2);
		}
	}

	@Override
	public final void trace(final String format, final Object... arguments) {
		if (TRACE) {
			__trace(format, arguments);
		}
	}

	@Override
	public final void trace(final String msg, final Throwable t) {
		if (TRACE) {
			__trace(msg, t);
		}
	}

	@Override
	public final boolean isTraceEnabled(final Marker marker) {
		return TRACE;
	}

	@Override
	public final void trace(final Marker marker, final String msg) {
		trace(msg);
	}

	@Override
	public final void trace(final Marker marker, final String format, final Object arg) {
		trace(format, arg);
	}

	@Override
	public final void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
		trace(format, arg1, arg2);
	}

	@Override
	public final void trace(final Marker marker, final String format, final Object... argArray) {
		trace(format, argArray);
	}

	@Override
	public final void trace(final Marker marker, final String msg, final Throwable t) {
		trace(msg, t);
	}

	/* Debug */

	@Override
	public final boolean isDebugEnabled() {
		return DEBUG;
	}

	private final void __debug(final String msg) {
		Log.d(config.tag, rewriteMsg(msg));
	}

	private final void __debug(final String msg, final Throwable t) {
		if (t == null) {
			Log.d(config.tag, rewriteMsg(msg));
		} else {
			Log.d(config.tag, rewriteMsg(msg), t);
		}
	}

	private final void __debug(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__debug(ft.getMessage(), ft.getThrowable());
	}

	@Override
	public final void debug(final String msg) {
		if (DEBUG) {
			__debug(msg);
		}
	}

	@Override
	public final void debug(final String format, final Object arg) {
		if (DEBUG) {
			__debug(format, arg);
		}
	}

	@Override
	public final void debug(final String format, final Object arg1, final Object arg2) {
		if (DEBUG) {
			__debug(format, arg1, arg2);
		}
	}

	@Override
	public final void debug(final String format, final Object... arguments) {
		if (DEBUG) {
			__debug(format, arguments);
		}
	}

	@Override
	public final void debug(final String msg, final Throwable t) {
		if (DEBUG) {
			__debug(msg, t);
		}
	}

	@Override
	public final boolean isDebugEnabled(final Marker marker) {
		return DEBUG;
	}

	@Override
	public final void debug(final Marker marker, final String msg) {
		debug(msg);
	}

	@Override
	public final void debug(final Marker marker, final String format, final Object arg) {
		debug(format, arg);
	}

	@Override
	public final void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
		debug(format, arg1, arg2);
	}

	@Override
	public final void debug(final Marker marker, final String format, final Object... argArray) {
		debug(format, argArray);
	}

	@Override
	public final void debug(final Marker marker, final String msg, final Throwable t) {
		debug(msg, t);
	}

	/* Info */

	@Override
	public final boolean isInfoEnabled() {
		return INFO;
	}

	private final void __info(final String msg) {
		Log.i(config.tag, rewriteMsg(msg));
	}

	private final void __info(final String msg, final Throwable t) {
		if (t == null) {
			Log.i(config.tag, rewriteMsg(msg));
		} else {
			Log.i(config.tag, rewriteMsg(msg), t);
		}
	}

	private final void __info(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__info(ft.getMessage(), ft.getThrowable());
	}

	@Override
	public final void info(final String msg) {
		if (INFO) {
			__info(msg);
		}
	}

	@Override
	public final void info(final String format, final Object arg) {
		if (INFO) {
			__info(format, arg);
		}
	}

	@Override
	public final void info(final String format, final Object arg1, final Object arg2) {
		if (INFO) {
			__info(format, arg1, arg2);
		}
	}

	@Override
	public final void info(final String format, final Object... arguments) {
		if (INFO) {
			__info(format, arguments);
		}
	}

	@Override
	public final void info(final String msg, final Throwable t) {
		if (INFO) {
			__info(msg, t);
		}
	}

	@Override
	public final boolean isInfoEnabled(final Marker marker) {
		return INFO;
	}

	@Override
	public final void info(final Marker marker, final String msg) {
		info(msg);
	}

	@Override
	public final void info(final Marker marker, final String format, final Object arg) {
		info(format, arg);
	}

	@Override
	public final void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
		info(format, arg1, arg2);
	}

	@Override
	public final void info(final Marker marker, final String format, final Object... argArray) {
		info(format, argArray);
	}

	@Override
	public final void info(final Marker marker, final String msg, final Throwable t) {
		info(msg, t);
	}

	/* Warn */

	@Override
	public final boolean isWarnEnabled() {
		return WARN;
	}

	private final void __warn(final String msg) {
		Log.w(config.tag, rewriteMsg(msg));
	}

	private final void __warn(final String msg, final Throwable t) {
		if (t == null) {
			Log.w(config.tag, rewriteMsg(msg));
		} else {
			Log.w(config.tag, rewriteMsg(msg), t);
		}
	}

	private final void __warn(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__warn(ft.getMessage(), ft.getThrowable());
	}

	@Override
	public final void warn(final String msg) {
		if (WARN) {
			__warn(msg);
		}
	}

	@Override
	public final void warn(final String format, final Object arg) {
		if (WARN) {
			__warn(format, arg);
		}
	}

	@Override
	public final void warn(final String format, final Object arg1, final Object arg2) {
		if (WARN) {
			__warn(format, arg1, arg2);
		}
	}

	@Override
	public final void warn(final String format, final Object... arguments) {
		if (WARN) {
			__warn(format, arguments);
		}
	}

	@Override
	public final void warn(final String msg, final Throwable t) {
		if (WARN) {
			__warn(msg, t);
		}
	}

	@Override
	public final boolean isWarnEnabled(final Marker marker) {
		return WARN;
	}

	@Override
	public final void warn(final Marker marker, final String msg) {
		warn(msg);
	}

	@Override
	public final void warn(final Marker marker, final String format, final Object arg) {
		warn(format, arg);
	}

	@Override
	public final void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
		warn(format, arg1, arg2);
	}

	@Override
	public final void warn(final Marker marker, final String format, final Object... argArray) {
		warn(format, argArray);
	}

	@Override
	public final void warn(final Marker marker, final String msg, final Throwable t) {
		warn(msg, t);
	}

	/* Error */

	@Override
	public final boolean isErrorEnabled() {
		return ERROR;
	}

	private final void __error(final String msg) {
		Log.e(config.tag, rewriteMsg(msg));
	}

	private final void __error(final String msg, final Throwable t) {
		if (t == null) {
			Log.e(config.tag, rewriteMsg(msg));
		} else {
			Log.e(config.tag, rewriteMsg(msg), t);
		}
	}

	private final void __error(final String format, final Object... arguments) {
		final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
		__error(ft.getMessage(), ft.getThrowable());
	}

	@Override
	public final void error(final String msg) {
		if (ERROR) {
			__error(msg);
		}
	}

	@Override
	public final void error(final String format, final Object arg) {
		if (ERROR) {
			__error(format, arg);
		}
	}

	@Override
	public final void error(final String format, final Object arg1, final Object arg2) {
		if (ERROR) {
			__error(format, arg1, arg2);
		}
	}

	@Override
	public final void error(final String format, final Object... arguments) {
		if (ERROR) {
			__error(format, arguments);
		}
	}

	@Override
	public final void error(final String msg, final Throwable t) {
		if (ERROR) {
			__error(msg, t);
		}
	}

	@Override
	public final boolean isErrorEnabled(final Marker marker) {
		return ERROR;
	}

	@Override
	public final void error(final Marker marker, final String msg) {
		error(msg);
	}

	@Override
	public final void error(final Marker marker, final String format, final Object arg) {
		error(format, arg);
	}

	@Override
	public final void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
		error(format, arg1, arg2);
	}

	@Override
	public final void error(final Marker marker, final String format, final Object... argArray) {
		error(format, argArray);
	}

	@Override
	public final void error(final Marker marker, final String msg, final Throwable t) {
		error(msg, t);
	}
}
