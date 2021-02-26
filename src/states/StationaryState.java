package states;

import events.ArrivalEvent;
import events.FloorEvent;
import main.Elevator;

public class StationaryState extends State {

	public StationaryState(Elevator e) {
		super(e);
	}

	
	@Override
	public State handleFloorEvent() {
		FloorEvent e = elevator.getFloorEvent();
		if (e != null) {
			if (e.getSource() == elevator.getCurrentFloor()) {
				return new DoorOpenState(elevator, e);
			} else {
				return new MovingState(elevator, e, false);
			}
		}
		return this;
	}

}
