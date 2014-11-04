package smart.vehicle.analytics.timer;

import static org.junit.Assert.*;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import smart.vehicle.analytics.exceptions.InvalidInputTypeException;

@RunWith(JUnitParamsRunner.class)
public class EpochParserTest {
	
	private EpochParser epochParser;
	
	@Before
	public void setUp() throws Exception {
		epochParser = new EpochParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Parameters({
		"268981, 0, 0, 4, 28, 981, [1st day - 12:04:28 AM]", 
		"269123, 0, 0, 4, 29, 123, [1st day - 12:04:29 AM]", 
		"1089951, 0, 0, 18, 9, 951, [1st day - 12:18:09 AM]"
		
    })
	public void validateDetailsOfTimeUnitInstanceReturnedWhenConvertIsInvoked(String millisecondsInString, 
			long days, long hours, long minutes, long seconds, long milliseconds, String prettyTimestamp) throws InvalidInputTypeException {
		
		/*TimeUnit atime = epochParser.convert("269123");
		assertEquals(0, atime.getDays());
		assertEquals(0, atime.getHours());
		assertEquals(4, atime.getMinutes());
		assertEquals(29, atime.getSeconds());
		assertEquals(123, atime.getMillis());
		assertEquals("[1st day - 12:04:29 AM]", atime.toPrettyString());*/
		
		TimeUnit atime = epochParser.convert(millisecondsInString);
		assertEquals(days, atime.getDays());
		assertEquals(hours, atime.getHours());
		assertEquals(minutes, atime.getMinutes());
		assertEquals(seconds, atime.getSeconds());
		assertEquals(milliseconds, atime.getMillis());
		assertEquals(prettyTimestamp, atime.toPrettyString());
	}

}
