package states;

import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public class DoorOpenState extends State {

	public DoorOpenState(Elevator e) {
		super(e);
		elevator.startTimer();
	}
	
	@Override
	public State handleFloorEvent(FloorEvent e) {
		return null;
	}

	@Override
	public State sendArrivalEvent() {
		return null;
	}

	@Override
	public State handleDoorTimerExpiry() {
		return new StationaryState(elevator);
	}

}
