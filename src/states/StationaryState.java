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
		elevator.sendStationaryEvent(new StationaryEvent(elevator.getSendReceiveFloorSocket().getLocalPort(), elevator.getId(), elevator.getCurrentFloor()));
		FloorEvent e = elevator.getFloorEvent();
		if (e != null) {
			System.out.println(Thread.currentThread().getName() + " received floor event " + e + ".  {Time: " + LocalTime.now() + "}");

			if (e.getSource() == elevator.getCurrentFloor()) {
				System.out.println(Thread.currentThread().getName() + " is on same floor as floorEvent source floor, floor " + e.getSource() + ".  {Time: " + LocalTime.now() + "}");
				DoorOpenState.createWithFloorEvent(elevator, e);
			} else {
				if(e.getErrorCode() == 2) {
					elevator.stop();
					return;
				}
				System.out.println(Thread.currentThread().getName() + " is on floor " + elevator.getCurrentFloor() + ", not on same floor as floorEvent source floor, need to go to floor " + e.getSource() + ".  {Time: " + LocalTime.now() + "}");
				MovingState.createWithFloorEvent(elevator, e, false);
			}
		}
	}

}
