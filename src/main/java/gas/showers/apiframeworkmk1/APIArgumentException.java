package gas.showers.apiframeworkmk1;
public class APIArgumentException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1055572993461572069L;

	public APIArgumentException(String message) {
		super(message);
	}
	
    public APIArgumentException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public static APIArgumentException newCCME() {
    	return new APIArgumentException("Syntax was correct, try command again (CCME)");
    }
}
