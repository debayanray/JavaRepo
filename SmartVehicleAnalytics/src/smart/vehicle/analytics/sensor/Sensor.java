package smart.vehicle.analytics.sensor;

import java.util.ArrayList;
import java.util.List;

import smart.vehicle.analytics.dao.Vehicle;

public class Sensor {

	private String name;
	private List<Vehicle> capturedVehicles;

	public Sensor(String name) {
		this.name = name;
		capturedVehicles = new ArrayList<Vehicle>();
	}
	
	
	
	
	
	
	public String getDataGeneratorPrefix() {
		return name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
