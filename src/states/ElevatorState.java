package states;
import events.ArrivalEvent;
import events.FloorEvent;
import main.Elevator;

public abstract class ElevatorState {
	protected Elevator elevator;
	
	public ElevatorState(Elevator e) {
		this.elevator = e;
	}
	
	public ElevatorState handleFloorEvent() {
		return null;
	}
	
	public ElevatorState handleArrivedAtFloor() {
		return null;
	}
	
	public ElevatorState handleDoorTimerExpiry() {
		return null;
	}
}
