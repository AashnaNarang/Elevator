import java.util.ArrayList;

public class Scheduler implements Runnable {
	private ArrayList<FloorEvent> floorEvents;
	private ArrayList<ArrivalEvent> arrivalEvents;
	private MiddleMan middleMan;
	private MiddleMan middleMan2;
	
	
	/**
	 * Public constructor to create Scheduler object and instantiate instance variables
	 * @param middleMan Object to hold and pass events to/from the floor
	 * @param middleMan2 Object to hold and pass events to/from the elevator
	 */
	public Scheduler(MiddleMan middleMan, MiddleMan middleMan2) {
		this.floorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new ArrayList<ArrivalEvent>();
		this.middleMan = middleMan;
		this.middleMan2 = middleMan2;
	}
	
	/**
	 * Used to communicate with Floor Subsystem and Elevator to receive and send events.
	 */
	@Override
	public void run() {
		while(true) {
			floorEvents.add(middleMan.getFloorEvent());
			middleMan2.putFloorEvent(floorEvents.remove(0));
			arrivalEvents.add(middleMan2.getArrivalEvent());
			middleMan.putArrivalEvent(arrivalEvents.remove(0));
		}
	}
	
}
