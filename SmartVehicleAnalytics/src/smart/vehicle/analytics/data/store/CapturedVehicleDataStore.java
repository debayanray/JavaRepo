package smart.vehicle.analytics.data.store;

import java.util.HashMap;
import java.util.List;

import smart.vehicle.analytics.dao.Vehicle;

public class CapturedVehicleDataStore {
	private static CapturedVehicleDataStore theInstance = new CapturedVehicleDataStore();
	private HashMap<Integer, List<Vehicle>> dayWiseCapturedVehicleData;

	protected CapturedVehicleDataStore() {
		dayWiseCapturedVehicleData = new HashMap<Integer, List<Vehicle>>();
	}
	
	public static CapturedVehicleDataStore getInstance() {
		return theInstance;
	}

	public void addDataForDay(List<Vehicle> capturedVehicleList, int whichDay) {
		dayWiseCapturedVehicleData.put(whichDay, capturedVehicleList);
		
	}

	public HashMap<Integer, List<Vehicle>> getDayWiseCapturedVehicleData() {
		return dayWiseCapturedVehicleData;
	}

	public void setDayWiseCapturedVehicleData(
			HashMap<Integer, List<Vehicle>> dayWiseCapturedVehicleData) {
		this.dayWiseCapturedVehicleData = dayWiseCapturedVehicleData;
	}
	
	

}
