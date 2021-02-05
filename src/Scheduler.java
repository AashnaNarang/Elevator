import java.util.ArrayList;

public class Scheduler implements Runnable{
	private ArrayList<FloorEvent> floorEvents;
	private ArrayList<ArrivalEvent> arrivalEvents;
	private int floor;
	private MiddleMan box;
	private MiddleMan box2;
	// private static final int NUM_FLOORS = 3;
	// might need an object to hold the event to give to elevator and wait() if null
	// private Event elevatorEvent
	
	/**
	 * Public constructor to create Scheduler object and instantiate instance variables
	 */
	public Scheduler(MiddleMan box, MiddleMan box2) {
		this.floorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new ArrayList<ArrivalEvent>();
		this.box = box;
		this.box2 = box2;
		floor = -1;
	}
	
	/**
	 * Used to communicate with Floor Subsystem and Elevator to receive and send events.
	 */
	@Override
	public void run() {
		while(true) {
			floorEvents.add(box.getFloorEvent());
			box2.putFloorEvent(floorEvents.remove(0));
			arrivalEvents.add(box2.getArrivalEvent());
			box.putArrivalEvent(arrivalEvents.remove(0));
		}
	}
	
}
