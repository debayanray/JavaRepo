package smart.vehicle.analytics.computing;

import java.util.Collection;

import smart.vehicle.analytics.dao.Vehicle;

public class TotalVehicleCountCalculator extends MetricsCalculator {

	@Override
	public long calculate(Collection<Vehicle> vehicles) {
		return vehicles.size();
	}
	
	@Override
	public String toString() {
		return "Total vehicle count: ";
	}

}
