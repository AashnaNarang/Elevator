package main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import events.ArrivalEvent;
import events.FloorEvent;

/**
 * The FloorSubsystem Class parses events and processes the information from the
 * events, sending floorEvents and taking arrival events from scheduler
 * 
 */
public class FloorSubsystem extends NetworkCommunicator implements Runnable {

	private Queue<FloorEvent> eventList;
	private List<Floor> floors;
	private String filename;
	private int schedPort;
	private DatagramSocket sendReceiveSocket; //declaration of socket


	/**
	 * Constructor for FloorSubsystem
	 * 
	 * @param filename    the file to be parsed
	 * @param numOfFloors the number of floors
	 */
	public FloorSubsystem(String filename, int numOfFloors, int hostPort, int schedPort) {
		this.filename = filename;
		this.eventList = new LinkedList<>();
		this.floors = new ArrayList<>();
		this.schedPort = schedPort;
		for (int floorNumber = 1; floorNumber <= numOfFloors; floorNumber++) {
			floors.add(new Floor(floorNumber));
		}
		try {
			sendReceiveSocket = new DatagramSocket(hostPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The run method passes to middleMan the events parsed and then receives from
	 * the middleMan arrivalEvent, triggers the buttons pressed to be on/off
	 * 
	 */
	@Override
	public void run() {
		parseFile(filename);
		while (true) {
			if (!eventList.isEmpty()) {
				FloorEvent eventSent = eventList.remove();
				byte[] data = Serial.serialize(eventSent);
				send(sendReceiveSocket, data, data.length, this.schedPort);
				System.out.println(Thread.currentThread().getName() + " is turning on " + eventSent.getDirection() +  " button for floor " + eventSent.getSource() + ".  {Time: " + LocalTime.now() + "}");
				floors.get(eventSent.getSource() - 1).switchButton(eventSent.getDirection(), true);
			}
			DatagramPacket receivePacket = receive(sendReceiveSocket, true);
			if (receivePacket != null) {
				ArrivalEvent arrivalEvent = Serial.deSerialize(receivePacket.getData(), ArrivalEvent.class);
				int currentFloor = arrivalEvent.getCurrentFloor();
				if (floors.get(currentFloor - 1).isUpButtonOn()) {
					System.out.println(Thread.currentThread().getName() + " is turning off up button for floor " + currentFloor + ".  {Time: " + LocalTime.now() + "}");
					floors.get(currentFloor - 1).switchButton(Direction.UP, false);
				} else if (floors.get(currentFloor - 1).isDownButtonOn()) {
					System.out.println(Thread.currentThread().getName() + " is turning off down button for floor " + currentFloor + ".  {Time: " + LocalTime.now() + "}");
					floors.get(currentFloor - 1).switchButton(Direction.DOWN, false);
				}
			}
		}
	}

	/**
	 * Parsed the file to return a list of events
	 * 
	 * @param filename the file to be parsed
	 */
	private void parseFile(String filename) {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String currentLine = br.readLine();
			while (currentLine != null) {
				String[] inputEvent = currentLine.split(" ");
				FloorEvent event = new FloorEvent(LocalTime.parse(inputEvent[0]), Integer.parseInt(inputEvent[1]),
						Direction.valueOf(inputEvent[2].toUpperCase()), Integer.parseInt(inputEvent[3]));
				eventList.add(event);
				currentLine = br.readLine();
			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	/**
	 * Method for testing
	 */
	public List<Floor> getFloorList() {
		return floors;
	}

}
