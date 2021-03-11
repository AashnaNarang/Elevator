package events;
import java.time.LocalTime;

import main.Direction;

/**
 * The FloorEvent Class handles the request being sent to scheduler It takes in
 * the parsed information from the input and creates an event
 *
 */
public class FloorEvent extends Event {
	private int source;
	private Direction direction;
	private boolean isAtSource;

	/**
	 * Constructor for FloorEvent
	 * 
	 * @param time        a time stamp for when the passenger clicks button
	 * @param source      the current floor the passenger is making the request on
	 * @param direction   the direction the passenger wants to go, up or down
	 * @param destination the floor the passenger wants to go to
	 */
	public FloorEvent(LocalTime time, int source, Direction direction, int destination) {
		super(time, destination);
		this.source = source;
		this.direction = direction;
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
	
}
