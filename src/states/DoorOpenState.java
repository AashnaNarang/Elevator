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
		System.out.println("Timer expired");
		if((floorEvent != null) && (stopEvent == null)) {
			System.out.println("There is an handleDoorTimerExpiry because elevator is already at source floor");
			MovingState.createWithFloorEvent(elevator, floorEvent, true);
		}
		else if ((floorEvent == null) && stopEvent.shouldIKeepGoing()) {
			System.out.println("There is an handleDoorTimerExpiry because scheduler told you to stop but the elevator keep going");
			MovingState.createWithSchedulerEvent(elevator, stopEvent);
		} else {
			System.out.println("There is an handleDoorTimerExpiry because scheduler told you to stop but the elevator did not keep going");
			StationaryState s = new StationaryState(elevator);
			elevator.setState(s);
		}
	}

}
