package main;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;
import states.ActiveState;
import states.IdleState;
import states.SchedulerState;
import util.UDPHelper;

public class Scheduler implements Runnable {
	private Queue<FloorEvent> floorEvents;
	private ArrayList<FloorEvent> sentFloorEvents;
	private Queue<ArrivalEvent> arrivalEvents;
	private ArrayList<Event> destinationEvents;
	private MiddleMan middleManFloor;
	private MiddleMan middleManElevator;
	private SchedulerState currentState;

	private UDPHelper udphelper;
	private final int RECEIVE_SOCKET = 25;
	private final int ELEVATOR_PORT = 24;
	private final int FLOOR_PORT = 23;

	/**
	 * Public constructor to create Scheduler object and instantiate instance
	 * variables
	 *
	 * @param middleMan  Object to hold and pass events to/from the floor
	 * @param middleMan2 Object to hold and pass events to/from the elevator
	 */
	public Scheduler(MiddleMan middleManFloor, MiddleMan middleManElevator) {
		this.floorEvents = new LinkedList<FloorEvent>();
		this.sentFloorEvents = new ArrayList<FloorEvent>();
		this.arrivalEvents = new LinkedList<ArrivalEvent>();
		this.destinationEvents = new ArrayList<Event>();
		this.middleManFloor = middleManFloor;
		this.middleManElevator = middleManElevator;
		this.currentState = new IdleState(this);

		udphelper = new UDPHelper(RECEIVE_SOCKET);
	}

	/**
	 * Used to communicate with Floor Subsystem and Elevator to receive and send
	 * events.
	 */
	@Override
	public void run() {
		while (true) {

			byte[] data = udphelper.receivePacket(this.udphelper.getReceiveSocket());
			//TODO: Deserialize data
			if(data != null) {
				//If the data is not null, deserialize the data here
				//Three checks -> checking for floor event, destination event, arrival event
				//TODO: For the if statement, If deserialized object is an instanceOf floor event, destination event or arrival event
				if(DeserializedObject instanceof FloorEvent) {
					middleManFloor.putFloorEvent(DeserializedObject);
				}
				else if(DeserializedObject instanceof Event) {
					middleManElevator.putDestinationEvent(DeserializedObject);
				}
				else if(DeserializedObject instanceof ArrivalEvent) {
					middleManElevator.putArrivalEvent(DeserializedObject);
				}
			}

			currentState.handleFloorEvent();
			currentState.handleDestinationEvent();
			currentState.handleArrivalEvent();
		}
	}

	public ArrivalEvent getArrivalEvent() {
		return middleManElevator.getArrivalEvent();
	}

	public Event getDestinationEvent() {
		return middleManElevator.getDestinationEvent();
	}

	public void sendFloorEventToElevator(FloorEvent e) {
		//TODO: Serialize floor event (e)
		byte[] serializedData = new byte[100]; //For now, this is the array to be sent
		udphelper.sendPacket(serializedData, ELEVATOR_PORT);
		//middleManElevator.putFloorEvent(e);
	}

	public void sendSchedulerEventToElevator(SchedulerEvent e) {
		//TODO: Serialize floor event (e)
		byte[] serializedData = new byte[100]; //For now, this is the array to be sent
		udphelper.sendPacket(serializedData, ELEVATOR_PORT);
		//middleManElevator.putSchedulerEvent(e);
	}

	public void sendArrivalEventToFloor(ArrivalEvent e) {
		//TODO: Serialize floor event (e)
		byte[] serializedData = new byte[100]; //For now, this is the array to be sent
		udphelper.sendPacket(serializedData, FLOOR_PORT);
		//middleManFloor.putArrivalEvent(e);
	}

	public FloorEvent getFloorEvent() {
		return middleManFloor.getFloorEvent();
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

	public ArrayList<Event> getDestinationEventsList() {
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
		return middleManElevator.removeFloorEvent(e);
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
