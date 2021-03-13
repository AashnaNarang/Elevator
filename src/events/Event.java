package events;

import java.io.Serializable;
import java.time.LocalTime;

public class Event implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7795450260063476223L;
	protected LocalTime time;
	protected int destination;
	private int id;
	
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
	
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    Event a = (Event) o;
	    return (time == a.getTime()) && (destination == a.getDestination());
	}

	public int getId() {
		return id;
	}

}
