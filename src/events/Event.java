package events;

import java.io.Serializable;
import java.time.LocalTime;

public class Event implements Serializable{
	/**
	 * declaration of instance variables
	 */
	private static final long serialVersionUID = 7795450260063476223L;
	protected LocalTime time;
	protected int destination;
	protected int id;
	

	/**
	 * 
	 * @param time Represents the time the event occurred.
	 * @param destination Represents the destination of where the elevator wants to go.
	 * @param id The id of the elevator.
	 */
	public Event(LocalTime time, int destination, int id) {
		this.destination = destination;
		this.time = time;
		this.id = id;
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
	
	/**
	 * Print destination event
	 */
	public String toString() {
		return "{Time: "+ time + ", Destination:" + getDestination() + "}";
	}
	
	/**
	 * @param o The object the event object will be compared to.
	 * @return A boolean representing whether or not the objects are equal.
	 */
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    Event a = (Event) o;
	    return (time == a.getTime()) && (destination == a.getDestination());
	}

	/**
	 * 
	 * @return The id of the elevator who sent the event.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id The id of the elevator the floor event should be handled by.
	 */
	public void setId(int id) {
		this.id = id;
	}

}
