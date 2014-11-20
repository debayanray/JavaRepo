package smart.vehicle.analytics.computing.samples;

import java.util.Collection;

import smart.vehicle.analytics.computing.IPredicate;
import smart.vehicle.analytics.computing.Predicate;
import smart.vehicle.analytics.core.TerminalOperationExpression;
import smart.vehicle.analytics.dao.Vehicle;

public class VehiclesMovingDuringLatterHalfOfDay extends TerminalOperationExpression {
	
	private IPredicate<Vehicle> isVehicleMovingDuringLatterHalfOfDay;
	
	public VehiclesMovingDuringLatterHalfOfDay(Collection<Vehicle> contextualVehicleCollection) {
		super(contextualVehicleCollection);
		
		isVehicleMovingDuringLatterHalfOfDay = new IPredicate<Vehicle>() {
			@Override
			public boolean apply(Vehicle vehicle) {
				int hours = vehicle.getPassingTime().getHours();
				return hours > 11 && hours < 24;
			}
		};
	}

	@Override
	protected Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) {
		return Predicate.filter(contextualVehicleCollection, isVehicleMovingDuringLatterHalfOfDay);
	}
	
	@Override
	public String toString() {
		return "moving during latter half of the day";
	}

}
