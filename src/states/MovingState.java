package states;

import java.time.LocalTime;

import events.*;
import main.Elevator;

public class MovingState extends ElevatorState {
	
	/***
	 * Private constructor to create moving state object with scheduler event
	 * @param e Elevator object
	 * @param event scheduler event
	 */
	private MovingState(Elevator e, SchedulerEvent event) {
		super(e);
		if (event.isAtSource()) {
			Event destinationEvent = new Event(LocalTime.now(), event.getDestination(), e.getId());
			elevator.sendDestinationEvent(destinationEvent);
			elevator.switchOnButton(event.getDestination()-1, true);
		}
	}
	
	/**
	 * Private constructor to create moving state object with floor event
	 * @param e elevator object
	 * @param event floor event
	 * @param isAtSource if elevator is at a source floor
	 */
	private MovingState(Elevator e, FloorEvent event, boolean isAtSource) {
		super(e);
		if (isAtSource) {
			Event destinationEvent = new Event(LocalTime.now(), event.getDestination(), e.getId());
			elevator.sendDestinationEvent(destinationEvent);
			elevator.switchOnButton(event.getDestination()-1, true);
		}
	}
	
	/**
	 * Create Moving State object with floor event and set elevators state, initiate elevator to move
	 * @param e Elevator object
	 * @param event floor event
	 * @param isAtSource if elevator is already at source floor
	 */
	public static void createWithFloorEvent(Elevator e, FloorEvent event, boolean isAtSource) {
		// Factory Pattern
		MovingState m = new MovingState(e, event, isAtSource);
		e.setState(m);
		if(isAtSource) {
			e.move(event);
		} else { 
			e.moveToSourceFloor(event);
		}
	}
	
	/**
	 * Create Moving State object with scheduler event and set elevators state, initiate elevator to move
	 * @param e Elevator object
	 * @param event floor event
	 * @param isAtSource if elevator is already at source floor
	 */
	public static void createWithSchedulerEvent(Elevator e, SchedulerEvent event) {
		// Factory Pattern
		MovingState m = new MovingState(e, event);
		e.setState(m);
		e.move(event);
	}


	@Override
	/**
	 * Send arrival event when arrived at a floor, wait to receive scheduler event and follow instructions given by scheduler
	 */
	public void handleArrivedAtFloor() {
		this.elevator.addStatus(Thread.currentThread().getName() + " arrived at floor " + elevator.getCurrentFloor() + " and sending arrival event" + ".  {Time: " + LocalTime.now() + "}");
		System.out.println(Thread.currentThread().getName() + " arrived at floor " + elevator.getCurrentFloor() + " and sending arrival event" + ".  {Time: " + LocalTime.now() + "}");
		ArrivalEvent e = new ArrivalEvent(elevator.getCurrentFloor(), LocalTime.now(), elevator.getDirection(),
				elevator.getSendReceiveScheduleSocket().getLocalPort(), elevator.getId());
		elevator.sendArrivalEvent(e);
		SchedulerEvent e2 = elevator.askShouldIStop();
		System.out.println(Thread.currentThread().getName() + " received scheduler event" + ".  {Time: " + LocalTime.now() + "}");
		if (e2.shouldStop()) {
			System.out.println("Scheduler event said stop the elevator" + ".  {Time: " + LocalTime.now() + "}");
			if (e2.isAtDestination()) {
				elevator.switchOnButton(e2.getFloor()-1, false);
			}
			DoorOpenState.createWithSchedulerEvent(elevator, e2);
		}
	}

}
