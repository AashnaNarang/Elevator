package states;
import events.ArrivalEvent;
import events.FloorEvent;
import events.Event;
import main.Scheduler;

/**
 * This is the active state of the scheduler 
 * 
 */
public class ActiveState extends SchedulerState{
	private Scheduler scheduler; 
	private FloorEvent floorEvent; 
	private ArrivalEvent arrivalEvent; 
	private Event destinationEvent; 
	
	/**
	 * Constructors to take in parameters passed in from idleState
	 * @param scheduler the scheduler
	 * @param floorEvent the floorEvent
	 */
	public ActiveState(Scheduler scheduler, FloorEvent floorEvent) {
		super(scheduler); 
		this.floorEvent = floorEvent; 
		scheduler.addToFloorEventsList(floorEvent);
	}
	/**
	 * Constructors to take in parameters passed in from idleState
	 * @param scheduler the scheduler
	 * @param arrivalEvent the arrivalEvent
	 */
	public ActiveState(Scheduler scheduler, ArrivalEvent arrivalEvent) {
		super(scheduler); 
		this.arrivalEvent = arrivalEvent; 
	}
	/**
	 * Constructors to take in parameters passed in from idleState
	 * @param scheduler the scheduler
	 * @param destinationEvent the destinationEvent
	 */
	public ActiveState(Scheduler scheduler, Event destinationEvent) {
		super(scheduler); 
		this.destinationEvent = destinationEvent; 
		scheduler.addToDestinationEventsList(destinationEvent);
	}

	/**
	 * Handles floorevents in active state, adding them to a list 
	 * If there are no more events to handle, then the scheduler returns to idle state
	 * 
	 */
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
	public SchedulerState handleDestinationEvent(Event newEvent) {
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
