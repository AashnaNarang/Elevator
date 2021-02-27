package states;
import main.Elevator;

public abstract class State {
	protected Elevator elevator;
	
	public State(Elevator e) {
		this.elevator = e;
		System.out.println("Moving into "+this.getClass().getSimpleName());
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
