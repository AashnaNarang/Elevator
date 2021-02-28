package main;

import java.time.LocalTime;
import java.util.ArrayList;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import states.ActiveState;
import states.IdleState;
import states.SchedulerState;

public class Scheduler implements Runnable {
	private ArrayList<FloorEvent> floorEvents;
	private ArrayList<ArrivalEvent> arrivalEvents;
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
		this.floorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new ArrayList<ArrivalEvent>();
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
		// will be used to keep track of the elevator status.
		boolean elevatorKeepsGoing = false;

		while (true) {

			SchedulerEvent schedulerEvent;
			FloorEvent floorEvent = getFloorEvent();
			ArrivalEvent arrivalEvent = getArrivalEvent();
			Event destinationEvent = getDestinationEvent();
			FloorEvent currentFloorEvent = null;
			boolean floorEventFlag = false, destinationEventFlag = false;

			currentState.handleFloorEvent(floorEvent);
			currentState.handleArrivalEvent(arrivalEvent);
			currentState.handleDestinationEvent(destinationEvent);

			if (currentState.getClass() == ActiveState.class) {
				if (!floorEvents.isEmpty() && !elevatorKeepsGoing) {
					floorEvent = floorEvents.get(0);
					middleManElevator.putFloorEvent(floorEvent);
				}

				if (!arrivalEvents.isEmpty()) {
					arrivalEvent = arrivalEvents.remove(0);

					for (FloorEvent fEvent : floorEvents) {
						if ((arrivalEvent.getCurrentFloor() == fEvent.getSource())
								&& fEvent.getDirection() == arrivalEvent.getDirection()) {
							currentFloorEvent = fEvent;
							floorEventFlag = true;
							floorEvents.remove(fEvent);
							break;
						}

					}

					for (Event destEvent : destinationEvents) {
						if (destEvent.getDestination() == arrivalEvent.getCurrentFloor()) {
							destinationEventFlag = true;
							destinationEvents.remove(destEvent);
							break;
						}
					}

					elevatorKeepsGoing = (!destinationEvents.isEmpty() || floorEventFlag);

					if (!floorEventFlag || !destinationEventFlag) {
						// No-stop
						schedulerEvent = new SchedulerEvent(arrivalEvent.getDirection(), LocalTime.now());
					} else if (destinationEventFlag && floorEventFlag) {
						schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), elevatorKeepsGoing, true,
								true, currentFloorEvent, currentFloorEvent.getDirection(), LocalTime.now());
					} else if (destinationEventFlag) {
						schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), elevatorKeepsGoing, true,
								false, currentFloorEvent, currentFloorEvent.getDirection(), LocalTime.now());
					} else {
						schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), true, false, true,
								currentFloorEvent, currentFloorEvent.getDirection(), LocalTime.now());
					}

					middleManElevator.putSchedulerEvent(schedulerEvent);
					middleManFloor.putArrivalEvent(arrivalEvent);
				}
			}
		}
	}

	public ArrivalEvent getArrivalEvent() {
		ArrivalEvent arrEvent = middleManElevator.getArrivalEvent();
		if (arrEvent != null) {
			//arrivalEvents.add(arrEvent);
		}
		return arrEvent;
	}

	public Event getDestinationEvent() {
		Event destinationEvent = middleManElevator.getDestinationEvent();
		if (destinationEvent != null) {
			destinationEvents.add(destinationEvent);
		}
		return destinationEvent;
	}

	public FloorEvent getFloorEvent() {
		FloorEvent floorEvent = middleManFloor.getFloorEvent();
		if (floorEvent != null) {
			//floorEvents.add(floorEvent);
		}
		return floorEvent;
	}

	public void setState(SchedulerState state) {
		this.currentState = state;
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

	public void addToDestinationEventsList(Event event) {
		destinationEvents.add(event);
	}
}
