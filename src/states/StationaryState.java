package states;

import events.FloorEvent;
import main.Elevator;

public class StationaryState extends ElevatorState {

	public StationaryState(Elevator e) {
		super(e);
	}

	
	@Override
	public void handleFloorEvent() {
		FloorEvent e = elevator.getFloorEvent();
		if (e != null) {
			if (e.getSource() == elevator.getCurrentFloor()) {
				System.out.println("The elevator is on same floor as floorEvent source floor, on floor " + e.getSource());
				DoorOpenState.createWithFloorEvent(elevator, e);
			} else {
				System.out.println("The Elevator is not on same floor as floorEvent source floor, on floor " + e.getSource());
				MovingState.createWithFloorEvent(elevator, e, false);
			}
		}
	}

}
