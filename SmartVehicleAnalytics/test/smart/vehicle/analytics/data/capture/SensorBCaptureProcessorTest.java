package smart.vehicle.analytics.data.capture;

import static org.junit.Assert.*;
import junit.framework.Assert;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import smart.vehicle.analytics.data.capture.SensorBCaptureProcessor;
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

@RunWith(JUnitParamsRunner.class)
public class SensorBCaptureProcessorTest {
	
	private SensorBCaptureProcessor sensorBCaptureProcessor;
	@Mock
	private SensorGeneratedDataParser sensorGeneratedDataParser;
	
	@Before
	public void setUp() throws Exception {
		sensorBCaptureProcessor = new SensorBCaptureProcessor(sensorGeneratedDataParser);
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
		String timeInfo = sensorBCaptureProcessor.stripSensorIdentifier(inputData);
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
		Assert.assertEquals(isSensorBData, sensorBCaptureProcessor.isSensorBData(inputData));
	}

}
