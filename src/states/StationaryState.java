package states;

import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public class StationaryState extends State {

	public StationaryState(Elevator e) {
		super(e);
	}
	
	@Override
	public State handleFloorEvent(FloorEvent e) {
		if (e.getDestination() == elevator.getCurrentFloor()) {
			return new DoorOpenState(elevator);
		} else {
			return new MovingState(elevator);
		}
	}

	@Override
	public State sendArrivalEvent() {
		ArrivalEvent e = new ArrivalEvent(elevator.getCurrentFloor(), null, null, elevator);
		elevator.sendArrivalEvent(e);
		return this;
	}

}
