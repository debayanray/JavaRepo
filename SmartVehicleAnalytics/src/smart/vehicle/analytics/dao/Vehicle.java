package smart.vehicle.analytics.dao;

import smart.vehicle.analytics.exceptions.InvalidInputTypeException;
import smart.vehicle.analytics.timer.EpochParser;
import smart.vehicle.analytics.timer.TimeUnit;

public class Vehicle {
	
	private static final int MPMS_TO_KMPH_MULTIPLYING_FACTOR = 3600;
	private static final double AVEARGE_WHEELBASE = 2.5;
	
	private String sensorAFrontTouchDown;
	private String sensorARearTouchDown;
	private String sensorBFrontTouchDown;
	private String sensorBRearTouchDown;
	
	private long sensorAFrontTouchDownEpoch;
	private long sensorARearTouchDownEpoch;
	private long sensorBFrontTouchDownEpoch;
	private long sensorBRearTouchDownEpoch;
	
	private boolean isNorthBound;
	private TimeUnit passingTime; // first touch down on sensor A
	private double speed; // in kph
	
	public Vehicle() {
		
	}
	
	public String getSensorAFrontTouchDown() {
		return sensorAFrontTouchDown;
	}
	public void setSensorAFrontTouchDown(String sensorAFrontTouchDown) {
		this.sensorAFrontTouchDown = sensorAFrontTouchDown;
		this.sensorAFrontTouchDownEpoch = getCorrespondingPremitiveLongValueFrom(this.sensorAFrontTouchDown);
		
		setPassingTime();
	}
	public String getSensorARearTouchDown() {
		return sensorARearTouchDown;
	}
	public void setSensorARearTouchDown(String sensorARearTouchDown) {
		this.sensorARearTouchDown = sensorARearTouchDown;
		this.sensorARearTouchDownEpoch = getCorrespondingPremitiveLongValueFrom(this.sensorARearTouchDown);
		
		setSpeed();
	}
	public String getSensorBFrontTouchDown() {
		return sensorBFrontTouchDown;
	}
	public void setSensorBFrontTouchDown(String sensorBFrontTouchDown) {
		this.sensorBFrontTouchDown = sensorBFrontTouchDown;
		this.sensorBFrontTouchDownEpoch = getCorrespondingPremitiveLongValueFrom(this.sensorBFrontTouchDown);
	}
	public String getSensorBRearTouchDown() {
		return sensorBRearTouchDown;
	}
	public void setSensorBRearTouchDown(String sensorBRearTouchDown) {
		this.sensorBRearTouchDown = sensorBRearTouchDown;
		this.sensorBRearTouchDownEpoch = getCorrespondingPremitiveLongValueFrom(this.sensorBRearTouchDown);
	}
	public long getSensorAFrontTouchDownEpoch() {
		return sensorAFrontTouchDownEpoch;
	}
	public long getSensorARearTouchDownEpoch() {
		return sensorARearTouchDownEpoch;
	}
	public long getSensorBFrontTouchDownEpoch() {
		return sensorBFrontTouchDownEpoch;
	}
	public long getSensorBRearTouchDownEpoch() {
		return sensorBRearTouchDownEpoch;
	}
	public boolean isNorthBound() {
		return isNorthBound;
	}
	public void setNorthBound(boolean isNorthBound) {
		this.isNorthBound = isNorthBound;
	}
	public TimeUnit getPassingTime() {
		return passingTime;
	}
	
	private void setPassingTime() {
		try {
			passingTime = new EpochParser().convert(sensorAFrontTouchDown);
		} catch (InvalidInputTypeException e) {
			e.printStackTrace();
		}
	}
	
	public double getSpeed() {
		return speed;
	}
	
	private void setSpeed() {
		long timeDifference = sensorARearTouchDownEpoch - sensorAFrontTouchDownEpoch;
		speed = (AVEARGE_WHEELBASE * MPMS_TO_KMPH_MULTIPLYING_FACTOR) / timeDifference;
	}
	
	private long getCorrespondingPremitiveLongValueFrom(String sensorTouchDown) {
		return Long.valueOf(sensorTouchDown).longValue();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(isNorthBound() ? "Northbound " : "Southbound ");
		sb.append("vehicle");
		
		sb.append("(@");
		sb.append(String.format("%.2f", speed));
		sb.append(" kph)");
		
		return sb.toString(); 
	}
	
	public String toDetailedString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.toString());
		sb.append(" - ");
		
		sb.append("Sensor A [");
		sb.append(sensorAFrontTouchDown);
		sb.append(", ");
		sb.append(sensorARearTouchDown);
		sb.append("]");
		
		sb.append(", ");
		
		sb.append("Sensor B [");
		sb.append(sensorBFrontTouchDown);
		sb.append(", ");
		sb.append(sensorBRearTouchDown);
		sb.append("]");
		
		return sb.toString(); 
	}
	
}
