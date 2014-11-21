package smart.vehicle.analytics.main;

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
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;
import smart.vehicle.analytics.data.store.CapturedVehicleDataStore;
import smart.vehicle.analytics.exceptions.NotSupportedException;

public class ApplicationTest {
	
	SensorGeneratedDataParser parser;
	
	TotalVehicleCountCalculator totalVehicleCountCalculator;
	AverageVehicleSpeedCalculator averageVehicleSpeedCalculator;
	
	@Before
	public void setUp() throws Exception {
		parser = new SensorGeneratedDataParser();
		
		totalVehicleCountCalculator = new TotalVehicleCountCalculator();
		averageVehicleSpeedCalculator = new AverageVehicleSpeedCalculator();
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
		System.out.println(totalVehicleCountCalculator.toString() + 
				vehiclesMovingTowardsNorth.toString() + 
				" during the complete session = " + 
				totalVehicleCountCalculator.calculate(vehiclesMovingTowardsNorth.compute()));
		
		VehiclesMovingTowardsSouth vehiclesMovingTowardsSouth = new VehiclesMovingTowardsSouth(
				CapturedVehicleDataStore.getInstance().getAllCapturedVehicleData());
		System.out.println(totalVehicleCountCalculator.toString() + 
				vehiclesMovingTowardsSouth.toString() + 
				" during the complete session = " + 
				totalVehicleCountCalculator.calculate(vehiclesMovingTowardsSouth.compute()));
		
		VehiclesMovingDuringFirstHalfOfDay vehiclesMovingDuringFirstHalfOfDay = new VehiclesMovingDuringFirstHalfOfDay(
				CapturedVehicleDataStore.getInstance().getAllCapturedVehicleData());
		System.out.println(totalVehicleCountCalculator.toString() + 
				vehiclesMovingDuringFirstHalfOfDay.toString() + 
				" during the complete session = " + 
				totalVehicleCountCalculator.calculate(vehiclesMovingDuringFirstHalfOfDay.compute()));
		System.out.println(averageVehicleSpeedCalculator.toString() + 
				vehiclesMovingDuringFirstHalfOfDay.toString() + 
				" during the complete session = " + 
				averageVehicleSpeedCalculator.calculate(vehiclesMovingDuringFirstHalfOfDay.compute()));
		
		VehiclesMovingDuringLatterHalfOfDay vehiclesMovingDuringLatterHalfOfDay = new VehiclesMovingDuringLatterHalfOfDay(
				CapturedVehicleDataStore.getInstance().getAllCapturedVehicleData());
		System.out.println(totalVehicleCountCalculator.toString() + 
				vehiclesMovingDuringLatterHalfOfDay.toString() + 
				" during the complete session = " + 
				totalVehicleCountCalculator.calculate(vehiclesMovingDuringLatterHalfOfDay.compute()));
		
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
		System.out.println(totalVehicleCountCalculator.toString() + 
				vehiclesMovingTowardsNorthAndDuringFirstHalfOfDay.toString() + 
				" during the complete session = " + 
				totalVehicleCountCalculator.calculate(vehiclesMovingTowardsNorthAndDuringFirstHalfOfDay.compute()));
		
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
		System.out.println(totalVehicleCountCalculator.toString() + 
				vehiclesMovingTowardsSouthAndDuringFirstHalfOfDay.toString() + 
				" during the complete session = " + 
				totalVehicleCountCalculator.calculate(vehiclesMovingTowardsSouthAndDuringFirstHalfOfDay.compute()));
		
		System.out.println(averageVehicleSpeedCalculator.toString() + 
				vehiclesMovingTowardsSouthAndDuringFirstHalfOfDay.toString() + 
				" during the complete session = " + 
				averageVehicleSpeedCalculator.calculate(vehiclesMovingTowardsSouthAndDuringFirstHalfOfDay.compute()));
		
	}

}
