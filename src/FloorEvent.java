import java.time.LocalTime;

/**
 * The FloorEvent Class handles the request being sent to scheduler It takes in
 * the parsed information from the input and creates an event
 *
 */
public class FloorEvent {

	private int source;
	private Direction direction;
	private LocalTime time;
	private int destination;

	/**
	 * Constructor for FloorEvent
	 * 
	 * @param time        a time stamp for when the passenger clicks button
	 * @param source      the current floor the passenger is making the request on
	 * @param direction   the direction the passenger wants to go, up or down
	 * @param destination the floor the passenger wants to go to
	 */
	public FloorEvent(LocalTime time, int source, Direction direction, int destination) {
		this.time = time;
		this.source = source;
		this.direction = direction;
		this.destination = destination;

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
	 * getter method for time
	 * 
	 * @return the time the passenger clicks the button
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * getter method for destination
	 * 
	 * @return the floor the passenger wants to go to
	 */
	public int getDestination() {
		return destination;
	}

}
