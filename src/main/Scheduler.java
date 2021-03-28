package main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import events.StationaryEvent;
import events.TimeoutEvent;
import states.ActiveState;
import states.IdleState;
import states.SchedulerState;
import timers.SchedulerTimer;

public class Scheduler extends NetworkCommunicator implements Runnable {
	//declaration of variables
	private Queue<FloorEvent> floorEvents;
	private ArrayList<FloorEvent> sentFloorEvents;
	private Queue<ArrivalEvent> arrivalEvents;
	private Queue<Event> destinationEvents;
	private SchedulerState currentState;
	private int numOfProcessedEvents;
	
	private DatagramSocket sendReceiveFloorSocket; 
	private DatagramSocket sendReceiveArrSocket;
	private DatagramSocket sendReceiveDestSocket; 
	private DatagramSocket sendReceiveStatSocket; 
	private DatagramSocket receiveTimerSocket;
	
	private int floorPort;
	private int timerPort;
	private SchedulerTimer[] timers;

	/**
	 * Public constructor to create Scheduler object and instantiate instance
	 * variables
	 * 
	 * @param middleMan  Object to hold and pass events to/from the floor
	 * @param middleMan2 Object to hold and pass events to/from the elevator
	 */
	public Scheduler(int floorEventPort, int arrPort, int destPort, int floorPort, int statPort, int timerPort) {
		this.floorEvents = new LinkedList<FloorEvent>();
		this.sentFloorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new LinkedList<ArrivalEvent>();
		this.destinationEvents = new LinkedList<Event>();
		this.currentState = new IdleState(this);
		this.floorPort = floorPort;
		this.timerPort = timerPort;
		this.timers = new SchedulerTimer[Configurations.NUM_ELEVATORS];
		this.numOfProcessedEvents = 0;
		for(int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
			timers[i] = new SchedulerTimer(this, Integer.toString(i), true, i, timerPort);
		}
		
		try {
			sendReceiveFloorSocket = new DatagramSocket(floorEventPort);
			sendReceiveArrSocket = new DatagramSocket(arrPort);
			sendReceiveDestSocket = new DatagramSocket(destPort);
			sendReceiveStatSocket = new DatagramSocket(statPort);
			receiveTimerSocket = new DatagramSocket(timerPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Used to communicate with Floor Subsystem and Elevator to receive and send
	 * events.
	 */
	@Override
	public void run() {
		while (true) {
			getTimeoutEvent();
			currentState.handleFloorEvent();
			currentState.handleDestinationEvent();
			try {
				currentState.handleArrivalEvent();
			} catch (InvalidRequestException e) {
				System.out.println(e);
				System.exit(0);
			}
		}
	}
	
	/**
	 * Start a timer for elevator
	 */
	public void startElevatorTimer(int elevatorId, boolean beforeArrivedAtSrcFlr, int numFloors) {
		timers[elevatorId] = new SchedulerTimer(this, Integer.toString(elevatorId), beforeArrivedAtSrcFlr, elevatorId, timerPort);
		timers[elevatorId].start(numFloors);
	}
	
	/**
	 * @return the timers
	 */
	public void cancelElevatorTimer(int elevatorId) {
		timers[elevatorId].cancel();
	}
	
	public void permanentFault(TimeoutEvent t) {
		System.out.println(Thread.currentThread().getName() + " received packet with timeout event.  {Time: " + LocalTime.now() + "}");
		ArrayList<FloorEvent> floorEventRemove = new ArrayList<FloorEvent>();
		ArrayList<Event> destEventRemove = new ArrayList<Event>();

		for(FloorEvent e: sentFloorEvents) {
			if(e.getElevatorId() == t.getElevatorId()) {
				e.setErrorCode(0);
				floorEventRemove.add(e);
			}
		}
		floorEvents.addAll(floorEventRemove);
		sentFloorEvents.removeAll(floorEventRemove);
		
		if(!t.isBeforeArrivedAtSrcFloor()) {
			for(Event e: destinationEvents) {
				if(e.getElevatorId() == t.getElevatorId()) {
					destEventRemove.add(e);
				}
			}
		}
		destinationEvents.removeAll(destEventRemove);
		
		if (floorEvents.size() > 0) {
			this.setState(new ActiveState(this));
		}
		System.out.println("Operations has been called for elevator " + t.getElevatorId() + ".  {Time: " + LocalTime.now() + "}");
	}

	/**
	 * 
	 * @return An Arrival Event detailing arrival information from an elevator.
	 */
	public ArrivalEvent getArrivalEventFromElevator() {
		DatagramPacket receivePacket = receive(sendReceiveArrSocket, true);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), ArrivalEvent.class);
	}
	
	/**
	 * 
	 * @return An Arrival Event detailing arrival information from an elevator.
	 */
	public void getTimeoutEvent() {
		DatagramPacket receivePacket = receive(receiveTimerSocket, true);
		if (receivePacket == null) {
			return;
		}
		permanentFault(Serial.deSerialize(receivePacket.getData(), TimeoutEvent.class));
	}

	/**
	 * 
	 * @return A Destination Event detailing the destination information from an elevator.
	 */
	public Event getDestinationEventFromElevator() {
		DatagramPacket receivePacket = receive(sendReceiveDestSocket, true);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), Event.class);
	}
	
	/**
	 * 
	 * @return A Stationary Event detailing what elevator is stationary.
	 */
	public StationaryEvent getStationaryEventFromElevator() {
		DatagramPacket receivePacket = receive(sendReceiveStatSocket, true);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), StationaryEvent.class);
	}
	
	/**
	 * 
	 * @param e The Floor Event to be sent to the elevator.
	 * @param port The port that the Floor Event will be sent to.
	 */
	public void sendFloorEventToElevator(FloorEvent e, int port) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, port); //doesn't matter what port we use to send
	}
	
	
	/**
	 * 
	 * @param e The Scheduler Event to be sent to the elevator.
	 * @param port The port that the Scheduler Event will be sent to.
	 */
	public void sendSchedulerEventToElevator(SchedulerEvent e, int port) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, port); //doesn't matter what port we use to send
	}
	
	/**
	 * 
	 * @param e The Arrival Event to be sent to the floor subsystem.
	 */
	public void sendArrivalEventToFloor(ArrivalEvent e) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.floorPort); //doesn't matter what port we use to send
	}

	/**
	 * 
	 * @return A Floor Event detailing the request of a person trying to use the elevator.
	 */
	public FloorEvent getFloorEventFromFloor() {
		DatagramPacket receivePacket = receive(sendReceiveFloorSocket, true);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), FloorEvent.class);
	}

	/**
	 * 
	 * @param state The state the Scheduler should enter next.
	 */
	public void setState(SchedulerState state) {
		this.currentState = state;
	}
	
	public Queue<FloorEvent> getFloorEventsList() {
		return floorEvents;
	}
	
	public Queue<ArrivalEvent> getArrivalEventsList() {
		return arrivalEvents;
	}
	
	public ArrayList<FloorEvent> getSentFloorEventsList() {
		return sentFloorEvents;
	}
	
	public Queue<Event> getDestinationEventsList() {
		return destinationEvents;
	}
	
	public void removeFloorEvent(FloorEvent e) {
		System.out.println(Thread.currentThread().getName() + " is removing FloorEvent. " + e + ".  {Time: " + LocalTime.now() + "}");
		floorEvents.remove(e);
	}
	
	
	public void removeDestinationEvent(Event e) {
		destinationEvents.remove(e);
	}

	public void removeSentFloorEvent(FloorEvent e) {
		sentFloorEvents.remove(e);
	}

	public boolean isFloorEventsListEmpty() {
		return floorEvents.isEmpty();
	}

	public boolean isArrivalEventsListEmpty() {
		return arrivalEvents.isEmpty();
	}

	public boolean isDestinationEventsListEmpty() {
		return destinationEvents.isEmpty();
	}

	public void addToFloorEventsList(FloorEvent event) {
		floorEvents.add(event);
	}
	
	public void addToSentFloorEventsList(FloorEvent event) {
		sentFloorEvents.add(event);
	}
	
	public FloorEvent getNextFloorEvent() {
		if (floorEvents.isEmpty()) {
			return null;
		}
		return floorEvents.remove();
	}
	
	public ArrivalEvent getNextArrivalEvent() {
		if (arrivalEvents.isEmpty()) {
			return null;
		}
		return arrivalEvents.remove();
	}

	public void addToArrivalEventsList(ArrivalEvent event) {
		arrivalEvents.add(event);
	}

	public void addToDestinationEventsList(Event event) {
		destinationEvents.add(event);
	}

	public int getNumOfProcessedEvents() {
		return numOfProcessedEvents;
	}

	public void incrementNumOfProcessedEvents() {
		this.numOfProcessedEvents++;
	}
	
}
