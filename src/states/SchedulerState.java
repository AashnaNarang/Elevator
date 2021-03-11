package states;
import events.FloorEvent;
import main.Scheduler;

public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
		System.out.println(Thread.currentThread().getName() + " is moving into " + this.getClass().getSimpleName());
	}
	
	/**
	 * Poll for floor events from middle man floor, add to elevator's list if able to retrieve event 
	 */
	public void handleFloorEvent() {
		FloorEvent floorEvent = scheduler.getFloorEventFromMiddleMan();
		if (floorEvent != null) {
			scheduler.addToFloorEventsList(floorEvent);
			scheduler.setState(new ActiveState(scheduler));
		}
	}
	
	public void handleArrivalEvent() {
	}
	
	public void handleDestinationEvent() {
	}
}
