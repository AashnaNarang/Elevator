package elevator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
public class FloorSubsystem implements Runnable {

	private Queue<FloorEvent> eventList;
	private List<Floor> floors;
	private int numOfFloors;
	private MiddleMan middleMan;
	private String filename;

	/**
	 * Constructor for FloorSubsystem
	 * 
	 * @param filename    the file to be parsed
	 * @param numOfFloors the number of floors
	 * @param middleMan   where events will be put and received
	 */
	public FloorSubsystem(String filename, int numOfFloors, MiddleMan middleMan) {
		this.filename = filename;
		this.middleMan = middleMan;
		this.numOfFloors = numOfFloors;
		this.eventList = new LinkedList<>();
		this.floors = new ArrayList<>();
		for (int floorNumber = 1; floorNumber <= numOfFloors; floorNumber++) {
			floors.add(new Floor(floorNumber));
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
				middleMan.putFloorEvent(eventSent);
				floors.get(eventSent.getSource() - 1).switchButton(eventSent.getDirection(), true);
			}
			ArrivalEvent arrivalEvent = middleMan.getArrivalEvent();
			if (arrivalEvent != null) {
				int currentFloor = arrivalEvent.getCurrentFloor();
				if (floors.get(currentFloor - 1).isUpButtonOn()) {
					floors.get(currentFloor - 1).switchButton(Direction.UP, false);
				} else if (floors.get(currentFloor - 1).isDownButtonOn()) {
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

}
