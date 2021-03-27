package states;
import events.FloorEvent;
import main.InvalidRequestException;
import main.Scheduler;
import java.time.LocalTime;
public abstract class SchedulerState {
	
protected Scheduler scheduler;
	
	public SchedulerState(Scheduler e) {
		this.scheduler = e;
		System.out.println(Thread.currentThread().getName() + " is moving into " + this.getClass().getSimpleName() + ".  {Time: " + LocalTime.now() + " }");
	}
	
	/**
	 * Get floor events from floor, add to elevator's list if able to retrieve event 
	 */
	public void handleFloorEvent() {
		FloorEvent floorEvent = scheduler.getFloorEventFromFloor();
		if (floorEvent != null) {
			System.out.println(Thread.currentThread().getName() + " received floor event " + floorEvent + " from floor subsystem" + ".  {Time: " + LocalTime.now() + " }");
			scheduler.addToFloorEventsList(floorEvent);
			scheduler.setState(new ActiveState(scheduler));
		}
	}
	
	public void handleArrivalEvent() throws InvalidRequestException {
	}
	
	public void handleDestinationEvent() {
	}
}
