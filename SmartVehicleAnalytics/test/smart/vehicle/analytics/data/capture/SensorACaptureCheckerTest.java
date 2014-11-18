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
public class SensorACaptureCheckerTest {
	
	@Mock
	SensorGeneratedDataParser sensorGeneratedDataParser;
	SensorACaptureChecker sensorACaptureChecker;
	SensorACaptureChecker sensorACaptureCheckerSpy;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sensorACaptureChecker = new SensorACaptureChecker(sensorGeneratedDataParser);
		sensorACaptureCheckerSpy = Mockito.spy(sensorACaptureChecker);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Parameters({
		"B86328902, 0",
		"A1089807, 1",
		"B1089810, 0"
    })
	public void stripSensorIdentifierCalledSuitablyWhileDataProcessingHappensAndDataIsMeantForSensorAProcessor(
			String lineToBeProcessed, int numberOfTimes) {
		
		sensorACaptureCheckerSpy.processData(lineToBeProcessed);
		Mockito.verify(sensorACaptureCheckerSpy, Mockito.times(numberOfTimes)).stripSensorIdentifier(lineToBeProcessed);
	}
	
	@Test
	public void newVehicleCreatedAndAddedToFrontAxlePassedThruSensorACapturedVehicleListIfNOMatchFound() {
		// Given
		Mockito.doReturn(null).when(sensorACaptureCheckerSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureCheckerSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).add(Mockito.any(Vehicle.class));
	}
	
	@Test
	public void matchingVehicleGetsRemovedFromFrontAxlePassedThruSensorACapturedVehicleListIfMatchFound() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureCheckerSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureCheckerSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).remove(myVehicle);
	}
	
	@Test
	public void setRearAxleTimeInfoForMatchingVehicleIfMatchFoundFromFrontAxlePassedThruSensorACapturedVehicleList() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureCheckerSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		// When
		sensorACaptureCheckerSpy.processData("A1089807");
		// Then
		Mockito.verify(myVehicle).setSensorARearTouchDown("1089807");
	}
	
	@Test
	public void setMatchingVehicleSouthboundIfMatchFoundInFrontAxle_A_List_And_AlreadyCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureCheckerSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(true).when(sensorACaptureCheckerSpy).hasVehicleFrontAxlePassedThruSensorB(myVehicle);
		// When
		sensorACaptureCheckerSpy.processData("A1089807");
		// Then
		Mockito.verify(myVehicle).setNorthBound(false);
	}
	
	@Test
	public void matchingVehicleGetsAddedToRearAxle_A_ListIfMatchFoundInFrontAxle_A_List_And_AlreadyCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureCheckerSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(true).when(sensorACaptureCheckerSpy).hasVehicleFrontAxlePassedThruSensorB(myVehicle);
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorGeneratedDataParser.getRearAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureCheckerSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).add(myVehicle);
	}
	
	@Test
	public void setMatchingVehicleNorthboundIfMatchFoundInFrontAxle_A_List_And_NOTCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureCheckerSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(false).when(sensorACaptureCheckerSpy).hasVehicleFrontAxlePassedThruSensorB(myVehicle);
		// When
		sensorACaptureCheckerSpy.processData("A1089807");
		// Then
		Mockito.verify(myVehicle).setNorthBound(true);
	}
	
	@Test
	public void matchingVehicleGetsAddedToFullyCapturedVehicleListIfMatchFoundInFrontAxle_A_List_And_NOTCapturedInSensorB() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(myVehicle).when(sensorACaptureCheckerSpy).findAnyMatchingVehicleFromSensorACapturedData(Mockito.anyString());
		Mockito.doReturn(false).when(sensorACaptureCheckerSpy).hasVehicleFrontAxlePassedThruSensorB(myVehicle);
		List<Vehicle> myOwnList = Mockito.mock(List.class);
		Mockito.when(sensorGeneratedDataParser.getFullyCapturedVehicleList()).thenReturn(myOwnList);
		// When
		sensorACaptureCheckerSpy.processData("A1089807");
		// Then
		Mockito.verify(myOwnList).add(myVehicle);
	}
	
	@Test
	public void findMatchingVehicleReturns_NULL_IfNoVehicleFoundWithinRangeOfRearAxlePassing() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(false).when(sensorACaptureCheckerSpy).isWithinRangeOfRearAxlePassing(Mockito.any(Vehicle.class), Mockito.anyLong());
		List<Vehicle> myOwnList = new ArrayList<Vehicle>();
		myOwnList.add(new Vehicle());
		myOwnList.add(new Vehicle());
		myOwnList.add(new Vehicle());
		Mockito.when(sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		Vehicle match = sensorACaptureCheckerSpy.findAnyMatchingVehicleFromSensorACapturedData("1089807");
		// Then
		Assert.assertNull(match);
	}
	
	@Test
	public void findMatchingVehicleReturnsMatchedVehicleIfVehicleIsWithinRangeOfRearAxlePassing() {
		// Given
		Vehicle myVehicle = Mockito.mock(Vehicle.class);
		Mockito.doReturn(true).when(sensorACaptureCheckerSpy).isWithinRangeOfRearAxlePassing(Mockito.eq(myVehicle), Mockito.anyLong());
		List<Vehicle> myOwnList = new ArrayList<Vehicle>();
		myOwnList.add(new Vehicle());
		myOwnList.add(myVehicle); // mock vehicle
		myOwnList.add(new Vehicle());
		Mockito.when(sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList()).thenReturn(myOwnList);
		// When
		Vehicle match = sensorACaptureCheckerSpy.findAnyMatchingVehicleFromSensorACapturedData("1089807");
		// Then
		Assert.assertEquals(myVehicle, match);
	}
}
