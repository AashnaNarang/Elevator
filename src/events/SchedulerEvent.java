package events;

import java.io.Serializable;
import java.time.LocalTime;

import main.Direction;

public class SchedulerEvent implements Serializable{
	/**
	 * declaration of instance variables
	 */
	private static final long serialVersionUID = 8065427451544535531L;
	private boolean stop;
	private int floor;
	private boolean keepGoingAfterStop;
	private Direction direction;
	private boolean isAtDestination;
	private boolean isAtSource;
	private LocalTime time;
	private FloorEvent floorEvent;
	

	/**
	 * 
	 * @param direction The direction the elevator should move in.
	 * @param time The time the event occurred at.
	 */
	public SchedulerEvent(Direction direction, LocalTime time) {
		this.stop = false;
		this.floor = -1;
		this.keepGoingAfterStop = true;
		this.isAtDestination = false;
		this.isAtSource = false;
		this.direction = direction;
		this.time = time;
		this.floorEvent = null;
	}
	
	/**
	 *
	 * @param floor The floor the elevator is on.
	 * @param keepGoing Whether the elevator should keep going.
	 * @param isAtDestination Whether the elevator is at a destination.
	 * @param isAtSource Whether the elevator at the source floor of a request.
	 * @param floorEvent The Floor Event to be given to the elevator next.
	 * @param direction The direction the elevator should move in.
	 * @param time The time the event occured at.
	 */
	public SchedulerEvent(int floor, boolean keepGoing, boolean isAtDestination, boolean isAtSource, FloorEvent floorEvent, Direction direction, LocalTime time) {
		this.stop = true;
		this.floor = floor;
		this.keepGoingAfterStop = keepGoing;
		this.direction = direction;
		this.time = time;
		this.isAtDestination = isAtDestination;
		this.isAtSource = isAtSource;
		this.floorEvent = floorEvent;
	}

	/**
	 * @return If you should stop or not
	 */
	public boolean shouldStop() {
		return stop;
	}

	/**
	 * @return the floor to stop at
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @return the keepGoing
	 */
	public boolean shouldIKeepGoing() {
		return keepGoingAfterStop;
	}

	/**
	 * @return the isAtSource
	 */
	public boolean isAtSource() {
		return isAtSource;
	}

	/**
	 * @return the direction to keep going in. Null if elevator should not continue moving
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return the time the event was created
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * @return the isAtDestination
	 */
	public boolean isAtDestination() {
		return isAtDestination;
	}

	/**
	 * @return the floorEvent
	 */
	public FloorEvent getFloorEvent() {
		return floorEvent;
	}
	
	/**
	 * @return the floorEvent
	 */
	public int getDestination() {
		if (floorEvent == null) {
			return -1;
		} else {
			return floorEvent.getDestination();
		}
	}
	
	/**
	 * @param o The object the event object will be compared to.
	 * @return A boolean representing whether or not the objects are equal.
	 */
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    SchedulerEvent a = (SchedulerEvent) o;
	    return (stop == a.shouldStop()) && (floor == a.getFloor()) && (keepGoingAfterStop == a.shouldIKeepGoing()) &&
	    		(time == a.getTime()) && (direction == a.getDirection()) && (isAtDestination == a.isAtDestination()) &&
	    		(isAtSource == a.isAtSource()) && (floorEvent.equals(a.getFloorEvent()));
	}
	
}
