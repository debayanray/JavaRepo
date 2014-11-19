package smart.vehicle.analytics.data.capture;

import java.util.ArrayList;
import java.util.List;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

public class SensorACaptureProcessor {
	
	private SensorGeneratedDataParser sensorGeneratedDataParser;
	private List<Vehicle> frontAxlePassedThruSensorACapturedVehicleList;
	private List<Vehicle> rearAxlePassedThruSensorACapturedVehicleList;
	
	public SensorACaptureProcessor(SensorGeneratedDataParser sensorGeneratedDataParser) {
		this.sensorGeneratedDataParser = sensorGeneratedDataParser;
		initCapturedVehicleList();
	}
	
	private void initCapturedVehicleList() {
		frontAxlePassedThruSensorACapturedVehicleList = new ArrayList<Vehicle>();
		rearAxlePassedThruSensorACapturedVehicleList = new ArrayList<Vehicle>();
	}
	
	public void resetCapturedVehicleList() {
		frontAxlePassedThruSensorACapturedVehicleList.clear();
		rearAxlePassedThruSensorACapturedVehicleList.clear();
	}
	
	public void nullifyCapturedVehicleList() {
		frontAxlePassedThruSensorACapturedVehicleList.clear();
		rearAxlePassedThruSensorACapturedVehicleList.clear();
		frontAxlePassedThruSensorACapturedVehicleList = null;
		rearAxlePassedThruSensorACapturedVehicleList = null;
	}
	
	public void processData(String line) {
		if(isSensorAData(line)) {
			String timeInfo = stripSensorIdentifier(line);
			
			Vehicle vehicle = findAnyMatchingVehicleFromSensorACapturedData(timeInfo);
			if(vehicle != null) {
				// remove from list of front axle passed thru sensor A
				getFrontAxlePassedThruSensorACapturedVehicleList().remove(vehicle);
				// set the rear axle touch down on sensor A
				vehicle.setSensorARearTouchDown(timeInfo);
				
				if(hasSensorBCapturedVehicleFrontAxle(vehicle)) {
					// set south bound - no need though, as already set when passed thru sensor B 
					vehicle.setNorthBound(false);
					// add to list of rear axle passed thru sensor A
					getRearAxlePassedThruSensorACapturedVehicleList().add(vehicle);
				}
				else {
					// set north bound
					vehicle.setNorthBound(true);
					// add to list of fully captured vehicle data
					sensorGeneratedDataParser.getCurrentDayCapturedVehicleList().add(vehicle);
				}
			}
			else {
				/**
				 *********************************************************
				 * for the first time a vehicle gets registered / captured
				 *********************************************************
				 */
				// create a new vehicle data
				vehicle = new Vehicle();
				// set the front axle touch down on sensor A
				vehicle.setSensorAFrontTouchDown(timeInfo);
				// add to list of front axle passed thru sensor A
				getFrontAxlePassedThruSensorACapturedVehicleList().add(vehicle);
			}
			
		}
	}

	public Vehicle findAnyMatchingVehicleFromSensorACapturedData(String timeInfo) {
		long timeInfoEpoch = Long.valueOf(timeInfo).longValue();
		Vehicle match = null;
		
		for (Vehicle vehicle : getFrontAxlePassedThruSensorACapturedVehicleList()) {
			if(isWithinRangeOfRearAxlePassing(vehicle, timeInfoEpoch)) {
				match = vehicle;
				break;
			}
		}
		
		return match;
	}
	
	public boolean isWithinRangeOfRearAxlePassing(Vehicle vehicle, long timeInfoEpoch) {
		long floorTimeEpoch = vehicle.getSensorAFrontTouchDownEpoch() + 100;
		long ceilingTimeEpoch = vehicle.getSensorAFrontTouchDownEpoch() + 180;
		if((timeInfoEpoch > floorTimeEpoch) && (timeInfoEpoch < ceilingTimeEpoch)) {
			return true;
		}
		return false;
	}
	
	public boolean hasSensorBCapturedVehicleFrontAxle(Vehicle vehicle) {
		 return sensorGeneratedDataParser.getFrontAxlePassedThruSensorBCapturedVehicleList().contains(vehicle);
	}
	
	public String stripSensorIdentifier(String line) {
		if(line.startsWith("A") || line.startsWith("B")) {
			return line.substring(1);
		}
		return line;
	}

	public boolean isSensorAData(String line) {
		return line.startsWith("A");
	}

	public List<Vehicle> getFrontAxlePassedThruSensorACapturedVehicleList() {
		return frontAxlePassedThruSensorACapturedVehicleList;
	}

	public void setFrontAxlePassedThruSensorACapturedVehicleList(
			List<Vehicle> frontAxlePassedThruSensorACapturedVehicleList) {
		this.frontAxlePassedThruSensorACapturedVehicleList = frontAxlePassedThruSensorACapturedVehicleList;
	}

	public List<Vehicle> getRearAxlePassedThruSensorACapturedVehicleList() {
		return rearAxlePassedThruSensorACapturedVehicleList;
	}

	public void setRearAxlePassedThruSensorACapturedVehicleList(
			List<Vehicle> rearAxlePassedThruSensorACapturedVehicleList) {
		this.rearAxlePassedThruSensorACapturedVehicleList = rearAxlePassedThruSensorACapturedVehicleList;
	}
}
