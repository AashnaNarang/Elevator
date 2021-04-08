package states;
import java.time.LocalTime;
import java.util.ArrayList;

import events.ArrivalEvent;
import events.FloorEvent;
import events.SchedulerEvent;
import events.StationaryEvent;
import events.Event;
import main.Configurations;
import main.Direction;
import main.InvalidRequestException;
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
			System.out.println(Thread.currentThread().getName() + " sent floor event to elevator with id " + elevStationary.getElevatorId() + ": " +  floorEvent + ".  {Time: " + LocalTime.now() + "}");
			floorEvent.setElevatorId(elevStationary.getElevatorId());
			scheduler.addToSentFloorEventsList(floorEvent);
			int numFloors = Math.abs(floorEvent.getSource() - elevStationary.getCurrentFloor());
			if(numFloors == 0) numFloors = 1;
			scheduler.startElevatorTimer(elevStationary.getElevatorId(), true, numFloors);
		}
	}

	@Override
	/**
	 * Analyze the arrival event to see if elevator needs to stop or not. Send scheduler event to elevator 
	 * to tell it what to do and send arrival event to floor subsystem
	 * @throws InvalidRequestException 
	 */
	public void handleArrivalEvent() throws InvalidRequestException {
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
			scheduler.startElevatorTimer(arrivalEvent.getElevatorId(), false, 1);
			scheduler.sendArrivalEventToFloor(arrivalEvent);
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
		//handle invalid scheduler event
		if(arrivalEvent.getCurrentFloor() == 1 && schedulerEvent.getDirection() == Direction.DOWN && 
				schedulerEvent.shouldIKeepGoing() == true) {
				throw new InvalidRequestException("Invalid Scheduler Event sending elevator to negative floor ");
		}else if(arrivalEvent.getCurrentFloor() == Configurations.NUMBER_OF_FLOORS && schedulerEvent.getDirection() == Direction.UP
				&& schedulerEvent.shouldIKeepGoing() == true) {
				throw new InvalidRequestException("Invalid Scheduler Event sending elevator above the max floor");
		}
		
		scheduler.sendSchedulerEventToElevator(schedulerEvent, arrivalEvent.getSchedPort());
		if (elevatorKeepsGoing) scheduler.startElevatorTimer(arrivalEvent.getElevatorId(), false, 1);
		if (floorEventFlag) scheduler.sendArrivalEventToFloor(arrivalEvent);
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
				&& (fEvent.getDirection() == arrivalEvent.getDirection());
	}
	
	/**
	 * Check if elevator should stop at given floor and elevator ids match
	 * @param arrivalEvent arrival event to check what floor elevator is at
	 * @param fEvent floor event to check
	 * @return
	 */
	private boolean isAtFloorCheckWithId(ArrivalEvent arrivalEvent, FloorEvent fEvent) {
		return isAtFloor(arrivalEvent, fEvent)
				&& (fEvent.getElevatorId() == arrivalEvent.getElevatorId());
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
				scheduler.incrementNumOfProcessedEvents();
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
			if (isAtFloorCheckWithId(arrivalEvent, fEvent)) {
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
