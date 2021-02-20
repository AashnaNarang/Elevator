package states;
import elevator.Elevator;
import events.ArrivalEvent;
import events.FloorEvent;

public abstract class State {
	protected Elevator elevator;
	
	public State(Elevator e) {
		this.elevator = e;
	}
	
	public abstract void handleFloorEvent(FloorEvent e);
	public abstract void sendArrivalEvent();
	public abstract void handleDoorTimerExpiry();
}
