package smart.vehicle.analytics.core;

import static org.junit.Assert.*;
import junit.framework.Assert;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import smart.vehicle.analytics.data.capture.SensorBCaptureChecker;
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

@RunWith(JUnitParamsRunner.class)
public class SensorBCaptureCheckerTest {
	
	private SensorBCaptureChecker sensorBCaptureChecker;
	@Mock
	private SensorGeneratedDataParser sensorGeneratedDataParser;
	
	@Before
	public void setUp() throws Exception {
		sensorBCaptureChecker = new SensorBCaptureChecker(sensorGeneratedDataParser);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Parameters({
		"B86328902, 86328902",
		"B582789, 582789",
		"A1089807, 1089807"
    })
	public void testStripSensorIdentifier(String inputData, String expectedStrippedTimeInfo) {
		String timeInfo = sensorBCaptureChecker.stripSensorIdentifier(inputData);
		Assert.assertEquals(expectedStrippedTimeInfo, timeInfo);
	}

	@Test
	@Parameters({
		"true, B86328902",
		"true, B582789",
		"false, A1089807", 
		"false, A582787", 
		"false, AXXXXXX",
		"true, BXXXXXX"
    })
	public void testIsSensorBData(boolean isSensorBData, String inputData) {
		Assert.assertEquals(isSensorBData, sensorBCaptureChecker.isSensorBData(inputData));
	}

}
