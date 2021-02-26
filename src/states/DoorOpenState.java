package states;

import events.*;
import main.Elevator;

public class DoorOpenState extends State {
	private Event event;
	public DoorOpenState(Elevator e, Event event) {
		super(e);
		this.event = event;
		elevator.startTimer();
	}

	@Override
	public State handleDoorTimerExpiry() {
		if (event instanceof FloorEvent) {
			return new MovingState(elevator, event);
		} else {
			return new StationaryState(elevator);
		}
	}

}
