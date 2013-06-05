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

import android.util.Log;

/**
 * Configuration of Android logging level.
 * 
 * Levels are mapped to the corresponding SLF4J level (except ASSERT which does not exist in SLF4J).
 */
public enum LogLevel {
	/** Suppress all log messages. */
	SUPPRESS,

	/** Log messages at ERROR level and above. */
	ERROR,

	/** Log messages at WARN level and above. */
	WARN,

	/** Log messages at INFO level and above. */
	INFO,

	/** Log messages at DEBUG level and above. */
	DEBUG,

	/** Log messages at TRACE level and above. */
	VERBOSE,
	
	/** Use {@link Log#isLoggable(String, int)} to determine the log level. */
	NATIVE;
}
