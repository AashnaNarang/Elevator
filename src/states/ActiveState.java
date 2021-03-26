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
	 * @param scheduler the scheduler object
	 */
	public ActiveState(Scheduler scheduler) {
		super(scheduler);
		floorEventFlag = false;
		destinationEventFlag= false;
	}


	@Override
	/**
	 * If there is a stationary elevator, then send a floor event. Remove from list and move to sent floor events
	 * list
	 */
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
			floorEvent.setElevatorId(elevStationary.getElevatorId());
			scheduler.addToSentFloorEventsList(floorEvent);
			int numFloors = Math.abs(floorEvent.getSource() - elevStationary.getCurrentFloor());
			scheduler.startElevatorTimer(elevStationary.getElevatorId(), true, numFloors);
		}
	}

	@Override
	/**
	 * Analyze the arrival event to see if elevator needs to stop or not. Send scheduler event to elevator 
	 * to tell it what to do and send arrival event to floor subsystem
	 */
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
		scheduler.cancelElevatorTimer(arrivalEvent.getElevatorId());
		currentFloorEvent = analyzeFloorEvents(arrivalEvent);
		analyzeDestinationEvents(arrivalEvent);

		if (arrivalEvent.didNotMoveYet()) {
			// No need to send scheduler event if elevator hasn't started moving, elevator already has instructions
			return;
		}
		
		boolean destEventsForCurrElev = false;
		for(Event d: scheduler.getDestinationEventsList()) {
			if(d.getElevatorId() == arrivalEvent.getElevatorId()) {
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
		if (elevatorKeepsGoing) scheduler.startElevatorTimer(arrivalEvent.getElevatorId(), false, 1);
		scheduler.sendArrivalEventToFloor(arrivalEvent);
		checkIfUpdateToIdleState();
	}
	
	@Override
	/**
	 * add destination event to scheduler's list
	 */
	public void handleDestinationEvent() {
		Event destinationEvent = scheduler.getDestinationEventFromElevator();
		if (destinationEvent != null) {
			System.out.println(Thread.currentThread().getName() + " is adding destination event from scheduler. " + destinationEvent);
			scheduler.addToDestinationEventsList(destinationEvent);
		}
	}
	
	/**
	 * Check if need to move scheduler to idle state
	 */
	private void checkIfUpdateToIdleState() {
		if(scheduler.isArrivalEventsListEmpty() && scheduler.isFloorEventsListEmpty() 
				&& scheduler.isDestinationEventsListEmpty()) {
			scheduler.setState(new IdleState(scheduler));
		}
	}
	
	/**
	 * Check if elevator should stop at given floor
	 * @param arrivalEvent arrival event to check what floor elevator is at
	 * @param fEvent floor event to check
	 * @return
	 */
	private boolean isAtFloor(ArrivalEvent arrivalEvent, FloorEvent fEvent) {
		return (arrivalEvent.getCurrentFloor() == fEvent.getSource())
				&& fEvent.getDirection() == arrivalEvent.getDirection()
				&& fEvent.getElevatorId() == arrivalEvent.getElevatorId();
	}
	
	/**
	 * Analyze destination events to see if elevator can service it
	 * @param arrivalEvent arrival event to get floor elevator is on and elevator id
	 */
	private void analyzeDestinationEvents(ArrivalEvent arrivalEvent) {
		ArrayList<Event> toRemove = new ArrayList<>();
		for (Event destEvent : scheduler.getDestinationEventsList()) {
			if (destEvent.getDestination() == arrivalEvent.getCurrentFloor() && destEvent.getElevatorId() == arrivalEvent.getElevatorId()) {
				destinationEventFlag = true;
				toRemove.add(destEvent);
			}
		}
		
		for(Event e: toRemove) { 
			scheduler.removeDestinationEvent(e);
		}
	}
	
	/**
	 * Analyze floor events to see if elevator can service it
	 * @param arrivalEvent arrival event to analyze
	 * @return floor event that can be serviced, otherwise null
	 */
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
