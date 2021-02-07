import java.time.LocalTime;

public class ArrivalEvent {
	int currentFloor;
	LocalTime time;
	Elevator elevator;
	Direction direction;

	/*
	 * Constructor for Arrival event.
	 *
	 * @param currentFloor - The current floor the elevator is on.
	 * @param time - The time stamp of the elevator event
	 * @param direction - Direction the elevator is going in
	 * @param elevator - The elevator itself
	 */
	public ArrivalEvent(int currentFloor, LocalTime time, Direction direction, Elevator elevator) {
		this.currentFloor = currentFloor;
		this.time = time;
		this.elevator = elevator;
		this.direction = direction;
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
	 * @return String representation of the event.
	 */
	public String toString() {
		return getCurrentFloor() + " " + getDirection();
	}
}
