/**
 * 
 */
package gas.showers.apiframeworkmk1;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gas.showers.datetime.DateHelper;

/**
 * @author Joey
 *
 */
public class APITest {
	
	public ArrayList<Double> listToSort;
	public API api;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		listToSort = new ArrayList<Double>();
		listToSort.add(new Double(5.3));
		listToSort.add(new Double(-1));
		listToSort.add(new Double(33));
		
		
		api = new APITestDriver(listToSort);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This test runs a threaded command that will sort the listToSort after 5 seconds, and live for a total of 65 seconds.
	 */
	@Test
	public void testSorter() {
		try {
			System.out.println(api.execute("sortarraylist"));
			LocalDateTime start = LocalDateTime.now();
			while (DateHelper.calculateSecondsBetweenLocalDateTime(start, LocalDateTime.now()) < 4) {
				assertEquals(listToSort.get(0).doubleValue(), 5.3, 0);
				assertEquals(listToSort.get(1).doubleValue(), -1, 0);
				assertEquals(listToSort.get(2).doubleValue(), 33, 0);
				Thread.sleep(50);
				System.out.println(api.getAllExecuting());
			}
			Thread.sleep(1000);
			assertEquals(listToSort.get(1).doubleValue(), 5.3, 0);
			assertEquals(listToSort.get(0).doubleValue(), -1, 0);
			assertEquals(listToSort.get(2).doubleValue(), 33, 0);
			
			System.out.println(api.getAllExecuting());
			
			api.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The same test as before, but with nt command.
	 * 
	 * Takes slightly longer but otherwise ok.
	 */
	@Test
	public void testSorter2() {
		try {
			System.out.println(api.execute("nt sortarraylist"));
			LocalDateTime start = LocalDateTime.now();
			while (DateHelper.calculateSecondsBetweenLocalDateTime(start, LocalDateTime.now()) < 4) {
				assertEquals(listToSort.get(0).doubleValue(), 5.3, 0);
				assertEquals(listToSort.get(1).doubleValue(), -1, 0);
				assertEquals(listToSort.get(2).doubleValue(), 33, 0);
				Thread.sleep(50);
				System.out.println(api.getAllExecuting());
			}
			Thread.sleep(1300);
			assertEquals(listToSort.get(1).doubleValue(), 5.3, 0);
			assertEquals(listToSort.get(0).doubleValue(), -1, 0);
			assertEquals(listToSort.get(2).doubleValue(), 33, 0);
			
			System.out.println(api.getAllExecuting());
			
			api.stop();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
