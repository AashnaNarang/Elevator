package states;

import events.*;
import main.Elevator;

public class DoorOpenState extends State {
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
	public State handleDoorTimerExpiry() {
		if((floorEvent != null) && (stopEvent == null)) {
			return new MovingState(elevator, floorEvent, true);
		}
		else if ((floorEvent == null) && stopEvent.shouldIKeepGoing()) {
			return new MovingState(elevator, stopEvent);
		} else {
			return new StationaryState(elevator);
		}
	}

}
