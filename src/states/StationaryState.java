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
		FloorEvent e = elevator.getFloorEvent();
		if (e != null) {
			if (e.getSource() == elevator.getCurrentFloor()) {
				return new DoorOpenState(elevator, e);
			} else {
				// lamp ?
				return new MovingState(elevator);
			}
		}
		return this;
	}

	@Override
	public State handleArrivedAtFloor() {
		ArrivalEvent e = new ArrivalEvent(elevator.getCurrentFloor(), null, null, elevator);
		elevator.sendArrivalEvent(e);
		return this;
	}

}
