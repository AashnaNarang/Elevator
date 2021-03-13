package main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import states.IdleState;
import states.SchedulerState;

public class Scheduler extends NetworkCommunicator implements Runnable {
	private Queue<FloorEvent> floorEvents;
	private ArrayList<FloorEvent> sentFloorEvents;
	private Queue<ArrivalEvent> arrivalEvents;
	private Queue<Event> destinationEvents;
	private SchedulerState currentState;
	
	private DatagramSocket sendReceiveFloorSocket; 
	private DatagramSocket sendReceiveArrSocket; //declaration of socket
	private DatagramSocket sendReceiveDestSocket; 
	
	private int floorPort;
	private int elevatorPort;

	/**
	 * Public constructor to create Scheduler object and instantiate instance
	 * variables
	 * 
	 * @param middleMan  Object to hold and pass events to/from the floor
	 * @param middleMan2 Object to hold and pass events to/from the elevator
	 */
	public Scheduler(int floorEventPort, int arrPort, int destPort, int floorPort, int elevatorPort) {
		this.floorEvents = new LinkedList<FloorEvent>();
		this.sentFloorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new LinkedList<ArrivalEvent>();
		this.destinationEvents = new LinkedList<Event>();
		this.currentState = new IdleState(this);
		this.floorPort = floorPort;
		this.elevatorPort = elevatorPort;
		try {
			sendReceiveFloorSocket = new DatagramSocket(floorEventPort);
			sendReceiveArrSocket = new DatagramSocket(arrPort);
			sendReceiveDestSocket = new DatagramSocket(destPort);

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
			currentState.handleFloorEvent();
			currentState.handleDestinationEvent();
			currentState.handleArrivalEvent();
		}
	}

	public ArrivalEvent getArrivalEventFromMiddleMan() {
		DatagramPacket receivePacket = receive(sendReceiveArrSocket);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), ArrivalEvent.class);
	}

	public Event getDestinationEventFromMiddleMan() {
		DatagramPacket receivePacket = receive(sendReceiveDestSocket);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), Event.class);
	}
	
	public void sendFloorEventToElevator(FloorEvent e) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.elevatorPort); //doesn't matter what port we use to send
	}
	
	public void sendSchedulerEventToElevator(SchedulerEvent e) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.elevatorPort); //doesn't matter what port we use to send
	}
	
	public void sendArrivalEventToFloor(ArrivalEvent e) {
		byte[] data = Serial.serialize(e);
		send(sendReceiveFloorSocket, data, data.length, this.floorPort); //doesn't matter what port we use to send
	}

	public FloorEvent getFloorEventFromMiddleMan() {
		DatagramPacket receivePacket = receive(sendReceiveFloorSocket);
		if (receivePacket == null) {
			return null;
		}
		return Serial.deSerialize(receivePacket.getData(), FloorEvent.class);
	}

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
		floorEvents.remove(e);
	}
	
	
	public void removeDestinationEvent(Event e) {
		destinationEvents.remove(e);
	}

	public void removeSentFloorEvent(FloorEvent e) {
		sentFloorEvents.remove(e);
	}
	
	public boolean removeFloorEventFromMiddleMan(FloorEvent e) {
		System.out.println(Thread.currentThread().getName() + " is removing FloorEvent. " + e);
		return floorEvents.remove(e);
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
}
