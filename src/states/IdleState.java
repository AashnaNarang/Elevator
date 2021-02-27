package states;

import events.ArrivalEvent;
import events.FloorEvent;
import main.DestinationEvent;
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
	 * This listens to a FloorEvent that will be passed into activeState if one is
	 * received
	 */
	@Override
	public SchedulerState handleFloorEvent(FloorEvent floorEvent) {
		if (floorEvent != null) {
			return new ActiveState(scheduler, floorEvent);
		}
		return this;
	}

	/**
	 * This listens to a ArrivalEvent that will be passed into activeState if one is
	 * received
	 */
	@Override
	public SchedulerState handleArrivalEvent() {
		ArrivalEvent arrivalEvent = scheduler.getArrivalEvent();

		if (arrivalEvent != null) {
			return new ActiveState(scheduler, arrivalEvent);
		}
		return this;
	}

	/**
	 * This listens to a DestinationEvent that will be passed into activeState if
	 * one is received
	 */
	@Override
	public SchedulerState handleDestinationEvent(DestinationEvent destinationEvent) {
		if (destinationEvent != null) {
			return new ActiveState(scheduler, destinationEvent);
		}
		return this;
	}

}
