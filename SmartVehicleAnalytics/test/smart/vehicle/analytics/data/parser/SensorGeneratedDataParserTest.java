package smart.vehicle.analytics.data.parser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SensorGeneratedDataParserTest {
	
	SensorGeneratedDataParser parser;
	
	@Before
	public void setUp() throws Exception {
		parser = new SensorGeneratedDataParser();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		parser.setFileAsInput("D:/GitHubRepos/JavaRepo/SmartVehicleAnalytics/sensorData.txt");
		parser.parse();
	}

}
