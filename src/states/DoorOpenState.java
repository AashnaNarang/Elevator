package states;

import events.*;
import main.Elevator;

public class DoorOpenState extends ElevatorState {
	private SchedulerEvent stopEvent;
	private FloorEvent floorEvent;
	
	public DoorOpenState(Elevator e, SchedulerEvent event) {
		super(e);
		this.stopEvent = event;
		elevator.startTimer();
	}
	
	public DoorOpenState(Elevator e, FloorEvent event) {
		super(e);
		this.floorEvent = event;
		elevator.startTimer();
	}

	@Override
	public ElevatorState handleDoorTimerExpiry() {
		if((floorEvent != null) && (stopEvent == null)) {
			System.out.println("In handleDoorTimerExpiry because elevator is already at source floor");
			return new MovingState(elevator, floorEvent, true);
		}
		else if ((floorEvent == null) && stopEvent.shouldIKeepGoing()) {
			System.out.println("In handleDoorTimerExpiry because scheduler told you to stop but keep going");
			return new MovingState(elevator, stopEvent);
		} else {
			System.out.println("In handleDoorTimerExpiry because scheduler told you to stop but DONT keep going");
			return new StationaryState(elevator);
		}
	}

}
