package gas.showers.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateHelper {
	private static final long YEAR = 31556900;
	private static final long MONTH = 2629740;
	private static final long DAY = 86400;
	private static final long HOUR = 3600;
	private static final long MINUTE = 60;
	
	public static long calculateSecondsBetweenLocalDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {		
		LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );
		long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS);
		tempDateTime = tempDateTime.plusYears( years );
		long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS);
		tempDateTime = tempDateTime.plusMonths( months );
		long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS);
		tempDateTime = tempDateTime.plusDays( days );
		long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours( hours );
		long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes( minutes );
		long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS);
		//do some addition
		return 0+Math.round(((double)years)*31556900d) + Math.round(((double)months)*2629740d) 
		+ Math.round(((double)days)*86400d) + Math.round(((double)hours)*3600d) 
		+ Math.round(((double)minutes)*60d) + Math.round(((double)seconds));
	}
	public static double calculateMinutesBetweenLocalDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {		
		return calculateSecondsBetweenLocalDateTime(fromDateTime, toDateTime) / ((double)MINUTE);
	}
	public static double calculateHoursBetweenLocalDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {		
		return calculateSecondsBetweenLocalDateTime(fromDateTime, toDateTime) / ((double)HOUR);
	}
	public static double calculateDaysBetweenLocalDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {		
		return calculateSecondsBetweenLocalDateTime(fromDateTime, toDateTime) / ((double)DAY);
	}
	public static double calculateMonthsBetweenLocalDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {		
		return calculateSecondsBetweenLocalDateTime(fromDateTime, toDateTime) / ((double)MONTH);
	}
	public static double calculateYearsBetweenLocalDateTime(LocalDateTime fromDateTime, LocalDateTime toDateTime) {		
		return calculateSecondsBetweenLocalDateTime(fromDateTime, toDateTime) / ((double)YEAR);
	}
	
	public static String getLongISODateTimeString(LocalDateTime in) {
		String temp = in.toString();
		String bad = "2015-01-02T00:00";
		if (temp.length() == bad.length())
			temp+= ":00";
		return temp;
	}
	public static String getLongISODateTimeString(LocalDate in) {
		String temp = in.toString();
		String bad = "2015-01-02";
		if (temp.length() == bad.length())
			temp+= "T00:00:00";
		return temp;
	}
	
	public static LocalDateTime getDateTimeISODateString(String in) {
		//2014-01-01 00:00:00
		return LocalDateTime.parse(in.replace(" ", "T"));
	}
	
	public static boolean ldInRangeInclusive(LocalDate check, LocalDate start, LocalDate end) {
		if (check.isBefore(start) || check.isAfter(end))
			return false;
		return true;
	}
	
}