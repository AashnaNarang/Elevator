package main;

/**
 * An exception to be thrown when the Scheduler parses an invalid request
 */
public class InvalidRequestException extends Exception {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -7613357772430747539L;

	/**
     * Create a new InvalidRequestException
     *
     * @param msg the message for the exception to contain
     */
    public InvalidRequestException(String msg) {
        super(msg);
    }

}
