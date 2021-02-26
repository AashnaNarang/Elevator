package main;
import java.util.ArrayList;

import events.ArrivalEvent;
import events.FloorEvent;

public class Scheduler implements Runnable {
	private ArrayList<FloorEvent> floorEvents;
	private ArrayList<ArrivalEvent> arrivalEvents;
	private ArrayList<DestinationEvent> destinationEvents; 
	private MiddleMan middleManFloor;
	private MiddleMan middleManElevator;
	
	
	/**
	 * Public constructor to create Scheduler object and instantiate instance variables
	 * @param middleMan Object to hold and pass events to/from the floor
	 * @param middleMan2 Object to hold and pass events to/from the elevator
	 */
	public Scheduler(MiddleMan middleManFloor, MiddleMan middleManElevator) {
		this.floorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new ArrayList<ArrivalEvent>();
		this.destinationEvents = new ArrayList<DestinationEvent>(); 
		this.middleManFloor = middleManFloor;
		this.middleManElevator = middleManElevator;
	}
	
	/**
	 * Used to communicate with Floor Subsystem and Elevator to receive and send events.
	 */
	@Override
	public void run() {
		while(true) {
			FloorEvent floorEvent = middleManFloor.getFloorEvent();
			if (floorEvent != null) {
				floorEvents.add(floorEvent);
			}
			if (!floorEvents.isEmpty()) {
				middleManElevator.putFloorEvent(floorEvents.remove(0));
			}
			
			DestinationEvent destinationEvent = middleManElevator.getDestinationEvent(); 
			if(destinationEvent != null) {
				destinationEvents.add(destinationEvent);
			}
			if (!destinationEvents.isEmpty()) {
				middleManElevator.putDestinationEvent(destinationEvents.remove(0));
			}
					
			
			ArrivalEvent arrEvent = middleManElevator.getArrivalEvent();
			if(arrEvent.getCurrentFloor() == floorEvent.getSource()) {
				floorEvent.setAtSource(true);
				middleManElevator.putFloorEvent(floorEvent);
			}
			if (arrEvent != null) {
				arrivalEvents.add(arrEvent);
			}
			if (!arrivalEvents.isEmpty()) {
				middleManFloor.putArrivalEvent(arrivalEvents.remove(0));
			}
		}
	}
	
}
