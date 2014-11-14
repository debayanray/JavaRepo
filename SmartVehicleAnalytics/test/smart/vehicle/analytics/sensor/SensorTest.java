package smart.vehicle.analytics.sensor;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SensorTest {

	private Sensor sensorA;
	private Sensor sensorB;
	
	@Before
	public void setUp() throws Exception {
		sensorA = new Sensor("A");
		sensorB = new Sensor("B");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void checkingDataGeneratorPrefixForSensor() {
		Assert.assertEquals("A", sensorA.getDataGeneratorPrefix());
		Assert.assertEquals("B", sensorB.getDataGeneratorPrefix());
	}

}
