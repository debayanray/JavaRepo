package smart.vehicle.analytics.data.capture;

import java.util.ArrayList;
import java.util.List;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

public class SensorBCaptureProcessor {

	private SensorGeneratedDataParser sensorGeneratedDataParser;
	private List<Vehicle> frontAxlePassedThruSensorBCapturedVehicleList;

	public SensorBCaptureProcessor(SensorGeneratedDataParser sensorGeneratedDataParser) {
		this.sensorGeneratedDataParser = sensorGeneratedDataParser;
		initCapturedVehicleList();
	}
	
	private void initCapturedVehicleList() {
		frontAxlePassedThruSensorBCapturedVehicleList = new ArrayList<Vehicle>();
	}
	
	public void resetCapturedVehicleList() {
		frontAxlePassedThruSensorBCapturedVehicleList.clear();
	}
	
	public void nullifyCapturedVehicleList() {
		frontAxlePassedThruSensorBCapturedVehicleList.clear();
		frontAxlePassedThruSensorBCapturedVehicleList = null;
	}
	
	public void processData(String line) {
		if(isSensorBData(line)) {
			String timeInfo = stripSensorIdentifier(line);
			
			Vehicle matchedVehicle = findAnyMatchingVehicleFromSensorAAndSenorBCapturedData(timeInfo);
			if(matchedVehicle != null) {
				// remove from list of front axle passed thru sensor B
				getFrontAxlePassedThruSensorBCapturedVehicleList().remove(matchedVehicle);
				// remove from list of rear axle passed thru sensor A
				sensorGeneratedDataParser.getRearAxlePassedThruSensorACapturedVehicleList().remove(matchedVehicle);
				// set the rear axle touch down on sensor B
				matchedVehicle.setSensorBRearTouchDown(timeInfo);
				// add to list of fully captured vehicle data
				sensorGeneratedDataParser.getCurrentDayCapturedVehicleList().add(matchedVehicle);
			}
			else {
				matchedVehicle = findAnyMatchingVehicleFromSensorACapturedData(timeInfo);
				if(matchedVehicle != null) {
					// set the front axle touch down on sensor B
					matchedVehicle.setSensorBFrontTouchDown(timeInfo);
					// set south bound
					matchedVehicle.setNorthBound(false);
					// add to list of front axle passed thru sensor B
					getFrontAxlePassedThruSensorBCapturedVehicleList().add(matchedVehicle);
				}
			}
			
		}
	}

	private Vehicle findAnyMatchingVehicleFromSensorAAndSenorBCapturedData(String timeInfo) {
		long timeInfoEpoch = Long.valueOf(timeInfo).longValue();
		Vehicle match = null;
		
		for (Vehicle vehicle : getFrontAxlePassedThruSensorBCapturedVehicleList()) {
			if(isWithinRangeOfRearAxlePassing(vehicle, timeInfoEpoch)) {
				match = vehicle;
				
				// to be doubly sure
				if(hasSensorACapturedVehicleRearAxle(match)) {
					break;
				}
				else {
					match = null;
				}
			}
		}
		
		return match;
	}
	
	private boolean hasSensorACapturedVehicleRearAxle(Vehicle vehicle) {
		return sensorGeneratedDataParser.getRearAxlePassedThruSensorACapturedVehicleList().contains(vehicle);
	}

	private Vehicle findAnyMatchingVehicleFromSensorACapturedData(String timeInfo) {
		long timeInfoEpoch = Long.valueOf(timeInfo).longValue();
		Vehicle match = null;
		
		for (Vehicle vehicle : sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList()) {
			if(isWithinRangeOfSensorBFrontAxleCapture(vehicle, timeInfoEpoch)) {
				match = vehicle;
				break;
			}
		}
		
		return match;
	}

	private boolean isWithinRangeOfRearAxlePassing(Vehicle vehicle, long timeInfoEpoch) {
		long floorTimeEpoch = vehicle.getSensorBFrontTouchDownEpoch() + 100;
		long ceilingTimeEpoch = vehicle.getSensorBFrontTouchDownEpoch() + 180;
		if((timeInfoEpoch > floorTimeEpoch) && (timeInfoEpoch < ceilingTimeEpoch)) {
			return true;
		}
		return false;
	}
	
	private boolean isWithinRangeOfSensorBFrontAxleCapture(Vehicle vehicle, long timeInfoEpoch) {
		long floorTimeEpoch = vehicle.getSensorAFrontTouchDownEpoch() + 2;
		long ceilingTimeEpoch = vehicle.getSensorAFrontTouchDownEpoch() + 8;
		if((timeInfoEpoch > floorTimeEpoch) && (timeInfoEpoch < ceilingTimeEpoch)) {
			return true;
		}
		return false;
	}
	
	public String stripSensorIdentifier(String line) {
		if(line.startsWith("A") || line.startsWith("B")) {
			return line.substring(1);
		}
		return line;
	}

	public boolean isSensorBData(String line) {
		return line.startsWith("B");
	}

	public List<Vehicle> getFrontAxlePassedThruSensorBCapturedVehicleList() {
		return frontAxlePassedThruSensorBCapturedVehicleList;
	}

	public void setFrontAxlePassedThruSensorBCapturedVehicleList(
			List<Vehicle> frontAxlePassedThruSensorBCapturedVehicleList) {
		this.frontAxlePassedThruSensorBCapturedVehicleList = frontAxlePassedThruSensorBCapturedVehicleList;
	}

}
