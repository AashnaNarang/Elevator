package main;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import states.ElevatorState;
import states.MovingState;
import states.StationaryState;
import util.UDPHelper;


/*
 * The Elevator class is designed so that it takes the task form the middleman
 * and moves the elevator up/down based on the given task it will receive.
 */

public class Elevator implements Runnable {
	private int currentFloor;
	private MiddleMan middleMan;
	private DirectionLamp upLamp;
	private DirectionLamp downLamp;
	private Direction direction;
	private ArrayList<ElevatorButton> buttons;
	private ElevatorState currentState;

	private UDPHelper udphelper;
	private final int RECEIVE_PORT = 24;
	private final int SCHEDULER_PORT = 25;


	/*
	 * constructor for Elevator Defining the middleclass parameters that are by to
	 * the scheduler.
	 *
	 * @param middleman - sending information to Middleman
	 */
	public Elevator(MiddleMan middleMan, int numFloor) {
		this.middleMan = middleMan;
		this.currentFloor = 1;
		this.upLamp = new DirectionLamp(Direction.UP);
		this.downLamp = new DirectionLamp(Direction.DOWN);
		this.direction = Direction.UP;
		this.buttons = new ArrayList<ElevatorButton>();
		this.currentState = new StationaryState(this);

		for (int i = 0; i < numFloor; i++) {
			buttons.add(new ElevatorButton(i));
		}

		udphelper = new UDPHelper(RECEIVE_PORT);
	}

	public void move(SchedulerEvent e) {
		this.direction = e.getDirection();
		this.switchLamps(true);
		while(currentState.getClass() == MovingState.class) {
			currentFloor += direction == Direction.UP ? 1 : -1;
			currentState.handleArrivedAtFloor();
		}
	}

	public void move(FloorEvent e) {

		int diffFloors = e.getSource() - currentFloor;

		//might have to move logic to scheduler maybe
		if (diffFloors != 0) {
			Direction direction = diffFloors < 0 ? Direction.DOWN : Direction.UP;
			e = new FloorEvent(e.getTime(), currentFloor, direction, e.getSource());
		}

		this.direction = e.getDirection();
		this.switchLamps(true);

		ArrivalEvent arrEvent = new ArrivalEvent(this.currentFloor, LocalTime.now(), this.direction, this, true);
		sendArrivalEvent(arrEvent);
		while(currentState.getClass() == MovingState.class) {
			System.out.println("The Elevator is moving one floor " + direction);
			currentFloor += direction == Direction.UP ? 1 : -1;
			currentState.handleArrivedAtFloor();
		}
	}

	/*
	 * This run method will set the information to the middleman as we try to update
	 * the middleman will the information This method will also update the current
	 * floor elevator is moving through.
	 */
	public void run() {
		while (true) {

			//Checks when the elevator receives an event from scheduler
			byte[] receivedData = udphelper.receivePacket(this.udphelper.getReceiveSocket());

			//TODO: If the received data is not null, deserialize the received data
			if(receivedData != null) {
				//Deserialize the data here, converted into a floor event
				//TODO: Puts the deserialized event as the argument for the floor event to be stored
				middleMan.putFloorEvent(DeserializedEvent);
			}

			currentState.handleFloorEvent();
		}
	}

	/**
	 * Switch the direction lamps depending on direction we are moving in
	 * @param on True if lamp should be turned on, otherwise false
	 */
	private void switchLamps(boolean on) {
		if (direction == Direction.UP) {
			upLamp.switchOn(on);
		} else {
			downLamp.switchOn(on);
		}
	}


	/**
	 * @return String representation of the elevator.
	 */
	public String toString() {
		return "The elevator is currently on floor: "+ this.currentFloor;
	}


	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	public void startTimer() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currentState.handleDoorTimerExpiry();
	}

	public void sendDestinationEvent(Event destinationEvent) {
		//TODO: Serialize destination event (e)
		byte[] serializedData = new byte[100]; //For now, this is the array to be sent
		udphelper.sendPacket(serializedData, SCHEDULER_PORT);
		//this.middleMan.putDestinationEvent(destinationEvent);
	}

	public void switchOnButton(int i, boolean b) {
		this.buttons.get(i).switchOn(b);

	}

	public void sendArrivalEvent(ArrivalEvent e) {
		//TODO: Serialize arrival event (e)
		byte[] serializedData = new byte[100]; //For now, this is the array to be sent
		udphelper.sendPacket(serializedData, SCHEDULER_PORT);
		//this.middleMan.putArrivalEvent(e);

	}

	public SchedulerEvent askShouldIStop() {
		return this.middleMan.getSchedulerEvent();
	}

	public Direction getDirection() {
		return this.direction;
	}

	public FloorEvent getFloorEvent() {
		return this.middleMan.getFloorEvent();
	}

	public void setState(ElevatorState state) {
		System.out.println("The state of the Elevator is set to " + state.getClass().getSimpleName());
		this.currentState = state;
	}

}
