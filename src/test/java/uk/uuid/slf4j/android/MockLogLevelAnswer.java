/**
 * Copyright 2013,2022  Simon Arlott
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

import org.junit.Assert;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.util.Log;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({ "THROWS_METHOD_THROWS_CLAUSE_THROWABLE" })
public class MockLogLevelAnswer implements Answer<Boolean> {
	private LogLevel level;

	public MockLogLevelAnswer(LogLevel level) {
		this.level = level;
	}

	@Override
	public Boolean answer(InvocationOnMock invocation) throws Throwable {
		Boolean ret;
		int arg = (Integer)invocation.getArguments()[1];

		switch (arg) {
		case Log.ERROR:
			ret = level.compareTo(LogLevel.ERROR) >= 0;
			break;

		case Log.WARN:
			ret = level.compareTo(LogLevel.WARN) >= 0;
			break;

		case Log.INFO:
			ret = level.compareTo(LogLevel.INFO) >= 0;
			break;

		case Log.DEBUG:
			ret = level.compareTo(LogLevel.DEBUG) >= 0;
			break;

		case Log.VERBOSE:
			ret = level.compareTo(LogLevel.VERBOSE) >= 0;
			break;

		default:
			Assert.fail("Unsupported log level " + arg);
			ret = null;
			break;
		}

		// System.out.println("MockLogLevelAnswer(" + invocation.getArguments()[0] + ", " + invocation.getArguments()[1] + ") = " + ret);
		return ret;
	}
}
