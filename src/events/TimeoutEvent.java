package events;

import java.io.Serializable;

public class TimeoutEvent implements Serializable {

	private static final long serialVersionUID = 5481647207280849440L;
	private int elevatorId;
	private boolean beforeArrivedAtSrcFloor;
	
	/**
	 * Public constructor to initialize instance variables
	 * @param elevatorId
	 * @param beforeArrivedAtSrcFloor
	 */
	public TimeoutEvent(int elevatorId, boolean beforeArrivedAtSrcFloor) {
		this.elevatorId = elevatorId;
		this.beforeArrivedAtSrcFloor = beforeArrivedAtSrcFloor;
	}

	/**
	 * @return the elevatorId
	 */
	public int getElevatorId() {
		return elevatorId;
	}

	/**
	 * @return the beforeArrivedAtSrcFloor
	 */
	public boolean isBeforeArrivedAtSrcFloor() {
		return beforeArrivedAtSrcFloor;
	}
	
	public String toString() {
		return "Elevator with ID " + elevatorId + " timed out. BeforeArrivedAtSrcFloor = " + beforeArrivedAtSrcFloor;
	}
	
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    TimeoutEvent a = (TimeoutEvent) o;
	    return (elevatorId == a.getElevatorId()) && (beforeArrivedAtSrcFloor == a.isBeforeArrivedAtSrcFloor());
	}
	
}
