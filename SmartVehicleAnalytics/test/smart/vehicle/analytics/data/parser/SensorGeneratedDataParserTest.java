package smart.vehicle.analytics.data.parser;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import smart.vehicle.analytics.computing.AverageVehicleSpeedCalculator;
import smart.vehicle.analytics.computing.TotalVehicleCountCalculator;
import smart.vehicle.analytics.computing.samples.VehiclesMovingDuringFirstHalfOfDay;
import smart.vehicle.analytics.computing.samples.VehiclesMovingDuringLatterHalfOfDay;
import smart.vehicle.analytics.computing.samples.VehiclesMovingTowardsNorth;
import smart.vehicle.analytics.computing.samples.VehiclesMovingTowardsSouth;
import smart.vehicle.analytics.core.OperationExpressionDecorator;
import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.store.CapturedVehicleDataStore;
import smart.vehicle.analytics.exceptions.NotSupportedException;

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
	public void test() throws NotSupportedException {
		
	}

}
