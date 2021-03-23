package events;
import java.time.LocalTime;

import main.Direction;

/**
 * The FloorEvent Class handles the request being sent to scheduler It takes in
 * the parsed information from the input and creates an event
 *
 */
public class FloorEvent extends Event {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1674329514177840536L;
	private int source;
	private Direction direction;
	private boolean isAtSource;
	private int errorCode;

	/**
	 * Constructor for FloorEvent
	 * 
	 * @param time        a time stamp for when the passenger clicks button
	 * @param source      the current floor the passenger is making the request on
	 * @param direction   the direction the passenger wants to go, up or down
	 * @param destination the floor the passenger wants to go to
	 * @param errorCode   the Event parsed will simulate an error depending what is passed
	 */
	public FloorEvent(LocalTime time, int source, Direction direction, int destination, int errorCode) {
		super(time, destination, -1);
		this.source = source;
		this.direction = direction;
		this.errorCode = errorCode;
	}

	/**
	 * Getter method for source
	 * 
	 * @return the current floor
	 */
	public int getSource() {
		return source;
	}

	/**
	 * Getter method for direction
	 * 
	 * @return the direction up or down clicked
	 */
	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * Getter method for error
	 * 
	 * @return the errorCode 0,1,2 
	 */
	public int getErrorCode() {
		return errorCode;
	}



	/**
	 * @return the isAtSource
	 */
	public boolean isAtSource() {
		return isAtSource;
	}

	/**
	 * @param isAtSource the isAtSource to set
	 */
	public void setAtSource(boolean isAtSource) {
		this.isAtSource = isAtSource;
	}

	/**
	 * @return String representation of the event.
	 */
	public String toString() {
		return "{Time:"+ time + ", Source floor:" + source+ ", Direction:" + direction + ", Destination floor:" + destination+"}";
	}
	
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    FloorEvent a = (FloorEvent) o;
	    return super.equals(a) && (source == a.getSource()) && (time == a.getTime()) &&
	           (direction == a.getDirection()) && (destination == a.getDestination());
	}
	
}
