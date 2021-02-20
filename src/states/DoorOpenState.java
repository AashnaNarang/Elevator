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
	public void handleFloorEvent(FloorEvent e) {
		// Do nothing
	}

	@Override
	public void sendArrivalEvent() {
		// Do nothing 
	}

	@Override
	public void handleDoorTimerExpiry() {
		elevator.setState(new StationaryState(elevator));
	}

}
