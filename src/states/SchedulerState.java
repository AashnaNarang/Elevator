package states;
import events.FloorEvent;
import events.Event;
import main.Scheduler;

public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
		System.out.println("Moving into " + this.getClass().getSimpleName());
	}
	
	public void handleFloorEvent(FloorEvent event) {
	}
	
	public void handleArrivalEvent() {
	}
	
	public void handleDestinationEvent(Event event) {
	}
}
