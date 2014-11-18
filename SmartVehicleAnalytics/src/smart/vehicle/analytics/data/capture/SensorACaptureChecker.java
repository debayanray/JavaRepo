package smart.vehicle.analytics.data.capture;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

public class SensorACaptureChecker {
	
	private SensorGeneratedDataParser sensorGeneratedDataParser;
	
	public SensorACaptureChecker(SensorGeneratedDataParser sensorGeneratedDataParser) {
		this.sensorGeneratedDataParser = sensorGeneratedDataParser;
	}

	public void processData(String line) {
		if(isSensorAData(line)) {
			String timeInfo = stripSensorIdentifier(line);
			
			Vehicle vehicle = findAnyMatchingVehicleFromSensorACapturedData(timeInfo);
			if(vehicle != null) {
				// remove from list of front axle passed thru sensor A
				sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList().remove(vehicle);
				// set the rear axle touch down on sensor A
				vehicle.setSensorARearTouchDown(timeInfo);
				
				if(hasVehicleFrontAxlePassedThruSensorB(vehicle)) {
					// set south bound - no need though, as already set when passed thru sensor B 
					vehicle.setNorthBound(false);
					// add to list of rear axle passed thru sensor A
					sensorGeneratedDataParser.getRearAxlePassedThruSensorACapturedVehicleList().add(vehicle);
				}
				else {
					// set north bound
					vehicle.setNorthBound(true);
					// add to list of fully captured vehicle data
					sensorGeneratedDataParser.getFullyCapturedVehicleList().add(vehicle);
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
				sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList().add(vehicle);
			}
			
		}
	}

	public Vehicle findAnyMatchingVehicleFromSensorACapturedData(String timeInfo) {
		long timeInfoEpoch = Long.valueOf(timeInfo).longValue();
		Vehicle match = null;
		
		for (Vehicle vehicle : sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleList()) {
			if(isWithinRangeOfRearAxlePassing(vehicle, timeInfoEpoch)) {
				match = vehicle;
				break;
			}
		}
		
		return match;
	}
	
	public boolean isWithinRangeOfRearAxlePassing(Vehicle vehicle, long timeInfoEpoch) {
		long floorTimeEpoch = vehicle.getSensorAFrontTouchDownEpoch() + 120;
		long ceilingTimeEpoch = vehicle.getSensorAFrontTouchDownEpoch() + 180;
		if((timeInfoEpoch > floorTimeEpoch) && (timeInfoEpoch < ceilingTimeEpoch)) {
			return true;
		}
		return false;
	}
	
	public boolean hasVehicleFrontAxlePassedThruSensorB(Vehicle vehicle) {
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
}
