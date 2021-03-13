package events;

import java.io.Serializable;
import java.time.LocalTime;

import main.Direction;
import main.Elevator;

public class ArrivalEvent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 520508342067884869L;
	private int currentFloor;
	private LocalTime time;
	private Direction direction;
	private boolean didNotMoveYet;

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
	 */
	public ArrivalEvent(int currentFloor, LocalTime time, Direction direction) {
		this.currentFloor = currentFloor;
		this.time = time;
		this.direction = direction;
		this.didNotMoveYet = false;
	}
	
	public ArrivalEvent(int currentFloor, LocalTime time, Direction direction, boolean didNotMoveYet) {
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
}
