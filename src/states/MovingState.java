package states;

import java.time.LocalTime;

import events.*;
import main.Elevator;

public class MovingState extends ElevatorState {

	private MovingState(Elevator e, SchedulerEvent event) {
		super(e);
		if (event.isAtSource()) {
			Event destinationEvent = new Event(LocalTime.now(), event.getDestination());
			elevator.sendDestinationEvent(destinationEvent);
			elevator.switchOnButton(event.getDestination()-1, true);
		}
	}
	
	private MovingState(Elevator e, FloorEvent event, boolean isAtSource) {
		super(e);
		if (isAtSource) {
			Event destinationEvent = new Event(LocalTime.now(), event.getDestination());
			elevator.sendDestinationEvent(destinationEvent);
			elevator.switchOnButton(event.getDestination()-1, true);
		}
	}
	
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
	
	public static void createWithSchedulerEvent(Elevator e, SchedulerEvent event) {
		// Factory Pattern
		MovingState m = new MovingState(e, event);
		e.setState(m);
		e.move(event);
	}


	@Override
	public void handleArrivedAtFloor() {
		System.out.println(Thread.currentThread().getName() + " arrived at floor " + elevator.getCurrentFloor() + " and sending arrival event" + ".  {Time: " + LocalTime.now() + "}");
		ArrivalEvent e = new ArrivalEvent(elevator.getCurrentFloor(), LocalTime.now(), elevator.getDirection());
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
