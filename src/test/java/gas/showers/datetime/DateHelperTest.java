/**
 * 
 */
package gas.showers.datetime;

import org.junit.*;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Test;

/**
 * @author Joey
 *
 */
public class DateHelperTest {
	private static final int TIMES_TO_TEST = 15;
	private static final int MAX_RANDOM = 100000; //min is 1
	private static final double ACCURACY = 0.0182; //percent of the actual amount

	protected Random rand;
	
	@Before
	public void setUp() {
		rand = new Random();
	}
	
	private long nextR() {
		return rand.nextInt((MAX_RANDOM - 1) + 1) + 1;
	}

	// how many seconds in
	private static final long YEAR = 31556900;
	private static final long MONTH = 2629740;
	private static final long DAY = 86400;
	private static final long HOUR = 3600;
	private static final long MINUTE = 60;
	private static final long SECOND = 1;

	@Test
	public void test() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime d = null;
		long testCase = 0;
		long actualCase = 0;
		long inc = 0;
		for (int x = 0; x < TIMES_TO_TEST; x++) {
			//year
			inc = nextR();
			d = now.minusYears(inc);
			testCase = DateHelper.calculateSecondsBetweenLocalDateTime(d, now);
			actualCase = inc * YEAR;
			assertEquals(actualCase, testCase, ACCURACY*((double)actualCase));
			assertEquals(((double)actualCase)/MINUTE, DateHelper.calculateMinutesBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MINUTE);
			assertEquals(((double)actualCase)/HOUR, DateHelper.calculateHoursBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/HOUR);
			assertEquals(((double)actualCase)/DAY, DateHelper.calculateDaysBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/DAY);
			assertEquals(((double)actualCase)/MONTH, DateHelper.calculateMonthsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MONTH);
			assertEquals(((double)actualCase)/YEAR, DateHelper.calculateYearsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/YEAR);
			//Month
			inc = nextR();
			d = now.minusMonths(inc);
			testCase = DateHelper.calculateSecondsBetweenLocalDateTime(d, now);
			actualCase = inc * MONTH;
			assertEquals(actualCase, testCase, ACCURACY*((double)actualCase));
			assertEquals(((double)actualCase)/MINUTE, DateHelper.calculateMinutesBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MINUTE);
			assertEquals(((double)actualCase)/HOUR, DateHelper.calculateHoursBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/HOUR);
			assertEquals(((double)actualCase)/DAY, DateHelper.calculateDaysBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/DAY);
			assertEquals(((double)actualCase)/MONTH, DateHelper.calculateMonthsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MONTH);
			assertEquals(((double)actualCase)/YEAR, DateHelper.calculateYearsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/YEAR);
			//Day
			inc = nextR();
			d = now.minusDays(inc);
			testCase = DateHelper.calculateSecondsBetweenLocalDateTime(d, now);
			actualCase = inc * DAY;
			assertEquals(actualCase, testCase, ACCURACY*((double)actualCase));
			assertEquals(((double)actualCase)/MINUTE, DateHelper.calculateMinutesBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MINUTE);
			assertEquals(((double)actualCase)/HOUR, DateHelper.calculateHoursBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/HOUR);
			assertEquals(((double)actualCase)/DAY, DateHelper.calculateDaysBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/DAY);
			assertEquals(((double)actualCase)/MONTH, DateHelper.calculateMonthsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MONTH);
			assertEquals(((double)actualCase)/YEAR, DateHelper.calculateYearsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/YEAR);
			//Hour
			inc = nextR();
			d = now.minusHours(inc);
			testCase = DateHelper.calculateSecondsBetweenLocalDateTime(d, now);
			actualCase = inc * HOUR;
			assertEquals(actualCase, testCase, ACCURACY*((double)actualCase));
			assertEquals(((double)actualCase)/MINUTE, DateHelper.calculateMinutesBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MINUTE);
			assertEquals(((double)actualCase)/HOUR, DateHelper.calculateHoursBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/HOUR);
			assertEquals(((double)actualCase)/DAY, DateHelper.calculateDaysBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/DAY);
			assertEquals(((double)actualCase)/MONTH, DateHelper.calculateMonthsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MONTH);
			assertEquals(((double)actualCase)/YEAR, DateHelper.calculateYearsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/YEAR);
			//Minute
			inc = nextR();
			d = now.minusMinutes(inc);
			testCase = DateHelper.calculateSecondsBetweenLocalDateTime(d, now);
			actualCase = inc * MINUTE;
			assertEquals(actualCase, testCase, ACCURACY*((double)actualCase));
			assertEquals(((double)actualCase)/MINUTE, DateHelper.calculateMinutesBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MINUTE);
			assertEquals(((double)actualCase)/HOUR, DateHelper.calculateHoursBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/HOUR);
			assertEquals(((double)actualCase)/DAY, DateHelper.calculateDaysBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/DAY);
			assertEquals(((double)actualCase)/MONTH, DateHelper.calculateMonthsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MONTH);
			assertEquals(((double)actualCase)/YEAR, DateHelper.calculateYearsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/YEAR);
			//Seconds
			inc = nextR();
			d = now.minusSeconds(inc);
			testCase = DateHelper.calculateSecondsBetweenLocalDateTime(d, now);
			actualCase = inc * SECOND;
			assertEquals(actualCase, testCase, ACCURACY*((double)actualCase));
			assertEquals(((double)actualCase)/MINUTE, DateHelper.calculateMinutesBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MINUTE);
			assertEquals(((double)actualCase)/HOUR, DateHelper.calculateHoursBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/HOUR);
			assertEquals(((double)actualCase)/DAY, DateHelper.calculateDaysBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/DAY);
			assertEquals(((double)actualCase)/MONTH, DateHelper.calculateMonthsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/MONTH);
			assertEquals(((double)actualCase)/YEAR, DateHelper.calculateYearsBetweenLocalDateTime(d, now), ACCURACY*((double)actualCase)/YEAR);
		}
		
	}

}
