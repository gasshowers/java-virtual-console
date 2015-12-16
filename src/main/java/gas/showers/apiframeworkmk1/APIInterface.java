package gas.showers.apiframeworkmk1;

import java.util.UUID;

/**
 * Interface for which all back end access points must use.
 * This will be extended as I work out the logistics involved.
 * @author Admin
 *
 */
public interface APIInterface {
	/**
	 * Simple string to be used as command ID, must be unique, must be lowercase.
	 * @return
	 */
	public abstract String command();
	/**
	 * String description, used for help etc.
	 * @return
	 */
	public abstract String description();
	/**
	 * The time before this should be killed, in seconds. <=-1 means never kill.
	 * The actual killing should be performed by the API itself, during a call
	 * to this method.
	 * @return
	 */
	public abstract long life();
	/**
	 * Generate a new instance, so that we can call this command from console for example.
	 * Generate a UUID here probably.
	 * @param args
	 * @return
	 */
	public abstract APIInterface generate(String[] args) throws APIArgumentException;
	
	/**
	 * Return a UUID for this object. I could implement this here but I cbf making this class abstract.
	 * @return
	 */
	public abstract UUID getUUID();

	/**
	 * The main entry point for API code. This MUST be always called at least once (by either a console interface
	 * or by another eg node.js)
	 * @return
	 */
	public abstract String get();
}
