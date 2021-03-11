package states;
import events.FloorEvent;
import main.Scheduler;

public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
		System.out.println("The Scheduler is moving into " + this.getClass().getSimpleName());
	}
	
	public void handleFloorEvent() {
		FloorEvent floorEvent = scheduler.getFloorEvent();
		if (floorEvent != null) {
			System.out.println("Adding floor event from scheduler get floorevent " + floorEvent);
			scheduler.addToFloorEventsList(floorEvent);
			scheduler.setState(new ActiveState(scheduler));
		}
	}
	
	public void handleArrivalEvent() {
	}
	
	public void handleDestinationEvent() {
	}
}
