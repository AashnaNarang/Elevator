package states;
import java.time.LocalTime;
import java.util.ArrayList;

import events.ArrivalEvent;
import events.FloorEvent;
import events.SchedulerEvent;
import events.StationaryEvent;
import events.Event;
import main.Scheduler;

/**
 * This is the active state of the scheduler 
 * 
 */
public class ActiveState extends SchedulerState {
	private boolean floorEventFlag;
	private boolean destinationEventFlag;
	
	/**
	 * Constructors to take in parameters passed in from idleState
	 * @param scheduler the scheduler
	 */
	public ActiveState(Scheduler scheduler) {
		super(scheduler);
		floorEventFlag = false;
		destinationEventFlag= false;
	}


	@Override
	public void handleFloorEvent() {
		super.handleFloorEvent();
		StationaryEvent elevStationary = scheduler.getStationaryEventFromElevator();
		boolean isElevStationary = elevStationary == null ? false : true;
		if (!isElevStationary) {
			return;
		}
		FloorEvent floorEvent = scheduler.getNextFloorEvent();
		if(floorEvent != null) {
			scheduler.sendFloorEventToElevator(floorEvent, elevStationary.getFloorPort());
			scheduler.addToSentFloorEventsList(floorEvent);
		}
	}

	@Override
	public void handleArrivalEvent() {
		floorEventFlag = false;
		destinationEventFlag= false;
		
		FloorEvent currentFloorEvent = null;
		SchedulerEvent schedulerEvent;
		ArrivalEvent arrivalEvent = scheduler.getNextArrivalEvent();
		
		if (arrivalEvent == null) {
			arrivalEvent = scheduler.getArrivalEventFromElevator();
			if (arrivalEvent == null) {
				return;
			}
		}
		
		currentFloorEvent = analyzeFloorEvents(arrivalEvent);
		analyzeDestinationEvents(arrivalEvent);

		if (arrivalEvent.didNotMoveYet()) {
			// No need to send scheduler event if elevator hasn't started moving, elevator already has instructions
			return;
		}
		
		boolean destEventsForCurrElev = false;
		for(Event d: scheduler.getDestinationEventsList()) {
			if(d.getId() == arrivalEvent.getId()) {
				destEventsForCurrElev = true;
			}
		}

		boolean elevatorKeepsGoing = ( destEventsForCurrElev || floorEventFlag);
		
		if (!floorEventFlag && !destinationEventFlag) {
			schedulerEvent = new SchedulerEvent(arrivalEvent.getDirection(), LocalTime.now());
		} else if (destinationEventFlag && floorEventFlag) {
			schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), elevatorKeepsGoing, true,
					true, currentFloorEvent, currentFloorEvent.getDirection(), LocalTime.now());
		} else if (destinationEventFlag) {
			schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), elevatorKeepsGoing, true,
					false, null, arrivalEvent.getDirection(), LocalTime.now());
		} else {
			schedulerEvent = new SchedulerEvent(arrivalEvent.getCurrentFloor(), true, false, true,
					currentFloorEvent, currentFloorEvent.getDirection(), LocalTime.now());
		}

		scheduler.sendSchedulerEventToElevator(schedulerEvent, arrivalEvent.getSchedPort());
		scheduler.sendArrivalEventToFloor(arrivalEvent);
		checkIfUpdateToIdleState();
	}
	
	@Override
	public void handleDestinationEvent() {
		Event destinationEvent = scheduler.getDestinationEventFromElevator();
		if (destinationEvent != null) {
			System.out.println(" Elevator is adding destination event from scheduler get destination event " + destinationEvent);
			scheduler.addToDestinationEventsList(destinationEvent);
		}
	}
	
	private void checkIfUpdateToIdleState() {
		if(scheduler.isArrivalEventsListEmpty() && scheduler.isFloorEventsListEmpty() 
				&& scheduler.isDestinationEventsListEmpty()) {
			scheduler.setState(new IdleState(scheduler));
		}
	}
	
	private boolean isAtFloor(ArrivalEvent arrivalEvent, FloorEvent fEvent) {
		return (arrivalEvent.getCurrentFloor() == fEvent.getSource())
				&& fEvent.getDirection() == arrivalEvent.getDirection();
	}
	
	private void analyzeDestinationEvents(ArrivalEvent arrivalEvent) {
		ArrayList<Event> toRemove = new ArrayList<>();
		for (Event destEvent : scheduler.getDestinationEventsList()) {
			if (destEvent.getDestination() == arrivalEvent.getCurrentFloor() && destEvent.getId() == arrivalEvent.getId()) {
				destinationEventFlag = true;
				toRemove.add(destEvent);
			}
		}
		
		for(Event e: toRemove) { 
			scheduler.removeDestinationEvent(e);
		}
	}
	
	private FloorEvent analyzeFloorEvents(ArrivalEvent arrivalEvent) {
		FloorEvent currentFloorEvent = null;
		for (FloorEvent fEvent : scheduler.getSentFloorEventsList()) {
			if (isAtFloor(arrivalEvent, fEvent)) {
				currentFloorEvent = fEvent;
				floorEventFlag = true;
				scheduler.removeSentFloorEvent(fEvent);
				scheduler.removeFloorEvent(fEvent);
				break;
			}
		}

		if (currentFloorEvent == null) {
			for (FloorEvent fEvent : scheduler.getFloorEventsList()) {
				if (isAtFloor(arrivalEvent, fEvent)) {
					currentFloorEvent = fEvent;
					floorEventFlag = true;
					scheduler.removeFloorEvent(fEvent);
					break;
				}
			}
		}
		
		return currentFloorEvent;
	}
	
}
