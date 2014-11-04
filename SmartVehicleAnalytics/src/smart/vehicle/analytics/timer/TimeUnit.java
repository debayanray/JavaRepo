package smart.vehicle.analytics.timer;

public class TimeUnit {
	
	private long days;
	private long hours;
	private long minutes;
	private long seconds;
	private long millis;
	private TimeUnitDecorator timeUnitDecorator;
	
	public TimeUnit() {
		timeUnitDecorator = new TimeUnitDecorator(this);
	}

	public long getDays() {
		return days;
	}
	public void setDays(long days) {
		this.days = days;
	}
	public long getHours() {
		return hours;
	}
	public void setHours(long hours) {
		this.hours = hours;
	}
	public long getMinutes() {
		return minutes;
	}
	public void setMinutes(long minutes) {
		this.minutes = minutes;
	}
	public long getSeconds() {
		return seconds;
	}
	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}
	public long getMillis() {
		return millis;
	}
	public void setMillis(long millis) {
		this.millis = millis;
	}
	
	public String toPrettyString() {
		return timeUnitDecorator.toPrettyTimestampString();
	}
	
	@Override
	public String toString() {
		return null;
	}
	
	
}
