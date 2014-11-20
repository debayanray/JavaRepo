package smart.vehicle.analytics.data.parser;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		parser.setFileAsInput("D:/GitHubRepos/JavaRepo/SmartVehicleAnalytics/sensorData.txt");
		parser.parse();
		
		System.out.println(CapturedVehicleDataStore.getInstance().getDayWiseCapturedVehicleData());
		
		
		VehiclesMovingTowardsNorth vehiclesMovingTowardsNorth = new VehiclesMovingTowardsNorth(
				CapturedVehicleDataStore.getInstance().getAllCapturedVehicleData());
		System.out.println(vehiclesMovingTowardsNorth + " during the complete session = " + vehiclesMovingTowardsNorth.compute().size());
		
		VehiclesMovingTowardsSouth vehiclesMovingTowardsSouth = new VehiclesMovingTowardsSouth(
				CapturedVehicleDataStore.getInstance().getAllCapturedVehicleData());
		System.out.println(vehiclesMovingTowardsSouth + " during the complete session = " + vehiclesMovingTowardsSouth.compute().size());
		
		VehiclesMovingDuringFirstHalfOfDay vehiclesMovingDuringFirstHalfOfDay = new VehiclesMovingDuringFirstHalfOfDay(
				CapturedVehicleDataStore.getInstance().getAllCapturedVehicleData());
		System.out.println(vehiclesMovingDuringFirstHalfOfDay + " during the complete session = " + vehiclesMovingDuringFirstHalfOfDay.compute().size());
		
		VehiclesMovingDuringLatterHalfOfDay vehiclesMovingDuringLatterHalfOfDay = new VehiclesMovingDuringLatterHalfOfDay(
				CapturedVehicleDataStore.getInstance().getAllCapturedVehicleData());
		System.out.println(vehiclesMovingDuringLatterHalfOfDay + " during the complete session = " + vehiclesMovingDuringLatterHalfOfDay.compute().size());
		
		OperationExpressionDecorator vehiclesMovingTowardsNorthAndDuringFirstHalfOfDay = new OperationExpressionDecorator(vehiclesMovingDuringFirstHalfOfDay) {
			
			@Override
			protected Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) throws NotSupportedException {
				return new VehiclesMovingTowardsNorth(contextualVehicleCollection).compute();
			}

			@Override
			protected String what() {
				return new VehiclesMovingTowardsNorth(null).toString();
			}
			
		};
		System.out.println(vehiclesMovingTowardsNorthAndDuringFirstHalfOfDay + " during the complete session = " + vehiclesMovingTowardsNorthAndDuringFirstHalfOfDay.compute().size());
		
		OperationExpressionDecorator vehiclesMovingTowardsSouthAndDuringFirstHalfOfDay = new OperationExpressionDecorator(vehiclesMovingDuringFirstHalfOfDay) {
			
			@Override
			protected Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) throws NotSupportedException {
				return new VehiclesMovingTowardsSouth(contextualVehicleCollection).compute();
			}

			@Override
			protected String what() {
				return new VehiclesMovingTowardsSouth(null).toString();
			}
			
		};
		System.out.println(vehiclesMovingTowardsSouthAndDuringFirstHalfOfDay + " during the complete session = " + vehiclesMovingTowardsSouthAndDuringFirstHalfOfDay.compute().size());
		
	}

}
