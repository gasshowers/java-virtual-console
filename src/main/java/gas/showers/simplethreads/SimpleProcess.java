package gas.showers.simplethreads;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Use to allow thread management, boilerplate code here.
 * @author Joey
 */
public abstract class SimpleProcess implements Runnable {
	protected Logger logger; //Logger.getLogger(API.class.getName())
	private static final int SLEEP_TIME = 50;
	
	private boolean running;
	private Thread t;
	private String procName;
	private Exception except;
	
	public SimpleProcess(String _procName) {
		this(_procName, null);
	}
	
	public SimpleProcess(String _procName, Logger _logger) {
		procName = _procName;
		running = false;
		except = null;
		logger = _logger;
	}
	
	public void sleep() {
		sleep(SLEEP_TIME);
	}
	public void sleep(long timeMS) {
		try {
			Thread.sleep(timeMS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			abRun();
		} catch (Exception e) {
			//save the exception for management higher up
			except = e;
			if (logger != null)
				logger.log(Level.SEVERE, "Threaded exception suppressed.", e);
			else
				e.printStackTrace();
		}
		running = false;
	}
	
	public void start() {
		running = true;
		if (t == null)
		{
			t = new Thread (this, procName);
			t.start();
		}
	} 
	
	public boolean getRunning() {
		return running;
	}
	public String getName() {
		return procName;
	}
	public Exception getException() {
		return except;
	}
	public abstract void abRun() throws Exception;
}