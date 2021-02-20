package states;

import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public class StationaryState extends State {

	public StationaryState(Elevator e) {
		super(e);
	}
	
	@Override
	public void handleFloorEvent(FloorEvent e) {
		if (e.getDestination() == elevator.getCurrentFloor()) {
			elevator.setState(new DoorOpenState(elevator));
		} else {
			elevator.setState(new MovingState(elevator));
		}
	}

	@Override
	public void sendArrivalEvent() {
		ArrivalEvent e = new ArrivalEvent(elevator.getCurrentFloor(), null, null, elevator);
		elevator.sendArrivalEvent(e);
	}

	@Override
	public void handleDoorTimerExpiry() {
		// Not applicable
	}

}
