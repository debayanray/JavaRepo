package smart.vehicle.analytics.computing;

import java.util.Collection;

import smart.vehicle.analytics.dao.Vehicle;

public class AverageVehicleSpeedCalculator extends MetricsCalculator {

	@Override
	public long calculate(Collection<Vehicle> vehicles) {
		double total = 0;
		for(Vehicle vehicle : vehicles) {
			total += vehicle.getSpeed();
		}
		return (long) (total / vehicles.size());
	}

	@Override
	public String toString() {
		return "Average vehicle speed (in kph): ";
	}
	
	

}
