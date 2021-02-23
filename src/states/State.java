package states;
import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public abstract class State {
	protected Elevator elevator;
	
	public State(Elevator e) {
		this.elevator = e;
	}
	
	public State handleFloorEvent(FloorEvent e) {
		return null;
	}
	
	public State handleArrivedAtFloor() {
		return null;
	}
	
	public State handleDoorTimerExpiry() {
		return null;
	}
}
