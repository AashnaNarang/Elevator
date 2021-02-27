package states;
import events.ArrivalEvent;
import events.FloorEvent;
import main.DestinationEvent;
import main.Scheduler;

public class ActiveState extends SchedulerState{
	//will implement the state interface
	private Scheduler scheduler; 
	private FloorEvent floorEvent; 
	private ArrivalEvent arrivalEvent; 
	private DestinationEvent destinationEvent; 
	
	public ActiveState(Scheduler scheduler, FloorEvent floorEvent) {
		super(scheduler); 
		this.floorEvent = floorEvent; 
		scheduler.addToFloorEventsList(floorEvent);
	}
	
	public ActiveState(Scheduler scheduler, ArrivalEvent arrivalEvent) {
		super(scheduler); 
		this.arrivalEvent = arrivalEvent; 
	}
	
	public ActiveState(Scheduler scheduler, DestinationEvent destinationEvent) {
		super(scheduler); 
		this.destinationEvent = destinationEvent; 
		scheduler.addToDestinationEventsList(destinationEvent);
	}

	@Override
	public SchedulerState handleFloorEvent(FloorEvent newEvent) {
		if(floorEvent != null) {
			scheduler.addToFloorEventsList(floorEvent);
		}
		if(newEvent != null) {
			scheduler.addToFloorEventsList(newEvent);
		}
		else if(scheduler.isFloorEventsListEmpty() && scheduler.isArrivalEventsListEmpty()
				&& scheduler.isDestinationEventsListEmpty()) {
			return new IdleState(scheduler);
		}
		
		return this; 
	}

	@Override
	public SchedulerState handleArrivalEvent() {
		if(arrivalEvent != null) {
			scheduler.addToArrivalEventsList(arrivalEvent);
		}
		else if(scheduler.isArrivalEventsListEmpty() && scheduler.isFloorEventsListEmpty()
				&& scheduler.isDestinationEventsListEmpty()) {
			return new IdleState(scheduler);
		}
		
		return this; 
	}
	
	@Override
	public SchedulerState handleDestinationEvent(DestinationEvent newEvent) {
		if(destinationEvent != null) {
			scheduler.addToDestinationEventsList(destinationEvent);
		}
		if(newEvent != null) {
			scheduler.addToDestinationEventsList(newEvent);
		}
		else if(scheduler.isArrivalEventsListEmpty() && scheduler.isFloorEventsListEmpty() 
				&& scheduler.isDestinationEventsListEmpty()) {
			return new IdleState(scheduler);
		}
		
		return this; 
	}
	
}
