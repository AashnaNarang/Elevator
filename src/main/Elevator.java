package main;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import events.StationaryEvent;
import states.ElevatorState;
import states.MovingState;
import states.StationaryState;


/*
 * The Elevator class is designed so that it takes the task form the middleman
 * and moves the elevator up/down based on the given task it will receive.
 */

public class Elevator extends NetworkCommunicator implements Runnable {
	private int currentFloor;
	private DirectionLamp upLamp;
	private DirectionLamp downLamp;
	private Direction direction;
	private ArrayList<ElevatorButton> buttons;
	private ElevatorState currentState;
	private int arrPort;
	private int destPort;
	private int statPort;
	private DatagramSocket sendReceiveFloorSocket; //declaration of socket
	private DatagramSocket sendReceiveScheduleSocket; //declaration of socket
	private int id;

	/*
	 * constructor for Elevator Defining the middleclass parameters that are by to
	 * the scheduler.
	 *
	 */
	public Elevator(int id, int numFloor, int floorPort, int schedPort, int arrPort, int destPort, int statPort) {
		this.currentFloor = 1;
		this.upLamp = new DirectionLamp(Direction.UP);
		this.downLamp = new DirectionLamp(Direction.DOWN);
		this.direction = Direction.UP;
		this.buttons = new ArrayList<ElevatorButton>();
		this.currentState = new StationaryState(this);
		this.arrPort = arrPort;
		this.destPort = destPort;
		this.statPort = statPort;
		this.id = id;
		try {
			sendReceiveFloorSocket = new DatagramSocket(floorPort);
			sendReceiveScheduleSocket = new DatagramSocket(schedPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < numFloor; i++) {
			buttons.add(new ElevatorButton(i));
		}
	}
	
	public void move(SchedulerEvent e) {
		this.direction = e.getDirection();
		this.switchLamps(true);
		
		System.out.println(Thread.currentThread().getName() + " is on floor " + currentFloor + ", about to move " + this.direction + ".  {Time: " + LocalTime.now() + "}");

		while(currentState.getClass() == MovingState.class) {
			System.out.println(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
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
		
		System.out.println(Thread.currentThread().getName() + " is on floor " + currentFloor + ", about to move " + this.direction + ".  {Time: " + LocalTime.now() + "}");
		ArrivalEvent arrEvent = new ArrivalEvent(this.currentFloor, LocalTime.now(), this.direction, this.sendReceiveScheduleSocket.getLocalPort(), this.id, true);
		sendArrivalEvent(arrEvent);
		while(currentState.getClass() == MovingState.class) {
			System.out.println(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
			currentFloor += direction == Direction.UP ? 1 : -1;
			currentState.handleArrivedAtFloor();
		}
	}
	
	public void moveToSourceFloor(FloorEvent e) {
		FloorEvent e1;
		int diffFloors = e.getSource() - currentFloor;
		
		//might have to move logic to scheduler maybe
		if (diffFloors != 0) {
			Direction direction = diffFloors < 0 ? Direction.DOWN : Direction.UP; 
			e1 = new FloorEvent(e.getTime(), currentFloor, direction, e.getSource());
			this.direction = e1.getDirection();
		} else {
			this.direction = e.getDirection();
		}
		
		this.switchLamps(true);
		System.out.println(Thread.currentThread().getName() + " is on floor " + currentFloor + ", moving towards source floor " + e.getSource() + ".  {Time: " + LocalTime.now() + "}");
		for (int i = 0; i < Math.abs(diffFloors); i++) {
			System.out.println(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
			currentFloor += direction == Direction.UP ? 1 : -1;
		}
		direction = e.getDirection();
		currentState.handleArrivedAtFloor();
	}

	/*
	 * This run method will set the information to the middleman as we try to update
	 * the middle man will the information This method will also update the current
	 * floor elevator is moving through.
	 */
	public void run() {
		while (true) {
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
		byte[] data = Serial.serialize(destinationEvent);
		send(sendReceiveFloorSocket, data, data.length, this.destPort);
	}

	public void switchOnButton(int i, boolean b) {
		this.buttons.get(i).switchOn(b);
		
	}

	public void sendArrivalEvent(ArrivalEvent e) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.arrPort);
	}
	
	public void sendStationaryEvent(StationaryEvent e) {
		System.out.println("Elevator State: " + currentState + " sending stationary event");
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.statPort);
	}

	public SchedulerEvent askShouldIStop() {
		DatagramPacket receivePacket = receive(sendReceiveScheduleSocket, false);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), SchedulerEvent.class);
	}

	public Direction getDirection() {
		return this.direction;
	}

	public FloorEvent getFloorEvent() {
		DatagramPacket receivePacket = receive(sendReceiveFloorSocket, false);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), FloorEvent.class);
	}
	
	public void setState(ElevatorState state) {
		this.currentState = state;
		System.out.println("Set state of " + Thread.currentThread().getName() +  " to " + state.getClass().getSimpleName() + ".  {Time: " + LocalTime.now() + "}");
	}

	public ElevatorState getState() {
		return currentState; 
	}
	
	public DatagramSocket getSendReceiveFloorSocket() {
		return sendReceiveFloorSocket;
	}

	public DatagramSocket getSendReceiveScheduleSocket() {
		return sendReceiveScheduleSocket;
	}

	public int getId() {
		return id;
	}
}
