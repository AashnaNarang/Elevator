import java.awt.Event;
import java.util.ArrayList;

public class Scheduler {
	private ArrayList<Event> events;
	private int floor;
	// private static final NUM_FLOORS = 3
	// might need an object to hold the event to give to elevator and wait() if null
	// private Event elevatorEvent
	
	/**
	 * Public constructor to create Scheduler object and instantiate instance variables
	 */
	public Scheduler() {
		this.events = new ArrayList<Event>();
		floor = -1;
	}
	
	/**
	 * Retrieve the event that should be executed by the elevator. If there is no event to run, then wait
	 * @return the event that should be executed by the elevator
	 */
	public synchronized Event getElevatorEvent() {
		while(events.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e);
				return null;
			}
		}
		return events.get(0);
	}
	
	/**
	 * Place an elevator or floor event in the queue
	 * @param event The event to be added to the queue
	 */
	public synchronized void putEvent(Event event) {
		events.add(event);
		schedule();
		notifyAll();
	}
	
	/**
	 * Notify the scheduler that the elevator has arrived at a certain floor while executing an event
	 * @param event The event object that describes which floor the elevator is currently at
	 * @return True if the elevator should stop at the floor, otherwise false
	 */
	public synchronized boolean putArrivedEvent(Event event) {
		floor = 1; // replace with event.floor later
		notifyAll();
		return shouldElevatorStop();
	}
	
	/**
	 * Wait if the elevator is not currently as given floor, otherwise return true
	 * @param floor Floor number to check 
	 * @return True if elevator is at the given floor
	 */
	public synchronized boolean getFloorEvent(int floor) {
		while(this.floor != floor) {
			try {
				wait();
			} catch (InterruptedException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Prioritize and schedule events to efficiently handle elevator and floor events
	 */
	private synchronized void schedule() {
		
	}
	
	/**
	 * Check if any Floor events in the queue correspond to the floor the elevator is currently at
	 * @return True if elevator is at a floor with a corresponding Floor event
	 */
	private synchronized boolean shouldElevatorStop() {
		// go through events, only look at floor events
		// check if any correspond to floor the elevator is currently at
		// for(Event e : events) {
		// 		if ((e instanceof FloorEvent) && (e.getFloor() == this.floor)) {
		//			return true;
		// 		}
		// }
		return false;
	}
	
}
