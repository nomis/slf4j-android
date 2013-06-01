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

class AndroidLoggerAdapter implements Logger {
	private final String tag;
	
	AndroidLoggerAdapter(String name) {
		tag = createTag(name);
	}
	
	private static final int MAX_TAG_LEN = 23;
	static String createTag(String name) {
		char[] tag = name.toCharArray();
		int arrayLen = tag.length;
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
	public String getName() {
		return tag;
	}

	@Override
	public boolean isTraceEnabled() {
		return Log.isLoggable(tag, Log.VERBOSE);
	}

	@Override
	public void trace(String msg) {
		Log.v(tag, msg);
	}

	@Override
	public void trace(String format, Object arg) {
		if (isTraceEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			trace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		if (isTraceEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			trace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void trace(String format, Object... arguments) {
		if (isTraceEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			trace(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void trace(String msg, Throwable t) {
		Log.v(tag, msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return isTraceEnabled();
	}

	@Override
	public void trace(Marker marker, String msg) {
		trace(msg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		trace(format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		trace(format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		trace(format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		trace(msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return Log.isLoggable(tag, Log.DEBUG);
	}

	@Override
	public void debug(String msg) {
		Log.d(tag, msg);
	}

	@Override
	public void debug(String format, Object arg) {
		if (isDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			debug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		if (isDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			debug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void debug(String format, Object... arguments) {
		if (isDebugEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			debug(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void debug(String msg, Throwable t) {
		Log.d(tag, msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return isDebugEnabled();
	}

	@Override
	public void debug(Marker marker, String msg) {
		debug(msg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		debug(format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		debug(format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		debug(format, arguments);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		debug(msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return Log.isLoggable(tag, Log.INFO);
	}

	@Override
	public void info(String msg) {
		Log.i(tag, msg);
	}

	@Override
	public void info(String format, Object arg) {
		if (isInfoEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			info(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		if (isInfoEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			info(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void info(String format, Object... arguments) {
		if (isInfoEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			info(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void info(String msg, Throwable t) {
		Log.i(tag, msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return isInfoEnabled();
	}

	@Override
	public void info(Marker marker, String msg) {
		info(msg);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		info(format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		info(format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {
		info(format, arguments);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		info(msg, t);
	}
	
	@Override
	public boolean isWarnEnabled() {
		return Log.isLoggable(tag, Log.WARN);
	}

	@Override
	public void warn(String msg) {
		Log.w(tag, msg);
	}

	@Override
	public void warn(String format, Object arg) {
		if (isWarnEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			warn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		if (isWarnEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			warn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void warn(String format, Object... arguments) {
		if (isWarnEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			warn(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void warn(String msg, Throwable t) {
		Log.w(tag, msg, t);
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return isWarnEnabled();
	}

	@Override
	public void warn(Marker marker, String msg) {
		warn(msg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		warn(format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		warn(format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		warn(format, arguments);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		warn(msg, t);
	}
	
	@Override
	public boolean isErrorEnabled() {
		return Log.isLoggable(tag, Log.ERROR);
	}

	@Override
	public void error(String msg) {
		Log.e(tag, msg);
	}

	@Override
	public void error(String format, Object arg) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg);
			error(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
			error(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void error(String format, Object... arguments) {
		if (isErrorEnabled()) {
			FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
			error(ft.getMessage(), ft.getThrowable());
		}
	}

	@Override
	public void error(String msg, Throwable t) {
		Log.e(tag, msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return isErrorEnabled();
	}

	@Override
	public void error(Marker marker, String msg) {
		error(msg);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		error(format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		error(format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {
		error(format, arguments);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		error(msg, t);
	}
}
