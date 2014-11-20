package smart.vehicle.analytics.computing.samples;

import java.util.Collection;

import smart.vehicle.analytics.computing.IPredicate;
import smart.vehicle.analytics.computing.Predicate;
import smart.vehicle.analytics.core.TerminalOperationExpression;
import smart.vehicle.analytics.dao.Vehicle;

public class VehiclesMovingTowardsSouth extends TerminalOperationExpression {
	
	private IPredicate<Vehicle> isVehicleMovingTowardsSouth;
	
	public VehiclesMovingTowardsSouth(Collection<Vehicle> contextualVehicleCollection) {
		super(contextualVehicleCollection);
		
		isVehicleMovingTowardsSouth = new IPredicate<Vehicle>() {
			@Override
			public boolean apply(Vehicle vehicle) {
				return vehicle.isNorthBound() == false;
			}
		};
	}

	@Override
	protected Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) {
		return Predicate.filter(contextualVehicleCollection, isVehicleMovingTowardsSouth);
	}
	
	@Override
	public String toString() {
		return "moving towards south";
	}

}
