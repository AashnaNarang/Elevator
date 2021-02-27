package states;
import main.Scheduler;

public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
	}
	
	public SchedulerState handleFloorEvent() {
		return null; 
	}
	
	public SchedulerState handleArrivalEvent() {
		return null; 
	}
	
	public SchedulerState handleDestinationEvent() {
		return null; 
	}
}
