package smart.vehicle.analytics.dao;

public class Vehicle {
	
	/*private String make;
	private String model;
	private String registration;*/
	
	private String sensorAFrontTouchDown;
	private String sensorARearTouchDown;
	private String sensorBFrontTouchDown;
	private String sensorBRearTouchDown;
	
	private long sensorAFrontTouchDownEpoch;
	private long sensorARearTouchDownEpoch;
	private long sensorBFrontTouchDownEpoch;
	private long sensorBRearTouchDownEpoch;
	
	private boolean isNorthBound;
	
	/*public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}*/
	
	public String getSensorAFrontTouchDown() {
		return sensorAFrontTouchDown;
	}
	public void setSensorAFrontTouchDown(String sensorAFrontTouchDown) {
		this.sensorAFrontTouchDown = sensorAFrontTouchDown;
		this.sensorAFrontTouchDownEpoch = getCorrespondingPremitiveLongValueFrom(this.sensorAFrontTouchDown);
	}
	public String getSensorARearTouchDown() {
		return sensorARearTouchDown;
	}
	public void setSensorARearTouchDown(String sensorARearTouchDown) {
		this.sensorARearTouchDown = sensorARearTouchDown;
		this.sensorARearTouchDownEpoch = getCorrespondingPremitiveLongValueFrom(this.sensorARearTouchDown);
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
	private long getCorrespondingPremitiveLongValueFrom(String sensorTouchDown) {
		return Long.valueOf(sensorTouchDown).longValue();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(isNorthBound() ? "Northbound " : "Southbound ");
		sb.append("vehicle - ");
		
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
