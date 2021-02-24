package states;

import java.time.LocalTime;

import events.*;
import main.Elevator;

public class MovingState extends State {

	public MovingState(Elevator e, Event event) {
		super(e);
		if (event.isAtSource()) {
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
		Event e2 = elevator.askShouldIStop();
		if (e2 != null) {
			if (!(e2 instanceof FloorEvent) {
				elevator.switchOnButton(e2.getDestination()-1, true);
			}
			return new DoorOpenState(elevator, e2);
		}
		return this;
	}

}
