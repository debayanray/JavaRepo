package smart.vehicle.analytics.core;

import java.util.Collection;

import smart.vehicle.analytics.exceptions.NotSupportedException;

public interface IOperationExpression<T> {
	
	/*Collection<T> compute() throws NotSupportedException;
	Collection<T> compute(Collection<T> contextualElementCollection) throws NotSupportedException;*/
	
	Collection<T> compute() throws NotSupportedException;

}
