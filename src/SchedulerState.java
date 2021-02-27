import events.ArrivalEvent;
import events.FloorEvent;
import main.Scheduler;

public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
	}
	
	public abstract void handleFloorEvent(FloorEvent event);
	
	public abstract void handleArrivedFloor(ArrivalEvent event);
}
