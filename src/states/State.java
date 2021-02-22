package states;
import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public abstract class State {
	protected Elevator elevator;
	
	public State(Elevator e) {
		this.elevator = e;
	}
	
	public State start() {
		return new StationaryState(elevator);
	}
	
	public abstract State handleFloorEvent(FloorEvent e);
	public abstract State sendArrivalEvent();
	public abstract State handleDoorTimerExpiry();
}
