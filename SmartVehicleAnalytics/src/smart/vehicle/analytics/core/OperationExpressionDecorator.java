package smart.vehicle.analytics.core;

import java.util.Collection;

import smart.vehicle.analytics.dao.Vehicle;
import smart.vehicle.analytics.exceptions.NotSupportedException;

public abstract class OperationExpressionDecorator implements IOperationExpression<Vehicle> {

	private IOperationExpression<Vehicle> operationExpression;
	
	/*@Override
	public final Collection<Vehicle> compute() throws NotSupportedException {
		throw new NotSupportedException();
	}*/

	/*@Override
	public final Collection<Vehicle> computeFrom(Collection<Vehicle> contextualVehicleCollection) throws NotSupportedException {
		throw new NotSupportedException();
	}*/
	
	public OperationExpressionDecorator(IOperationExpression<Vehicle> operationExpression) {
		this.operationExpression = operationExpression;
	}
	
	@Override
	public Collection<Vehicle> compute() throws NotSupportedException {
		return applyOperationOn(operationExpression.compute());
	}

	protected abstract Collection<Vehicle> applyOperationOn(Collection<Vehicle> contextualVehicleCollection) throws NotSupportedException;
	
	@Override
	public String toString() {
		return this.what() + " + " + operationExpression.toString();
	}

	protected String what() {
		// TODO Auto-generated method stub
		return null;
	}
}
