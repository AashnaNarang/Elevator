package states;

import events.*;
import main.Elevator;

public class DoorOpenState extends ElevatorState {
	private SchedulerEvent stopEvent;
	private FloorEvent floorEvent;
	
	private DoorOpenState(Elevator e, SchedulerEvent event) {
		super(e);
		this.stopEvent = event;
	}
	
	private DoorOpenState(Elevator e, FloorEvent event) {
		super(e);
		this.floorEvent = event;
	}
	
	public static void createWithSchedulerEvent(Elevator e, SchedulerEvent event) {
		// Factory Pattern
		DoorOpenState d = new DoorOpenState(e, event);
		e.setState(d);
		e.startTimer();
	}
	
	public static void createWithFloorEvent(Elevator e, FloorEvent event) {
		// Factory Pattern
		DoorOpenState d = new DoorOpenState(e, event);
		e.setState(d);
		e.startTimer();
	}

	@Override
	public void handleDoorTimerExpiry() {
		if((floorEvent != null) && (stopEvent == null)) {
			System.out.println("Door timer expired, " + Thread.currentThread().getName() + " is already at source floor");
			MovingState.createWithFloorEvent(elevator, floorEvent, true);
		}
		else if ((floorEvent == null) && stopEvent.shouldIKeepGoing()) {
			System.out.println("Door timer expired, scheduler told " + Thread.currentThread().getName() + " to stop then keep going");
			MovingState.createWithSchedulerEvent(elevator, stopEvent);
		} else {
			System.out.println("Door timer expired, scheduler told " + Thread.currentThread().getName() + " to stop and do not continue moving");
			StationaryState s = new StationaryState(elevator);
			elevator.setState(s);
		}
	}

}
