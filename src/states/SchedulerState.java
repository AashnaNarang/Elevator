package states;
import events.FloorEvent;
import main.DestinationEvent;
import main.Scheduler;

public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
	}
	
	public SchedulerState handleFloorEvent(FloorEvent event) {
		return null; 
	}
	
	public SchedulerState handleArrivalEvent() {
		return null; 
	}
	
	public SchedulerState handleDestinationEvent(DestinationEvent event) {
		return null; 
	}
}
