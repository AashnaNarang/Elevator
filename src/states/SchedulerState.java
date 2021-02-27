package states;
import events.FloorEvent;
import events.Event;
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
	
	public SchedulerState handleDestinationEvent(Event event) {
		return null; 
	}
}
