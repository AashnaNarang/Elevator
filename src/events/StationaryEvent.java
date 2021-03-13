package events;

import java.io.Serializable;

public class StationaryEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8443069861169128009L;

	private static final String message = "I am stationary";
	
	private int floorPort;
	
	public StationaryEvent(int floorPort) {
		this.floorPort = floorPort;
	}

	public static String getMessage() {
		return message;
	}

	public int getFloorPort() {
		return floorPort;
	}
	
}
