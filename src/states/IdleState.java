package states;
import events.ArrivalEvent;
import events.FloorEvent;
import main.DestinationEvent;
import main.MiddleMan;
import main.Scheduler;

public class IdleState extends SchedulerState{
	
	private MiddleMan middleManFloor;
	
	public IdleState(Scheduler scheduler) {
		super(scheduler);
	}

	@Override
	public SchedulerState handleFloorEvent() {
		FloorEvent floorEvent = scheduler.getFloorEvent(); 
		
		if (floorEvent != null) {
			return new ActiveState(scheduler, floorEvent);
		}
		return this; 
	}

	@Override
	public SchedulerState handleArrivalEvent() {
		ArrivalEvent arrivalEvent = scheduler.getArrivalEvent();
		
		if(arrivalEvent != null) {
			return new ActiveState(scheduler, arrivalEvent); 
		}
		return this; 
	}
	
	@Override
	public SchedulerState handleDestinationEvent() {
		DestinationEvent destinationEvent = scheduler.getDestinationEvent();
		
		if(destinationEvent != null) {
			return new ActiveState(scheduler, destinationEvent); 
		}
		return this; 
	}

}
