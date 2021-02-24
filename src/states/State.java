package states;
import events.ArrivalEvent;
import events.FloorEvent;
import main.Elevator;

public abstract class State {
	protected Elevator elevator;
	
	public State(Elevator e) {
		this.elevator = e;
	}
	
	public State handleFloorEvent() {
		return null;
	}
	
	public State handleArrivedAtFloor() {
		return null;
	}
	
	public State handleDoorTimerExpiry() {
		return null;
	}
}
