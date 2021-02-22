package states;

import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public class MovingState extends State {

	public MovingState(Elevator e) {
		super(e);
	}
	
	@Override
	public State handleFloorEvent(FloorEvent e) {
		if (e.getDestination() == elevator.getCurrentFloor()) {
			elevator.handleFloorEvent(e);
			// either elevator.handleFloorEvent(e) or stationaryState.handleFloorEvent(e)
			return new StationaryState(elevator);
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
