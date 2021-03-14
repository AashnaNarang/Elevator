package states;

import java.time.LocalTime;

import events.FloorEvent;
import events.StationaryEvent;
import main.Elevator;

public class StationaryState extends ElevatorState {

	public StationaryState(Elevator e) {
		super(e);
	}

	
	@Override
	/**
	 * Get floor event from scheduler. Open door if already at source floor, otherwise switch to moving state
	 */
	public void handleFloorEvent() {
		elevator.sendStationaryEvent(new StationaryEvent(elevator.getSendReceiveFloorSocket().getLocalPort()));
		FloorEvent e = elevator.getFloorEvent();
		if (e != null) {
			if (e.getSource() == elevator.getCurrentFloor()) {
				System.out.println(Thread.currentThread().getName() + " is on same floor as floorEvent source floor, floor " + e.getSource() + ".  {Time: " + LocalTime.now() + "}");
				DoorOpenState.createWithFloorEvent(elevator, e);
			} else {
				System.out.println(Thread.currentThread().getName() + " is on floor " + elevator.getCurrentFloor() + ", not on same floor as floorEvent source floor, need to go to floor " + e.getSource() + ".  {Time: " + LocalTime.now() + "}");
				MovingState.createWithFloorEvent(elevator, e, false);
			}
		}
	}

}
