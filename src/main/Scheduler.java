package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import states.IdleState;
import states.SchedulerState;

public class Scheduler implements Runnable {
	private Queue<FloorEvent> floorEvents;
	private ArrayList<FloorEvent> sentFloorEvents;
	private Queue<ArrivalEvent> arrivalEvents;
	private ArrayList<Event> destinationEvents;
	private MiddleMan middleManFloor;
	private MiddleMan middleManElevator;
	private SchedulerState currentState;

	/**
	 * Public constructor to create Scheduler object and instantiate instance
	 * variables
	 * 
	 * @param middleMan  Object to hold and pass events to/from the floor
	 * @param middleMan2 Object to hold and pass events to/from the elevator
	 */
	public Scheduler(MiddleMan middleManFloor, MiddleMan middleManElevator) {
		this.floorEvents = new LinkedList<FloorEvent>();
		this.sentFloorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new LinkedList<ArrivalEvent>();
		this.destinationEvents = new ArrayList<Event>();
		this.middleManFloor = middleManFloor;
		this.middleManElevator = middleManElevator;
		this.currentState = new IdleState(this);
	}

	/**
	 * Used to communicate with Floor Subsystem and Elevator to receive and send
	 * events.
	 */
	@Override
	public void run() {
		while (true) {
			currentState.handleFloorEvent();
			currentState.handleDestinationEvent();
			currentState.handleArrivalEvent();
		}
	}

	public ArrivalEvent getArrivalEventFromMiddleMan() {
		return middleManElevator.getArrivalEvent();
	}

	public Event getDestinationEventFromMiddleMan() {
		return middleManElevator.getDestinationEvent();
	}
	
	public void sendFloorEventToElevator(FloorEvent e) {
		middleManElevator.putFloorEvent(e);
	}
	
	public void sendSchedulerEventToElevator(SchedulerEvent e) {
		middleManElevator.putSchedulerEvent(e);
	}
	
	public void sendArrivalEventToFloor(ArrivalEvent e) {
		middleManFloor.putArrivalEvent(e);
	}

	public FloorEvent getFloorEventFromMiddleMan() {
		return middleManFloor.getFloorEvent();
	}

	public void setState(SchedulerState state) {
		this.currentState = state;
	}
	
	public Queue<FloorEvent> getFloorEventsList() {
		return floorEvents;
	}
	
	public Queue<ArrivalEvent> getArrivalEventsList() {
		return arrivalEvents;
	}
	
	public ArrayList<FloorEvent> getSentFloorEventsList() {
		return sentFloorEvents;
	}
	
	public ArrayList<Event> getDestinationEventsList() {
		return destinationEvents;
	}
	
	public void removeFloorEvent(FloorEvent e) {
		floorEvents.remove(e);
	}
	
	
	public void removeDestinationEvent(Event e) {
		destinationEvents.remove(e);
	}

	public void removeSentFloorEvent(FloorEvent e) {
		sentFloorEvents.remove(e);
	}
	
	public boolean removeFloorEventFromMiddleMan(FloorEvent e) {
		return middleManElevator.removeFloorEvent(e);
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
	
	public void addToSentFloorEventsList(FloorEvent event) {
		sentFloorEvents.add(event);
	}
	
	public FloorEvent getNextFloorEvent() {
		if (floorEvents.isEmpty()) {
			return null;
		}
		return floorEvents.remove();
	}
	
	public ArrivalEvent getNextArrivalEvent() {
		if (arrivalEvents.isEmpty()) {
			return null;
		}
		return arrivalEvents.remove();
	}

	public void addToArrivalEventsList(ArrivalEvent event) {
		arrivalEvents.add(event);
	}

	public void addToDestinationEventsList(Event event) {
		destinationEvents.add(event);
	}
}
