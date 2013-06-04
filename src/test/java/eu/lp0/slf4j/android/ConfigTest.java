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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor({ "eu.lp0.slf4j.android.LoggerFactory" })
public class ConfigTest {
	@Test
	public void tags() {
		Config config = new Config(Mockito.mock(Logger.class, Mockito.withSettings().verboseLogging()));
		Assert.assertEquals("JavaApp", config.getTag("java.net.Socket"));
		Assert.assertEquals("JavaLang", config.getTag("java.lang.Void"));
		Assert.assertEquals("JavaUtil", config.getTag("java.util.List"));
		Assert.assertEquals("JavaApp", config.getTag("java.oops.Test"));
	}

	@Test
	public void levels() {
		Config config = new Config(Mockito.mock(Logger.class, Mockito.withSettings().verboseLogging()));
		Assert.assertEquals(LogLevel.DEBUG, config.getLevel("java.net.Socket"));
		Assert.assertEquals(LogLevel.WARN, config.getLevel("java.lang.Void"));
		Assert.assertEquals(LogLevel.WARN, config.getLevel("java.util.List"));
		Assert.assertEquals(LogLevel.WARN, config.getLevel("java.oops.Test"));
	}
}
