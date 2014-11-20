package smart.vehicle.analytics.computing.samples;

import java.util.Collection;

import smart.vehicle.analytics.computing.IPredicate;
import smart.vehicle.analytics.computing.Predicate;
import smart.vehicle.analytics.core.TerminalOperationExpression;
import smart.vehicle.analytics.dao.Vehicle;

public class VehiclesMovingDuringFirstHalfOfDay extends TerminalOperationExpression {
	
	private IPredicate<Vehicle> isVehicleMovingDuringFirstHalfOfDay;
	
	public VehiclesMovingDuringFirstHalfOfDay(Collection<Vehicle> contextualVehicleCollection) {
		super(contextualVehicleCollection);
		
		isVehicleMovingDuringFirstHalfOfDay = new IPredicate<Vehicle>() {
			@Override
			public boolean apply(Vehicle vehicle) {
				return vehicle.getPassingTime().getHours() < 12;
			}
		};
	}

	@Override
	protected Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) {
		return Predicate.filter(contextualVehicleCollection, isVehicleMovingDuringFirstHalfOfDay);
	}
	
	@Override
	public String toString() {
		return "moving during first half of the day";
	}

}
