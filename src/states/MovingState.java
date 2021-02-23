package states;

import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public class MovingState extends State {

	public MovingState(Elevator e) {
		super(e);
		// elevator.move()
	}
	
	@Override
	public State handleFloorEvent(FloorEvent e) {
		if (e.getDestination() == elevator.getCurrentFloor()) {
			// this part might need to fix. Need to set elevators state to stationary then make elevator call handle floor event again
			// elevator.handleFloorEvent(e);
			// either elevator.handleFloorEvent(e) or stationaryState.handleFloorEvent(e)
			return new StationaryState(elevator);
		} else {
			return new MovingState(elevator);
		}
	}

	@Override
	public State handleArrivedAtFloor() {
		ArrivalEvent e = new ArrivalEvent(elevator.getCurrentFloor(), null, null, elevator);
		elevator.sendArrivalEvent(e);
//		FloorEvent e = elevator.getShouldStop(e);
//		if (e != null) {
//			return Door Open State(e) --> startTimer --> 
		// handleTimerExpiry -->  then return new StationaryState(elevator,e)
		// stationary state can check if null --> if not send destination event
//		}
		// if e == null then dont need to stop
		return this;
	}

}
