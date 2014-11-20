package smart.vehicle.analytics.timer;

public class TimeUnit {
	
	private int whichDay;
	private int hours;
	private int minutes;
	private int seconds;
	private int milliseconds;
	private TimeUnitDecorator timeUnitDecorator;
	
	public TimeUnit() {
		timeUnitDecorator = new TimeUnitDecorator(this);
	}

	public int getDay() {
		return whichDay;
	}
	public void setDay(int day) {
		this.whichDay = day;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public int getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(int milliseconds) {
		this.milliseconds = milliseconds;
	}
	
	@Override
	public String toString() {
		return timeUnitDecorator.toPrettyTimestampString();
	}
	
	
}
