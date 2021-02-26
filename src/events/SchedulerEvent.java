package events;

import java.time.LocalTime;

import main.Direction;

public class SchedulerEvent {
	private boolean stop;
	private int floor;
	private boolean keepGoingAfterStop;
	private Direction direction;
	private boolean isAtDestination;
	private boolean isAtSource;
	private LocalTime time;
	private FloorEvent floorEvent;
	

	/**
	 * DONT STOP
	 * @param direction
	 * @param time
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
	 * YES STOP
	 * @param floor
	 * @param keepGoing
	 * @param isAtDestination
	 * @param isAtSource
	 * @param floorEvent
	 * @param direction
	 * @param time
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
	
	
	
	
}
