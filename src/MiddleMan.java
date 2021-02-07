import java.util.LinkedList;
import java.util.Queue;

public class MiddleMan {
	private Queue<FloorEvent> floorEvents;
	private Queue<ArrivalEvent> arrivalEvents;
	
	/**
	 * Public constructor to initialize MiddleMan's instance variables
	 */
	public MiddleMan() {
		this.floorEvents = new LinkedList<>();
		this.arrivalEvents = new LinkedList<>();
	}
	
	/**
	 * Get an event that signifies a user's request to use the elevator from the queue of events
	 * @return The event object
	 */
	public synchronized FloorEvent getFloorEvent() {
		while (floorEvents.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				return null;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is receiving FloorEvent. " +
				floorEvents.peek());
		notifyAll();
		return floorEvents.remove();
	}
	
	/**
	 * Add an event that signifies a user's request to use the elevator into the queue of events
	 * @param floorEvent The event object to add
	 */
	public synchronized void putFloorEvent(FloorEvent floorEvent) {
		while (!floorEvents.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}
		floorEvents.add(floorEvent);
		System.out.println(Thread.currentThread().getName() + " is sending FloorEvent. " + 
				floorEvent);
		notifyAll();
	}
	
	
	/**
	 * Get an event that signifies an elevator has arrived a floor
	 * @return The event object
	 */
	public synchronized ArrivalEvent getArrivalEvent() {
		while (arrivalEvents.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				return null;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is receiving ArrivalEvent. " + 
				arrivalEvents.peek());
		notifyAll();
		return arrivalEvents.remove();
	}
	
	/**
	 * Add an event that signifies an elevator has arrived at a floor
	 * @param arrivalEvent The event object to add
	 */
	public synchronized void putArrivalEvent(ArrivalEvent arrivalEvent) {
		while (!arrivalEvents.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is sending ArrivalEvent. " + 
				arrivalEvent);
		arrivalEvents.add(arrivalEvent);
		notifyAll();
		
	}
	
}
