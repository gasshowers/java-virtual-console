package gas.showers.simplethreads;

/**
 * Basically a fatal error, shit dun fucked.
 * @author Joey
 *
 */
public class InnerException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4588222930514553790L;

	public InnerException(String message) {
		super(message);
	}
	
    public InnerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
