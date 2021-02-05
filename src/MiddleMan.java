import java.util.LinkedList;
import java.util.Queue;

public class MiddleMan {
	private Queue<Event> floorEvents;
	private Queue<Event> arrivalEvents;
	
	/**
	 * Public constructor to intialize MiddleMan's instance variables
	 */
	public MiddleMan() {
		this.floorEvents = new LinkedList<>();
		this.arrivalEvents = new LinkedList<>();
	}
	
	/**
	 * Get an event that signifies a user's request to use the elevator from the queue of events
	 * @return The event object
	 */
	public synchronized Event getFloorEvent() {
		if (floorEvents.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				return null;
			}
		}
		return floorEvents.poll();
	}
	
	/**
	 * Add an event that signifies a user's request to use the elevator into the queue of events
	 * @param floorEvent The event object to add
	 */
	public synchronized void putFloorEvent(Event floorEvent) {
		floorEvents.add(floorEvent);
		notifyAll();
	}
	
	
	/**
	 * Get an event that signifies an elevator has arrived a floor
	 * @return The event object
	 */
	public synchronized Event getArrivalEvent() {
		return arrivalEvents.poll();
	}
	
	/**
	 * Add an event that signifies an elevator has arrived at a floor
	 * @param arrivalEvent The event object to add
	 */
	public synchronized void putArrivalEvent(Event arrivalEvent) {
		arrivalEvents.add(arrivalEvent);
	}
	
}
