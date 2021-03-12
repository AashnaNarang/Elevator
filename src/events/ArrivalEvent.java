package events;

import java.time.LocalTime;

import main.Direction;
import main.Elevator;

public class ArrivalEvent {
	private int currentFloor;
	private LocalTime time;
	private Elevator elevator;
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
	public ArrivalEvent(int currentFloor, LocalTime time, Direction direction, Elevator elevator) {
		this.currentFloor = currentFloor;
		this.time = time;
		this.elevator = elevator;
		this.direction = direction;
		this.didNotMoveYet = false;
	}
	
	public ArrivalEvent(int currentFloor, LocalTime time, Direction direction, Elevator elevator, boolean didNotMoveYet) {
		this.currentFloor = currentFloor;
		this.time = time;
		this.elevator = elevator;
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
	 * getter method for Elevator
	 *
	 * @return the elevator that needs the arrivalevent info
	 */
	public Elevator getElevator() {
		return elevator;
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
