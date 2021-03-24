package events;

import java.io.Serializable;

public class StationaryEvent implements Serializable {
	private static final long serialVersionUID = -8443069861169128009L;
	private static final String message = "I am stationary";
	private int floorPort;
	private int id;
	private int currentFloor;

	/**
	 * 
	 * @param floorPort The port floor events should be sent to.
	 * @param id The id of the elevator
	 */
	
	public StationaryEvent(int floorPort, int id, int currentFloor) {
		this.floorPort = floorPort;
		this.id = id;
		this.currentFloor = currentFloor;
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
	
	/**
	 * 
	 * @return The id of the elevator who sent the event.
	 */
	public int getElevatorId() {
		return id;
	}

	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

}
