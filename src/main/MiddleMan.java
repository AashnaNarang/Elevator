package main;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import events.SchedulerEvent;

public class MiddleMan {
	private Queue<FloorEvent> floorEvents;
	private Queue<ArrivalEvent> arrivalEvents;
	private Queue<Event> destinationEvents;
	private SchedulerEvent schedEvent;
	
	/**
	 * Public constructor to initialize MiddleMan's instance variables
	 */
	public MiddleMan() {
		this.floorEvents = new LinkedList<>();
		this.arrivalEvents = new LinkedList<>();
		this.destinationEvents = new LinkedList<>();
		this.schedEvent = null;
	}
	
	/**
	 * Get an event that signifies a user's request to use the elevator from the queue of events
	 * @return The event object
	 */
	public synchronized FloorEvent getFloorEvent() {
		try {
			FloorEvent tempEvent = floorEvents.remove();
			System.out.println(Thread.currentThread().getName() + " is receiving FloorEvent. " +
					tempEvent);
			notifyAll();
			return tempEvent;
		} catch (NoSuchElementException e) {
			return null;
		}
		
	}
	
	/**
	 * Remove event from the queue of events if it was already serviced
	 * @return The event object
	 */
	public synchronized boolean removeFloorEvent(FloorEvent e) {
		System.out.println(Thread.currentThread().getName() + " is removing FloorEvent. " + e);
		return floorEvents.remove(e);
	}
	
	/**
	 * Add an event that signifies a user's request to use the elevator into the queue of events
	 * @param floorEvent The event object to add
	 */
	public synchronized void putFloorEvent(FloorEvent floorEvent) {
		floorEvents.add(floorEvent);
		System.out.println(Thread.currentThread().getName() + " is sending FloorEvent. " + 
				floorEvent);
		notifyAll();
	}
	
	
	/**
	 * Get an event that signifies an elevator has arrived a floor
	 * @return The event object
	 */
	public synchronized ArrivalEvent getArrivalEvent() {
		try {
			ArrivalEvent tempEvent = arrivalEvents.remove();
			System.out.println(Thread.currentThread().getName() + " is receiving ArrivalEvent. " + 
					tempEvent);
			notifyAll();
			return tempEvent;
		} catch (NoSuchElementException e) {
			return null;
		}
		
	}
	
	/**
	 * Add an event that signifies an elevator has arrived at a floor
	 * @param arrivalEvent The event object to add
	 */
	public synchronized void putArrivalEvent(ArrivalEvent arrivalEvent) {
		while (!arrivalEvents.isEmpty()) {
			try {
				System.out.println("wait() put arrival event");
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is sending ArrivalEvent. " + 
				arrivalEvent);
		arrivalEvents.add(arrivalEvent);
		notifyAll();
		
	}
	
	
	/**
	 * Gets an event that represents a destination 
	 * @return The event object
	 */
	public synchronized Event getDestinationEvent() {
		try {
			Event tempEvent = destinationEvents.remove();
			System.out.println(Thread.currentThread().getName() + " is receiving DestinationEvent. " + 
					tempEvent);
			notifyAll();
			return tempEvent;
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	/**
	 * Add an event that signifies an elevator destination
	 * @param event The event object to add
	 */
	public synchronized void putDestinationEvent(Event event) {
		while (!destinationEvents.isEmpty()) {
			try {
				System.out.println("wait() put destination event");
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is sending DestinationEvent. " + 
				event);
		destinationEvents.add(event);
		notifyAll();
	}

	
	/**
	 * Add an event that signifies an elevator has arrived at a floor
	 * @param arrivalEvent The event object to add
	 */
	public synchronized SchedulerEvent getSchedulerEvent() {
		while (schedEvent == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				return null;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is receiving SchedulerEvent. " + 
				schedEvent);
		SchedulerEvent tempEvent = schedEvent;
		schedEvent = null;
		notifyAll();
		return tempEvent;
	}
	
	/**
	 * Add an event that signifies an scheduler event
	 * @param event The event object to add
	 */
	public synchronized void putSchedulerEvent(SchedulerEvent event) {
		while (schedEvent != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				return;
			}
		}
		System.out.println(Thread.currentThread().getName() + " is sending SchedulerEvent. " + 
				event);
		schedEvent = event; //need to decide what type of event to send if no source event for arrival event
		notifyAll();
	}
}
