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

final class LoggerConfig {
	static final LoggerConfig DEFAULT = new LoggerConfig();
	static {
		DEFAULT.showName = ShowName.FALSE;
		DEFAULT.showThread = false;
	}

	String tag;
	LogLevel level;
	ShowName showName;
	Boolean showThread;

	LoggerConfig() {
	}

	LoggerConfig(String tag) {
		this.tag = tag;
	}

	LoggerConfig(LogLevel level) {
		this.level = level;
	}

	LoggerConfig(ShowName showName) {
		this.showName = showName;
	}

	enum ShowName {
		FALSE, SHORT, LONG;
	}

	final boolean isComplete() {
		if (tag == null) {
			return false;
		}

		if (level == null) {
			return false;
		}

		if (showName == null) {
			return false;
		}

		if (showThread == null) {
			return false;
		}

		return true;
	}

	final boolean merge(LoggerConfig config) {
		if (config == null) {
			return isComplete();
		} else {
			boolean complete = true;

			if (tag == null) {
				tag = config.tag;
				complete = false;
			}

			if (level == null) {
				level = config.level;
				complete = false;
			}

			if (showName == null) {
				showName = config.showName;
				complete = false;
			}

			if (showThread == null) {
				showThread = config.showThread;
				complete = false;
			}

			return complete;
		}
	}
}
