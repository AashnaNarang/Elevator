package main;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import main.Configurations;
import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import events.StationaryEvent;
import states.ElevatorState;
import states.MovingState;
import states.StationaryState;
import timers.ElevatorTimer;

/*
 * The Elevator class is designed so that it takes the task form the middleman
 * and moves the elevator up/down based on the given task it will receive.
 */

public class Elevator extends NetworkCommunicator implements Runnable {
	private static int ELEVATOR_ID=0;
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
	private boolean running;
	private boolean isDoorsOpen;
	private boolean didTimeout;
	private int errorCode;
	private List<String> statuses;
    private final Object STATUS_LOCK = new Object();


	/**
	 * Elevator constructor to intialize instance variables 
	 * @param numFloor number of floors
	 * @param floorPort port to listen to for floor events
	 * @param schedPort port to listen to for scheduler events
	 * @param arrPort
	 * @param destPort
	 * @param statPort
	 */
	public Elevator(int floorPort, int schedPort, int arrPort, int destPort, int statPort) {
		this.currentFloor = 1;
		this.upLamp = new DirectionLamp(Direction.UP);
		this.downLamp = new DirectionLamp(Direction.DOWN);
		this.direction = Direction.UP;
		this.buttons = new ArrayList<ElevatorButton>();
		this.currentState = new StationaryState(this);
		this.arrPort = arrPort;
		this.destPort = destPort;
		this.statPort = statPort;
		this.id = ELEVATOR_ID;
		running = true;
		isDoorsOpen = false;
		didTimeout = false;
		errorCode = 0;
		this.statuses = new LinkedList<String>();
		
		ELEVATOR_ID++;
		try {
			sendReceiveFloorSocket = new DatagramSocket(floorPort);
			sendReceiveScheduleSocket = new DatagramSocket(schedPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < Configurations.NUMBER_OF_FLOORS; i++) {
			buttons.add(new ElevatorButton(i));
		}
	}
	
	/**
	 * Move the elevator given an scheduler event until elevator state changes
	 * @param e Scheduler event to listen to
	 */
	public void move(SchedulerEvent e) {
		this.direction = e.getDirection();
		this.switchLamps(true);
		
		this.statuses.add(Thread.currentThread().getName() + " is on floor " + currentFloor + ", about to move " + this.direction + ".  {Time: " + LocalTime.now() + "}");
		System.out.println(Thread.currentThread().getName() + " is on floor " + currentFloor + ", about to move " + this.direction + ".  {Time: " + LocalTime.now() + "}");

		while(currentState.getClass() == MovingState.class) {
			this.statuses.add(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
			System.out.println(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
			try {
				Thread.sleep(Configurations.TIME_MOVING_BETWEEN_FLOOR);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			currentFloor += direction == Direction.UP ? 1 : -1;
			System.out.println(Thread.currentThread().getName() + " arrived at floor " + currentFloor + ".  {Time: " + LocalTime.now() + "}");
			currentState.handleArrivedAtFloor();
		}
			
	}
	
	/**
	 * Move the elevator given a FloorEvent until elevator state changes
	 * @param e FloorEvent to listen to 
	 */
	public void move(FloorEvent e) {
		int diffFloors = e.getSource() - currentFloor;
		
		if (diffFloors != 0) {
			Direction direction = diffFloors < 0 ? Direction.DOWN : Direction.UP; 
			e = new FloorEvent(e.getTime(), currentFloor, direction, e.getSource(), e.getElevatorId()); 
		}
		
		this.direction = e.getDirection();
		this.switchLamps(true);
		
		this.statuses.add(Thread.currentThread().getName() + " is on floor " + currentFloor + ", about to move " + this.direction + ".  {Time: " + LocalTime.now() + "}");
		System.out.println(Thread.currentThread().getName() + " is on floor " + currentFloor + ", about to move " + this.direction + ".  {Time: " + LocalTime.now() + "}");
		ArrivalEvent arrEvent = new ArrivalEvent(this.currentFloor, LocalTime.now(), this.direction, this.sendReceiveScheduleSocket.getLocalPort(), this.id, true);
		sendArrivalEvent(arrEvent);
		if (e.getErrorCode() == 2) {
			this.stop();
			return;
		}
		while(currentState.getClass() == MovingState.class) {
			this.statuses.add(Thread.currentThread().getName() + " is on floor " + currentFloor + ", about to move " + this.direction + ".  {Time: " + LocalTime.now() + "}");
			System.out.println(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
			try {
				Thread.sleep(Configurations.TIME_MOVING_BETWEEN_FLOOR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			currentFloor += direction == Direction.UP ? 1 : -1;
			System.out.println(Thread.currentThread().getName() + " arrived at floor " + currentFloor + ".  {Time: " + LocalTime.now() + "}");
			currentState.handleArrivedAtFloor();
		}
	}
	
	/**
	 * Move the elevator given you're already on source floor and given a FloorEvent until elevator state changes
	 * @param e FloorEvent to listen to 
	 */
	public void moveToSourceFloor(FloorEvent e) {
		FloorEvent e1;
		int diffFloors = e.getSource() - currentFloor;
		
		if (diffFloors != 0) {
			Direction direction = diffFloors < 0 ? Direction.DOWN : Direction.UP; 
			e1 = new FloorEvent(e.getTime(), currentFloor, direction, e.getSource(), e.getElevatorId());
			this.direction = e1.getDirection();
		} else {
			this.direction = e.getDirection();
		}
		
		this.switchLamps(true);
		this.statuses.add(Thread.currentThread().getName() + " is on floor " + currentFloor + ", moving towards source floor " + e.getSource() + ".  {Time: " + LocalTime.now() + "}");
		System.out.println(Thread.currentThread().getName() + " is on floor " + currentFloor + ", moving towards source floor " + e.getSource() + ".  {Time: " + LocalTime.now() + "}");
		for (int i = 0; i < Math.abs(diffFloors); i++) {
			this.statuses.add(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
			System.out.println(Thread.currentThread().getName() + " is moving one floor " + direction + ".  {Time: " + LocalTime.now() + "}");
			try {
				Thread.sleep(Configurations.TIME_MOVING_BETWEEN_FLOOR);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			currentFloor += direction == Direction.UP ? 1 : -1;
			System.out.println(Thread.currentThread().getName() + " arrived at floor " + currentFloor + ".  {Time: " + LocalTime.now() + "}");
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
		while (running) {
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
		return "The elevator is currently on floor: "+ this.currentFloor + " with error code " + this.errorCode;
	}


	/**
	 * @return the currentFloor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	/**
	 * @return the error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Start the door timer and call handler when timer expires
	 */
	public void startTimer(FloorEvent fe) {
		while (true) {
			this.statuses.add("Door opened for " + Thread.currentThread().getName() + ".  {Time: " + LocalTime.now() + "}");
			this.setDoorsOpen(true);
			Elevator tempElevator = this;
			ElevatorTimer elevatorTimer = new ElevatorTimer(this); //timer used to determine if error occurred
			TimerTask closeDoorsTask = new TimerTask() { //timer used to close doors
		        public void run() {
		            tempElevator.setDoorsOpen(false);
		        }
		    };
		    
		    Timer timer = new Timer("Timer");
		    long delay = fe == null ? Configurations.TIME_TO_LOAD_UNLOAD: 
		    	fe.getErrorCode() == 1 ? Configurations.TIME_TO_LOAD_UNLOAD * 2 : Configurations.TIME_TO_LOAD_UNLOAD;
		    timer.schedule(closeDoorsTask, delay); //start timers
		    elevatorTimer.start();
			while (this.getIsDoorsOpen()) {}; //wait till one of the timers comes back
			if (this.getDidTimeout()) { //this happens when the doors do not close in time (transient error) 
				this.errorCode = fe.getErrorCode();
				this.statuses.add(Thread.currentThread().getName() + " is in error " + this.errorCode + " state");
				System.out.println(Thread.currentThread().getName() + " is in error " + this.errorCode + " state");
				closeDoorsTask.cancel();
				timer.cancel();
				this.setDoorsOpen(true);
				this.setDidTimeout(false);
				fe.setErrorCode(0);
			} else {
				this.errorCode = 0;
				elevatorTimer.cancel();
//				System.out.println(Thread.currentThread().getName() + " is in error " + this.error + " state");
				break;
			}
		}
		
		currentState.handleDoorTimerExpiry();
	}

	/**
	 * Send destination event to scheduler
	 * @param destinationEvent event to send
	 */
	public void sendDestinationEvent(Event destinationEvent) {
		byte[] data = Serial.serialize(destinationEvent);
		send(sendReceiveFloorSocket, data, data.length, this.destPort);
	}

	public void switchOnButton(int i, boolean b) {
		this.buttons.get(i).switchOn(b);
		
	}

	/**
	 * Send arrival event to scheduler
	 * @param e event to send
	 */
	public void sendArrivalEvent(ArrivalEvent e) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.arrPort);
	}
	
	/**
	 * Send stationary event to scheduler
	 * @param e event to send
	 */
	public void sendStationaryEvent(StationaryEvent e) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.statPort);
	}

	/**
	 * Ask scheduler for a scheduler event
	 * @return scheduler event
	 */
	public SchedulerEvent askShouldIStop() {
		DatagramPacket receivePacket = receive(sendReceiveScheduleSocket, false);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), SchedulerEvent.class);
	}

	/**
	 * Get direction elevator is currently moving in
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * Receive a floor event from the scheduler
	 * @return floor event received or null if doesn't exist
	 */
	public FloorEvent getFloorEvent() {
		DatagramPacket receivePacket = receive(sendReceiveFloorSocket, false);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), FloorEvent.class);
	}
	
	/**
	 * Change state of elevator
	 * @param state
	 */
	public void setState(ElevatorState state) {
		this.currentState = state;
		System.out.println("Set state of " + Thread.currentThread().getName() +  " to " + state.getClass().getSimpleName() + ".  {Time: " + LocalTime.now() + "}");
	}

	/**
	 * Get current elevator state
	 * @return state
	 */
	public ElevatorState getState() {
		return currentState; 
	}
	
	/**
	 * Get sendReceiveFloorSocket
	 * @return socket object
	 */
	public DatagramSocket getSendReceiveFloorSocket() {
		return sendReceiveFloorSocket;
	}

	/**
	 * Get sendReceiveSchedulerSocket
	 * @return socket object
	 */
	public DatagramSocket getSendReceiveScheduleSocket() {
		return sendReceiveScheduleSocket;
	}

	/**
	 * Get the elevator's id
	 * @return id of elevator
	 */
	public int getId() {
		return id;
	}
	
	public void stop() {
		// Okay to use running boolean because this will only be called when elevator thread is running
		this.errorCode = 2;
		this.statuses.add(Thread.currentThread().getName() + " broke down. Stopping now." + ".  {Time: " + LocalTime.now() + "}");
		System.out.println(Thread.currentThread().getName() + " broke down. Stopping now." + ".  {Time: " + LocalTime.now() + "}");
		this.statuses.add(Thread.currentThread().getName() + " is in error " + this.errorCode + " state");
		System.out.println(Thread.currentThread().getName() + " is in error " + this.errorCode + " state");
		running = false;
	}
	
	/**
	 * @return the isDoorsOpen
	 */
	public synchronized boolean getIsDoorsOpen() {
		return isDoorsOpen;
	}

	/**
	 * @param isDoorsOpen the isDoorsOpen to set
	 */
	public synchronized void setDoorsOpen(boolean isDoorsOpen) {
		this.isDoorsOpen = isDoorsOpen;
	}

	/**
	 * @return the didTimeout
	 */
	public synchronized boolean getDidTimeout() {
		return didTimeout;
	}

	/**
	 * @param didTimeout the didTimeout to set
	 */
	public synchronized void setDidTimeout(boolean didTimeout) {
		this.didTimeout = didTimeout;
	}
	
	/**
	 * 
	 * @param status The status string to be added
	 */
	public void addStatus(String status) {
        synchronized (STATUS_LOCK) {
    		this.statuses.add(status);
        }
	}
	
	/**
	 * 
	 * @return The removed status string
	 */
	public LinkedList<String> getStatuses( ) {
        synchronized (STATUS_LOCK) {
        	LinkedList<String> temp = new LinkedList<String>(this.statuses);
    		this.statuses.clear();
    		return temp;
        }
	}
}
