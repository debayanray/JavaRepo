package smart.vehicle.analytics.data.capture;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.data.parser.SensorGeneratedDataParser;

public class SensorBCaptureChecker {

	private SensorGeneratedDataParser sensorGeneratedDataParser;

	public SensorBCaptureChecker(SensorGeneratedDataParser sensorGeneratedDataParser) {
		this.sensorGeneratedDataParser = sensorGeneratedDataParser;
	}

	public void checkData(String line) {
		if(isSensorBData(line)) {
			String timeInfo = stripSensorIdentifier(line);
			
			Vehicle matchedVehicle = findAnyMatchingVehicleFromSensorAAndSenorBCapturedData(timeInfo);
			if(matchedVehicle != null) {
				sensorGeneratedDataParser.getFrontAxlePassedThruSensorBCapturedVehicleData().remove(matchedVehicle);
				sensorGeneratedDataParser.getRearAxlePassedThruSensorACapturedVehicleData().remove(matchedVehicle);
				matchedVehicle.setSensorBRearTouchDown(timeInfo);
				sensorGeneratedDataParser.getFullyCapturedVehicleData().add(matchedVehicle);
			}
			else {
				matchedVehicle = findAnyMatchingVehicleFromSensorACapturedData(timeInfo);
				if(matchedVehicle != null) {
					matchedVehicle.setSensorBFrontTouchDown(timeInfo);
					sensorGeneratedDataParser.getFrontAxlePassedThruSensorBCapturedVehicleData().add(matchedVehicle);
				}
			}
			
		}
	}

	private Vehicle findAnyMatchingVehicleFromSensorAAndSenorBCapturedData(String timeInfo) {
		long timeInfoEpoch = Long.valueOf(timeInfo).longValue();
		Vehicle match = null;
		Vehicle verifyMatch = null;
		
		for (Vehicle vehicle : sensorGeneratedDataParser.getFrontAxlePassedThruSensorBCapturedVehicleData()) {
			if(isWithinRangeOfRearAxlePassing(vehicle, timeInfoEpoch)) {
				match = vehicle;
				break;
			}
		}
		
		for (Vehicle vehicle : sensorGeneratedDataParser.getRearAxlePassedThruSensorACapturedVehicleData()) {
			if(isWithinRangeOfSensorBIntercepting(vehicle, timeInfoEpoch)) {
				verifyMatch = vehicle;
				break;
			}
		}
		
		if(match == verifyMatch) {
			return match;
		}
		
		return null;
	}
	
	private Vehicle findAnyMatchingVehicleFromSensorACapturedData(String timeInfo) {
		long timeInfoEpoch = Long.valueOf(timeInfo).longValue();
		Vehicle match = null;
		
		for (Vehicle vehicle : sensorGeneratedDataParser.getFrontAxlePassedThruSensorACapturedVehicleData()) {
			if(isWithinRangeOfSensorBIntercepting(vehicle, timeInfoEpoch)) {
				match = vehicle;
				break;
			}
		}
		
		return match;
	}

	private boolean isWithinRangeOfRearAxlePassing(Vehicle vehicle, long timeInfoEpoch) {
		long floorTimeEpoch = vehicle.getSensorBRearTouchDownEpoch() + 120;
		long ceilingTimeEpoch = vehicle.getSensorBRearTouchDownEpoch() + 180;
		if((timeInfoEpoch > floorTimeEpoch) && (timeInfoEpoch < ceilingTimeEpoch)) {
			return true;
		}
		return false;
	}
	
	private boolean isWithinRangeOfSensorBIntercepting(Vehicle vehicle, long timeInfoEpoch) {
		long floorTimeEpoch = vehicle.getSensorARearTouchDownEpoch() + 2;
		long ceilingTimeEpoch = vehicle.getSensorARearTouchDownEpoch() + 8;
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

}
