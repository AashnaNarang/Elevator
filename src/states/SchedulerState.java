package states;
import main.Scheduler;

public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
		System.out.println("Moving into " + this.getClass().getSimpleName());
	}
	
	public void handleFloorEvent() {
	}
	
	public void handleArrivalEvent() {
	}
	
	public void handleDestinationEvent() {
	}
}
