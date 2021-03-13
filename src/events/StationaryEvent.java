package events;

import java.io.Serializable;

public class StationaryEvent implements Serializable {

	/**
	 * declaration of instance variables
	 */
	private static final long serialVersionUID = -8443069861169128009L;

	private static final String message = "I am stationary";
	
	private int floorPort;
	
	/**
	 * 
	 * @param floorPort The port floor events should be sent to.
	 */
	
	public StationaryEvent(int floorPort) {
		this.floorPort = floorPort;
	}

	/**
	 * 
	 * @return The message of the event. 
	 */
	public static String getMessage() {
		return message;
	}

	/**
	 * 
	 * @return The port floor events should be sent to.
	 */
	public int getFloorPort() {
		return floorPort;
	}
	
}
