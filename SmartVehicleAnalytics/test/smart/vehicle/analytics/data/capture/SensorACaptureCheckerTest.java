package smart.vehicle.analytics.data.capture;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

public class SensorACaptureCheckerTest {
	
	@Mock
	SensorGeneratedDataParser sensorGeneratedDataParser;
	SensorACaptureChecker sensorACaptureChecker;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		sensorACaptureChecker = new SensorACaptureChecker(sensorGeneratedDataParser);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
