package states;

import events.ArrivalEvent;
import events.FloorEvent;
import events.Event;
import main.MiddleMan;
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
	 * This listens to a ArrivalEvent that will be passed into activeState if one is
	 * received
	 */
	@Override
	public void handleArrivalEvent() {
		ArrivalEvent arrivalEvent = scheduler.getArrivalEvent();
		if (arrivalEvent != null) {
			System.out.println("Adding arrival event from scheduler get arrivalevent " + arrivalEvent);
			scheduler.addToArrivalEventsList(arrivalEvent);
			scheduler.setState(new ActiveState(scheduler));
		}
	}

	/**
	 * This listens to a DestinationEvent that will be passed into activeState if
	 * one is received
	 */
	@Override
	public void handleDestinationEvent() {
		Event destinationEvent = scheduler.getDestinationEvent();
		if (destinationEvent != null) {
			System.out.println("Adding destination event from scheduler get destination event " + destinationEvent);
			scheduler.addToDestinationEventsList(destinationEvent);
			scheduler.setState(new ActiveState(scheduler));
		}
	}

}
