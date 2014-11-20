package smart.vehicle.analytics.computing.samples;

import java.util.Collection;

import smart.vehicle.analytics.computing.IPredicate;
import smart.vehicle.analytics.computing.Predicate;
import smart.vehicle.analytics.core.TerminalOperationExpression;
import smart.vehicle.analytics.dao.Vehicle;

public class VehiclesMovingTowardsNorth extends TerminalOperationExpression {
	
	private IPredicate<Vehicle> isVehicleMovingTowardsNorth;
	
	public VehiclesMovingTowardsNorth(Collection<Vehicle> contextualVehicleCollection) {
		super(contextualVehicleCollection);
		
		isVehicleMovingTowardsNorth = new IPredicate<Vehicle>() {
			@Override
			public boolean apply(Vehicle vehicle) {
				return vehicle.isNorthBound();
			}
		};
	}

	@Override
	protected Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) {
		return Predicate.filter(contextualVehicleCollection, isVehicleMovingTowardsNorth);
	}

	@Override
	public String toString() {
		return "moving towards north";
	}
	
	

}
