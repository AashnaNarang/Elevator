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
				System.out.println(Thread.currentThread().getName() + " is on same floor as floorEvent source floor, floor " + e.getSource());
				DoorOpenState.createWithFloorEvent(elevator, e);
			} else {
				System.out.println(Thread.currentThread().getName() + " is on floor " + elevator.getCurrentFloor() + ", not on same floor as floorEvent source floor, need to go to floor " + e.getSource());
				MovingState.createWithFloorEvent(elevator, e, false);
			}
		}
	}

}
