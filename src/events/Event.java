package events;

import java.time.LocalTime;

public class Event {
	protected LocalTime time;
	protected int destination;
	
	public Event(LocalTime time, int destination) {
		this.destination = destination;
		this.time = time;
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
