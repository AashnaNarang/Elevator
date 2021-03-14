package states;

import events.ArrivalEvent;
import events.Event;
import main.Scheduler;

/**
 * This is the Idle state of the Scheduler class
 *
 */
public class IdleState extends SchedulerState {

	public IdleState(Scheduler scheduler) {
		super(scheduler);
	}

	/**
	 * Get arrival events from elevator, add to elevator's list if able to retrieve event 
	 */
	@Override
	public void handleArrivalEvent() {
		ArrivalEvent arrivalEvent = scheduler.getArrivalEventFromElevator();
		if (arrivalEvent != null) {
			scheduler.addToArrivalEventsList(arrivalEvent);
			scheduler.setState(new ActiveState(scheduler));
		}
	}

	/**
	 * Get destination events from elevator, add to elevator's list if able to retrieve event 
	 */
	@Override
	public void handleDestinationEvent() {
		Event destinationEvent = scheduler.getDestinationEventFromElevator();
		if (destinationEvent != null) {
			scheduler.addToDestinationEventsList(destinationEvent);
			scheduler.setState(new ActiveState(scheduler));
		}
	}

}
