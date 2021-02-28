package states;
import events.FloorEvent;
import events.ArrivalEvent;
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
	
	public void handleArrivalEvent(ArrivalEvent event) {
	}
	
	public void handleDestinationEvent(Event event) {
	}
}
