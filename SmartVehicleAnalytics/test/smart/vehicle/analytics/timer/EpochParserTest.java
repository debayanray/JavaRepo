package smart.vehicle.analytics.timer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import smart.vehicle.analytics.exceptions.InvalidInputTypeException;

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
	public void testTimeUnitDetails() throws InvalidInputTypeException {
		TimeUnit atime = epochParser.convert("269123");
		assertEquals(0, atime.getDays());
		assertEquals(0, atime.getHours());
		assertEquals(4, atime.getMinutes());
		assertEquals(29, atime.getSeconds());
		assertEquals(123, atime.getMillis());
		assertEquals("[1st day - 12:04:29 AM]", atime.toPrettyString());
	}

}
