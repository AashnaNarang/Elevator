package states;

import events.FloorEvent;
import main.Elevator;

public class StationaryState extends ElevatorState {

	public StationaryState(Elevator e) {
		super(e);
	}

	
	@Override
	public ElevatorState handleFloorEvent() {
		FloorEvent e = elevator.getFloorEvent();
		if (e != null) {
			if (e.getSource() == elevator.getCurrentFloor()) {
				System.out.println("On same floor as floorEvent src floor, on floor " + e.getSource());
				return new DoorOpenState(elevator, e);
			} else {
				System.out.println("Not on same floor as floorEvent src floor, on floor " + e.getSource());
				return new MovingState(elevator, e, false);
			}
		}
		return this;
	}

}
