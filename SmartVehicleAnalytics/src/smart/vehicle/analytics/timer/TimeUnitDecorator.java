package smart.vehicle.analytics.timer;

public class TimeUnitDecorator {
	
	private TimeUnit theTimeUnit;

	public TimeUnitDecorator(TimeUnit timeUnit) {
		this.theTimeUnit = timeUnit;
	}

	public String getDayString() {
		String day = "";
		switch (Long.valueOf(theTimeUnit.getDays()).intValue()) {
		case 0:
			day = "1st day";
			break;
		case 1:
			day = "2nd day";
			break;
		case 2:
			day = "3rd day";
			break;
		case 3:
			day = "4th day";
			break;
		case 4:
			day = "5th day";
			break;
		case 5:
			day = "6th day";
			break;
		default:
			break;
		}
		return day;
	}

	public String getTimeString() {
		StringBuffer time = new StringBuffer("");
		boolean beforeNoon = true;
		
		// Hour of the time of day
		if(theTimeUnit.getHours() < 13) {
			if(theTimeUnit.getHours() == 0) {
				time.append("12");
			}
			else {
				time.append(theTimeUnit.getHours());
			}
			beforeNoon = true;
		}
		else {
			time.append(theTimeUnit.getHours() % 12);
			beforeNoon = false;
		}
		// Hour - end
		time.append(":");
		// Minute of the time of day
		if(theTimeUnit.getMinutes() < 10) {
			time.append("0");
		}
		time.append(theTimeUnit.getMinutes());
		// Minute - end
		time.append(":");
		// Second of the time of day
		if(theTimeUnit.getSeconds() < 10) {
			time.append("0");
		}
		time.append(theTimeUnit.getSeconds());
		// Second - end
		time.append(" ");
		
		if(beforeNoon) {
			time.append("AM");
		}
		else {
			time.append("PM");
		}
		
		return time.toString();
	}

	public String toPrettyTimestampString() {
		return "[" + getDayString() + " - " + getTimeString() + "]";
	}

}
