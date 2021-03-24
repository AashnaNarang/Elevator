package events;

import java.io.Serializable;
import java.time.LocalTime;

import main.Direction;

public class ArrivalEvent implements Serializable{
	/**
	 * declaration of instance variables
	 */
	private static final long serialVersionUID = 520508342067884869L;
	private int currentFloor;
	private LocalTime time;
	private Direction direction;
	private boolean didNotMoveYet;
	private int schedPort;
	private int id;

	/*
	 * Constructor for Arrival event.
	 *
	 * @param currentFloor - The current floor the elevator is on.
	 * 
	 * @param time - The time stamp of the elevator event
	 * 
	 * @param direction - Direction the elevator is going in
	 * 
	 * @param elevator - The elevator itself
	 * 
	 * @param schedPort - The port where Scheduler events should be sent to.
	 * 
	 * @param id = The id representing the elevator.
	 */
	public ArrivalEvent(int currentFloor, LocalTime time, Direction direction, int schedPort, int id) {
		this.currentFloor = currentFloor;
		this.time = time;
		this.direction = direction;
		this.didNotMoveYet = false;
		this.schedPort = schedPort;
		this.id = id;
	}
	
	
	/*
	 * Constructor for Arrival event.
	 *
	 * @param currentFloor - The current floor the elevator is on.
	 * 
	 * @param time - The time stamp of the elevator event
	 * 
	 * @param direction - Direction the elevator is going in
	 * 
	 * @param elevator - The elevator itself
	 * 
	 * @param schedPort - The port where Scheduler events should be sent to.
	 * 
	 * @param id - The id representing the elevator.
	 * 
	 * @param didNotMoveYet - Has the elevator moved yet.
	 */
	public ArrivalEvent(int currentFloor, LocalTime time, Direction direction, int schedPort, int id, boolean didNotMoveYet) {
		this.currentFloor = currentFloor;
		this.time = time;
		this.direction = direction;
		this.didNotMoveYet = didNotMoveYet;
	}

	/*
	 * getter method for time
	 *
	 * @return the current time.
	 */
	public LocalTime getTime() {
		return time;
	}
	

	/*
	 * getter method for currentFloor
	 *
	 * @return the currentfloor the elevator is at.
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * @return the direction the elevator is going in
	 */
	public Direction getDirection() {
		return direction;
	}
	
	

	/**
	 * @return didNotMoveYet - if elevator has started moving yet
	 */
	public boolean didNotMoveYet() {
		return didNotMoveYet;
	}

	/**
	 * @return String representation of the event.
	 */
	public String toString() {
		return "{Time: "+ time + ", Current floor:" + getCurrentFloor() + ", Direction:" + getDirection() + "}";
	}
	
	/**
	 * @return Boolean representing whether the objects are equal
	 */
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (o == null) return false;
	    if (getClass() != o.getClass()) return false;
	    ArrivalEvent a = (ArrivalEvent) o;
	    return (currentFloor == a.getCurrentFloor()) && (time == a.getTime()) &&
	           (direction == a.getDirection()) && (didNotMoveYet == a.didNotMoveYet());
	}

	/**
	 * 
	 * @return Integer representing the port where Scheduler Events will be sent to.
	 */
	public int getSchedPort() {
		return schedPort;
	}

	/**
	 * 
	 * @return Integer representing the id of the Elevator sending the event.
	 */
	public int getElevatorId() {
		return id;
	}

}
