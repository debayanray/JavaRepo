package smart.vehicle.analytics.data.capture;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

@RunWith(JUnitParamsRunner.class)
public class SensorACaptureProcessorTest {
	
	@Mock
	SensorGeneratedDataParser sensorGeneratedDataParser;
	SensorACaptureProcessor sensorACaptureProcessorSpy;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sensorACaptureProcessorSpy = Mockito.spy(new SensorACaptureProcessor(sensorGeneratedDataParser));
	}

	@Test
	@Parameters({
		"B86328902, 0",
		"A1089807, 1",
		"B1089810, 0"
    })
	public void stripSensorIdentifierCalledSuitablyWhileDataProcessingHappensAndDataIsMeantForSensorAProcessor(
			String lineToBeProcessed, int numberOfTimes) {
		
		sensorACaptureProcessorSpy.processData(lineToBeProcessed);
		Mockito.verify(sensorACaptureProcessorSpy, Mockito.times(numberOfTimes)).stripSensorIdentifier(lineToBeProcessed);
	}
	
	@Test
	public void newVehicleCreatedAndAddedToFrontAxlePassedThruSensorACapturedVehicleListIfNOMatchFound() {
		// Given
		Mockito.doReturn(null).when(sensorACaptureProcessorSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorACaptureProcessorSpy.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureProcessorSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).add(Mockito.any(Vehicle.class));
	}
	
	@Test
	public void matchingVehicleGetsRemovedFromFrontAxlePassedThruSensorACapturedVehicleListIfMatchFound() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureProcessorSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorACaptureProcessorSpy.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureProcessorSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).remove(myVehicle);
	}
	
	@Test
	public void setRearAxleTimeInfoForMatchingVehicleIfMatchFoundFromFrontAxlePassedThruSensorACapturedVehicleList() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureProcessorSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		// When
		sensorACaptureProcessorSpy.processData("A1089807");
		// Then
		Mockito.verify(myVehicle).setSensorARearTouchDown("1089807");
	}
	
	@Test
	public void setMatchingVehicleSouthboundIfMatchFoundInFrontAxle_A_List_And_AlreadyCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureProcessorSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(true).when(sensorACaptureProcessorSpy).hasSensorBCapturedVehicleFrontAxle(myVehicle);
		// When
		sensorACaptureProcessorSpy.processData("A1089807");
		// Then
		Mockito.verify(myVehicle).setNorthBound(false);
	}
	
	@Test
	public void matchingVehicleGetsAddedToRearAxle_A_ListIfMatchFoundInFrontAxle_A_List_And_AlreadyCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureProcessorSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(true).when(sensorACaptureProcessorSpy).hasSensorBCapturedVehicleFrontAxle(myVehicle);
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorACaptureProcessorSpy.getRearAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureProcessorSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).add(myVehicle);
	}
	
	@Test
	public void setMatchingVehicleNorthboundIfMatchFoundInFrontAxle_A_List_And_NOTCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureProcessorSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(false).when(sensorACaptureProcessorSpy).hasSensorBCapturedVehicleFrontAxle(myVehicle);
		// When
		sensorACaptureProcessorSpy.processData("A1089807");
		// Then
		Mockito.verify(myVehicle).setNorthBound(true);
	}
	
	@Test
	public void matchingVehicleGetsAddedToFullyCapturedVehicleListIfMatchFoundInFrontAxle_A_List_And_NOTCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureProcessorSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(false).when(sensorACaptureProcessorSpy).hasSensorBCapturedVehicleFrontAxle(myVehicle);
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorGeneratedDataParser.getCurrentDayCapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureProcessorSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).add(myVehicle);
	}
	
	@Test
	public void findMatchingVehicleReturns_NULL_IfNoVehicleFoundWithinRangeOfRearAxlePassing() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(false).when(sensorACaptureProcessorSpy).isWithinRangeOfRearAxlePassing(Mockito.any(Vehicle.class), Mockito.anyLong());
		List<Vehicle> myOwnList = new ArrayList<Vehicle>();
		myOwnList.add(new Vehicle());
		myOwnList.add(new Vehicle());
		myOwnList.add(new Vehicle());
		Mockito.when(sensorACaptureProcessorSpy.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		Vehicle match = sensorACaptureProcessorSpy.findAnyMatchingVehicleFromSensorACapturedData("1089807");
		// Then
		Assert.assertNull(match);
	}
	
	@Test
	public void findMatchingVehicleReturnsMatchedVehicleIfVehicleIsWithinRangeOfRearAxlePassing() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(true).when(sensorACaptureProcessorSpy).isWithinRangeOfRearAxlePassing(Mockito.eq(myVehicle), Mockito.anyLong());
		List<Vehicle> myOwnList = new ArrayList<Vehicle>();
		myOwnList.add(new Vehicle());
		myOwnList.add(myVehicle); // mock vehicle
		myOwnList.add(new Vehicle());
		Mockito.when(sensorACaptureProcessorSpy.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		Vehicle match = sensorACaptureProcessorSpy.findAnyMatchingVehicleFromSensorACapturedData("1089807");
		// Then
		Assert.assertEquals(myVehicle, match);
	}
}
