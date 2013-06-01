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
package eu.lp0.slf4j;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import android.util.Log;

final class AndroidLoggerAdapter implements Logger {
	private final String tag;
	
	AndroidLoggerAdapter(final String name) {
		tag = createTag(name);
	}
	
	private static final int MAX_TAG_LEN = 23;
	static final String createTag(final String name) {
		final char[] tag = name.toCharArray();
		final int arrayLen = tag.length;
		int len = 0;
		int mark = 0;
		
		if (arrayLen <= MAX_TAG_LEN)
			return name;
		
		for (int i = 0; i < arrayLen; i++, len++) {
			if (tag[i] == '.') {
				len = mark;
				
				if (tag[len] != '.') {
					len++;
				}
				
				mark = len;
				
				if (i + 1 < arrayLen && tag[i+1] != '.') {
					mark++;
				}
			}
			
			tag[len] = tag[i];
		}
		
		if (len > MAX_TAG_LEN) {
			int i = 0;
			
			mark--;
			
			for (int j = 0; j < len; j++) {
				if (tag[j] == '.' && (((j & 1) == 1 && j != mark) || (i >= MAX_TAG_LEN - 1)))
					continue;
				
				tag[i++] = tag[j];
			}
			
			len = i;
			
			if (len > MAX_TAG_LEN) {
				len = MAX_TAG_LEN;
			}
		}
		
		return new String(tag, 0, len);
	}

	@Override
	public final String getName() {
		return tag;
	}
	
	@Override
	public final boolean isTraceEnabled() {
		return Log.isLoggable(tag, Log.VERBOSE);
	}

	@Override
	public final void trace(final String msg) {
		Log.v(tag, msg);
	}

	@Override
	public final void trace(final String format, final Object arg) {
		if (isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			trace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void trace(final String format, final Object arg1, final Object arg2) {
		if (isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			trace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void trace(final String format, final Object... arguments) {
		if (isTraceEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			trace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void trace(final String msg, final Throwable t) {
		if (t == null) {
			Log.v(tag, msg);
		} else {
			Log.v(tag, msg, t);
		}
	}

	@Override
	public final boolean isTraceEnabled(final Marker marker) {
		return isTraceEnabled();
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

	@Override
	public final boolean isDebugEnabled() {
		return Log.isLoggable(tag, Log.DEBUG);
	}

	@Override
	public final void debug(final String msg) {
		Log.d(tag, msg);
	}

	@Override
	public final void debug(final String format, final Object arg) {
		if (isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			debug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void debug(final String format, final Object arg1, final Object arg2) {
		if (isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			debug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void debug(final String format, final Object... arguments) {
		if (isDebugEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			debug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void debug(final String msg, final Throwable t) {
		if (t == null) {
			Log.d(tag, msg);
		} else {
			Log.d(tag, msg, t);
		}
	}

	@Override
	public final boolean isDebugEnabled(final Marker marker) {
		return isDebugEnabled();
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
	public final void debug(final Marker marker, final String format, final Object... arguments) {
		debug(format, arguments);
	}

	@Override
	public final void debug(final Marker marker, final String msg, final Throwable t) {
		debug(msg, t);
	}

	@Override
	public final boolean isInfoEnabled() {
		return Log.isLoggable(tag, Log.INFO);
	}

	@Override
	public final void info(final String msg) {
		Log.i(tag, msg);
	}

	@Override
	public final void info(final String format, final Object arg) {
		if (isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			info(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void info(final String format, final Object arg1, final Object arg2) {
		if (isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			info(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void info(final String format, final Object... arguments) {
		if (isInfoEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			info(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void info(final String msg, final Throwable t) {
		if (t == null) {
			Log.i(tag, msg);
		} else {
			Log.i(tag, msg, t);
		}
	}

	@Override
	public final boolean isInfoEnabled(final Marker marker) {
		return isInfoEnabled();
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
	public final void info(final Marker marker, final String format, final Object... arguments) {
		info(format, arguments);
	}

	@Override
	public final void info(final Marker marker, final String msg, final Throwable t) {
		info(msg, t);
	}
	
	@Override
	public final boolean isWarnEnabled() {
		return Log.isLoggable(tag, Log.WARN);
	}

	@Override
	public final void warn(final String msg) {
		Log.w(tag, msg);
	}

	@Override
	public final void warn(final String format, final Object arg) {
		if (isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			warn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void warn(final String format, final Object arg1, final Object arg2) {
		if (isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			warn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void warn(final String format, final Object... arguments) {
		if (isWarnEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			warn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void warn(final String msg, final Throwable t) {
		if (t == null) {
			Log.w(tag, msg);
		} else {
			Log.w(tag, msg, t);
		}
	}

	@Override
	public final boolean isWarnEnabled(final Marker marker) {
		return isWarnEnabled();
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
	public final void warn(final Marker marker, final String format, final Object... arguments) {
		warn(format, arguments);
	}

	@Override
	public final void warn(final Marker marker, final String msg, final Throwable t) {
		warn(msg, t);
	}
	
	@Override
	public final boolean isErrorEnabled() {
		return Log.isLoggable(tag, Log.ERROR);
	}

	@Override
	public final void error(final String msg) {
		Log.e(tag, msg);
	}

	@Override
	public final void error(final String format, final Object arg) {
		if (isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg);
			error(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void error(final String format, final Object arg1, final Object arg2) {
		if (isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			error(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void error(final String format, final Object... arguments) {
		if (isErrorEnabled()) {
			final FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			error(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public final void error(final String msg, final Throwable t) {
		if (t == null) {
			Log.e(tag, msg);
		} else {
			Log.e(tag, msg, t);
		}
	}

	@Override
	public final boolean isErrorEnabled(final Marker marker) {
		return isErrorEnabled();
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
	public final void error(final Marker marker, final String format, final Object... arguments) {
		error(format, arguments);
	}

	@Override
	public final void error(final Marker marker, final String msg, final Throwable t) {
		error(msg, t);
	}
}
