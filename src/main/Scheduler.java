package main;
import java.time.LocalTime;
import java.util.ArrayList;

import events.ArrivalEvent;
import events.FloorEvent;
import states.ActiveState;
import states.IdleState;
import states.SchedulerState;

public class Scheduler implements Runnable {
	private ArrayList<FloorEvent> floorEvents;
	private ArrayList<ArrivalEvent> arrivalEvents;
	private ArrayList<DestinationEvent> destinationEvents; 
	private MiddleMan middleManFloor;
	private MiddleMan middleManElevator;
	private SchedulerState currentState; 
	
	
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
		this.currentState = new IdleState(this); 
	}
	
	/**
	 * Used to communicate with Floor Subsystem and Elevator to receive and send events.
	 */
	@Override
	public void run() {
		while(true) {
			
			ArrivalEvent arrivalEvent; 
			DestinationEvent destinationEvent; 
			FloorEvent floorEvent; 
			
			currentState = currentState.handleFloorEvent();
			currentState = currentState.handleArrivalEvent();
			currentState = currentState.handleDestinationEvent();
			
			if(currentState.getClass() == ActiveState.class) {
				if (!floorEvents.isEmpty()) {
					floorEvent = floorEvents.remove(0);
					middleManElevator.putFloorEvent(floorEvent);
				}
				
				if (!arrivalEvents.isEmpty()) {
					arrivalEvent = arrivalEvents.remove(0);
					if(arrivalEvent.getCurrentFloor() == floorEvent.getSource()) {
						SchedulerEvent schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), true, false, 
								true, floorEvent, arrivalEvent.getDirection(), LocalTime.now());
						middleManElevator.putSchedulerEvent(schedulerEvent);
					}
					middleManFloor.putArrivalEvent(arrivalEvent);
				}
				 
				if (!destinationEvents.isEmpty()) {
					destinationEvent = destinationEvents.remove(0);
					if(destinationEvent.getDestination() == arrivalEvent.getCurrentFloor()) {
						boolean keepGoing = !destinationEvents.isEmpty();
						SchedulerEvent schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), keepGoing, true, 
								false, floorEvent, arrivalEvent.getDirection(), LocalTime.now());
						middleManElevator.putSchedulerEvent(schedulerEvent);
					}
					middleManElevator.putDestinationEvent(destinationEvent);
				}			
			}		
		}
	}

	public ArrivalEvent getArrivalEvent() {
		ArrivalEvent arrEvent = middleManElevator.getArrivalEvent();
		if (arrEvent != null) {
			arrivalEvents.add(arrEvent);
		}
		return arrEvent; 
	}
	
	public DestinationEvent getDestinationEvent() {
		DestinationEvent destinationEvent = middleManElevator.getDestinationEvent();
		if (destinationEvent != null) {
			destinationEvents.add(destinationEvent);
		}
		return destinationEvent; 
	}

	public FloorEvent getFloorEvent() {
		FloorEvent floorEvent = middleManFloor.getFloorEvent();
		if (floorEvent != null) {
			floorEvents.add(floorEvent);
		}
		return floorEvent;
	}
	
	public boolean isFloorEventsListEmpty() {
		return floorEvents.isEmpty();
	}
	
	public boolean isArrivalEventsListEmpty() {
		return arrivalEvents.isEmpty();
	}
	
	public boolean isDestinationEventsListEmpty() {
		return destinationEvents.isEmpty();
	}
	
	public void addToFloorEventsList(FloorEvent event) {
		floorEvents.add(event); 
	}
	
	public void addToArrivalEventsList(ArrivalEvent event) {
		arrivalEvents.add(event); 
	}
	
	public void addToDestinationEventsList(DestinationEvent event) {
		destinationEvents.add(event); 
	}
}
