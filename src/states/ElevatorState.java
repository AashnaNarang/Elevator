package states;
import main.Elevator;

public abstract class ElevatorState {
	protected Elevator elevator;
	
	public ElevatorState(Elevator e) {
		this.elevator = e;
		System.out.println(Thread.currentThread().getName() + " is moving into " + this.getClass().getSimpleName());
	}
	
	public void handleFloorEvent() {
	}
	
	public void handleArrivedAtFloor() {
	}
	
	public void handleDoorTimerExpiry() {
	}
}
