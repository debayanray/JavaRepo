package smart.vehicle.analytics.core;

import java.util.Collection;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.exceptions.NotSupportedException;

public abstract class TerminalOperationExpression implements IOperationExpression<Vehicle> {
	
	private Collection<Vehicle> contextualVehicleCollection;
	
	/*@Override
	public final Collection<Vehicle> compute() throws NotSupportedException {
		throw new NotSupportedException();
	}*/

	/*@Override
	public Collection<Vehicle> computeFrom(Collection<Vehicle> contextualVehicleCollection) throws NotSupportedException {
		return applyOperationOn(contextualVehicleCollection);
	}*/
	
	public TerminalOperationExpression(Collection<Vehicle> contextualVehicleCollection) {
		this.contextualVehicleCollection = contextualVehicleCollection;
	}
	
	@Override
	public Collection<Vehicle> compute() throws NotSupportedException {
		if(contextualVehicleCollection == null) {
			throw new NotSupportedException();
		}
		return applyOperationOn(contextualVehicleCollection);
	}

	protected abstract Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) throws NotSupportedException;

}
