package states;

import java.time.LocalTime;

import events.*;
import main.Elevator;

public class DoorOpenState extends ElevatorState {
	private SchedulerEvent stopEvent;
	private FloorEvent floorEvent;
	
	/**
	 * Private constructor to use to initialize state with a scheduler event
	 * @param e Elevator object
	 * @param event Scheduler event object
	 */
	private DoorOpenState(Elevator e, SchedulerEvent event) {
		super(e);
		this.stopEvent = event;
	}
	
	/**
	 * Private constructor to use to initialize state with a floor event
	 * @param e Elevator object
	 * @param event Floor event object
	 */
	private DoorOpenState(Elevator e, FloorEvent event) {
		super(e);
		this.floorEvent = event;
	}
	
	/**
	 * Create Door Open state with a scheduler event and set elevator's state 
	 * @param e Elevator object
	 * @param event scheduler event
	 */
	public static void createWithSchedulerEvent(Elevator e, SchedulerEvent event) {
		// Factory Pattern
		DoorOpenState d = new DoorOpenState(e, event);
		e.setState(d);
		e.startTimer(event.getFloorEvent());
	}
	
	/**
	 * Create Door Open state with a floor event and set elevator's state 
	 * @param e Elevator object
	 * @param event floor event
	 */
	public static void createWithFloorEvent(Elevator e, FloorEvent event) {
		// Factory Pattern
		DoorOpenState d = new DoorOpenState(e, event);
		e.setState(d);
		e.startTimer(event);
	}

	@Override
	/**
	 * Handle door timer expired and switch state depending on elevator's current state
	 */
	public void handleDoorTimerExpiry() {
		if((floorEvent != null) && (stopEvent == null)) {
			System.out.println("Door timer expired, " + Thread.currentThread().getName() + " is already at source floor" + ".  {Time: " + LocalTime.now() + "}");
			MovingState.createWithFloorEvent(elevator, floorEvent, true);
		}
		else if ((floorEvent == null) && stopEvent.shouldIKeepGoing()) {
			System.out.println("Door timer expired, scheduler told " + Thread.currentThread().getName() + " to stop then keep going" + ".  {Time: " + LocalTime.now() + "}");
			MovingState.createWithSchedulerEvent(elevator, stopEvent);
		} else {
			System.out.println("Door timer expired, scheduler told " + Thread.currentThread().getName() + " to stop and do not continue moving" + ".  {Time: " + LocalTime.now() + "}");
			StationaryState s = new StationaryState(elevator);
			elevator.setState(s);
		}
	}

}
