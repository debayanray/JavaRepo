package smart.vehicle.analytics.timer;

import smart.vehicle.analytics.exceptions.InvalidInputTypeException;

public class EpochParser {
	
	private static final int SECOND = 1000;
	private static final int MINUTE = 60 * SECOND;
	private static final int HOUR = 60 * MINUTE;
	private static final int DAY = 24 * HOUR;
	
	public TimeUnit convert(String millisecondsInString) throws InvalidInputTypeException {
		long milliseconds = 0;
		try {
			milliseconds = Long.valueOf(millisecondsInString);
		}
		catch (NumberFormatException e) {
			throw new InvalidInputTypeException();
		}
		
		TimeUnit timeUnit = new TimeUnit();
		
		if(milliseconds > DAY) {
			timeUnit.setDays(milliseconds / DAY);
			milliseconds %= DAY;
		}
		if(milliseconds > HOUR) {
			timeUnit.setHours(milliseconds / HOUR);
			milliseconds %= HOUR;
		}
		if(milliseconds > MINUTE) {
			timeUnit.setMinutes(milliseconds / MINUTE);
			milliseconds %= MINUTE;
		}
		if(milliseconds > SECOND) {
			timeUnit.setSeconds(milliseconds / SECOND);
			milliseconds %= SECOND;
		}
		timeUnit.setMillis(milliseconds);
		
		return timeUnit;
	}
	
	

}
