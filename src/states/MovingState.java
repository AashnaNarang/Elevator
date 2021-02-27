package states;

import java.time.LocalTime;

import events.*;
import main.Elevator;

public class MovingState extends State {

	public MovingState(Elevator e, SchedulerEvent event) {
		super(e);
		if (event.isAtSource()) {
			Event destinationEvent = new Event(LocalTime.now(), event.getDestination());
			elevator.sendDestinationEvent(destinationEvent);
			elevator.switchOnButton(event.getDestination()-1, true);
		}
		 elevator.move(event);
	}
	
	public MovingState(Elevator e, FloorEvent event, boolean isAtSource) {
		super(e);
		if (isAtSource) {
			Event destinationEvent = new Event(LocalTime.now(), event.getDestination());
			elevator.sendDestinationEvent(destinationEvent);
			elevator.switchOnButton(event.getDestination()-1, true);
		}
		elevator.move(event);
	}

	@Override
	public State handleArrivedAtFloor() {
		ArrivalEvent e = new ArrivalEvent(elevator.getCurrentFloor(), LocalTime.now(), elevator.getDirection(), elevator);
		elevator.sendArrivalEvent(e);
		SchedulerEvent e2 = elevator.askShouldIStop();
		if (e2.shouldStop()) {
			if (e2.isAtDestination()) {
				elevator.switchOnButton(e2.getFloor()-1, false);
			}
			return new DoorOpenState(elevator, e2);
		}
		return this;
	}

}
