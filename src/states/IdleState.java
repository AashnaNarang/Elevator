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
		System.out.println("Moving into " + this.getClass().getSimpleName());
	}

	/**
	 * This listens to a FloorEvent that will be passed into activeState if one is
	 * received
	 */
	@Override
	public void handleFloorEvent(FloorEvent floorEvent) {
		if (floorEvent != null) {
			scheduler.setState(new ActiveState(scheduler, floorEvent));
		}
	}

	/**
	 * This listens to a ArrivalEvent that will be passed into activeState if one is
	 * received
	 */
	@Override
	public void handleArrivalEvent() {
		ArrivalEvent arrivalEvent = scheduler.getArrivalEvent();
		if (arrivalEvent != null) {
			scheduler.setState(new ActiveState(scheduler, arrivalEvent));
		}
	}

	/**
	 * This listens to a DestinationEvent that will be passed into activeState if
	 * one is received
	 */
	@Override
	public void handleDestinationEvent(Event destinationEvent) {
		if (destinationEvent != null) {
			scheduler.setState(new ActiveState(scheduler, destinationEvent));
		}
	}

}
