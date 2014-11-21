package smart.vehicle.analytics.computing;

import java.util.Collection;

import smart.vehicle.analytics.dao.Vehicle;

public abstract class MetricsCalculator {
	
	public abstract long calculate(Collection<Vehicle> vehicles);
}
