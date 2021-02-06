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

/**
 * The FloorSubsystem Class parses events and processes the information from the
 * events, sending floorEvents and taking arrival events from scheduler
 * 
 */
public class FloorSubsystem implements Runnable {

	private Queue<FloorEvent> eventList;
	private List<Floor> floors;
	private int numOfFloors;
	private MiddleMan box;
	private String filename;

	/**
	 * Constructor for FloorSubsystem
	 * 
	 * @param filename    the file to be parsed
	 * @param numOfFloors the number of floors
	 * @param box         where events will be put and received
	 */
	public FloorSubsystem(String filename, int numOfFloors, MiddleMan box) {
		this.filename = filename;
		this.box = box;
		this.numOfFloors = numOfFloors;
		this.eventList = new LinkedList<>();
		this.floors = new ArrayList<>();
	}

	/**
	 * The run method passes to box the events parsed and then receives from the box
	 * arrivalEvent, triggers the buttons pressed to be on/off
	 * 
	 */
	@Override
	public void run() {
		eventList = parseFile(filename);
		while (true) {
			if (!eventList.isEmpty()) {
				FloorEvent eventSent = eventList.remove();
				box.putFloorEvent(eventSent);
				floors.get(eventSent.getSource()).turnButtonOn(eventSent.getDirection(), true);
			}
			ArrivalEvent arrivalEvent = box.getArrivalEvent();
			int currentFloor = arrivalEvent.getCurrentFloor();
			if (floors.get(currentFloor - 1).getLampStatusForUpButton()) {
				floors.get(currentFloor - 1).turnButtonOn(Direction.UP, false);
			} else if (floors.get(currentFloor - 1).getLampStatusForDownButton()) {
				floors.get(currentFloor - 1).turnButtonOn(Direction.DOWN, false);
			}
		}
	}

	/**
	 * Parsed the file to return a list of events
	 * 
	 * @param filename the file to be parsed
	 * @return A queue of events parsed from the file
	 */
	private Queue<FloorEvent> parseFile(String filename) {
		Queue<FloorEvent> events = new LinkedList<FloorEvent>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String currentLine = br.readLine();
			while (currentLine != null) {
				String[] inputEvent = currentLine.split(" ");
				FloorEvent event = new FloorEvent(LocalTime.parse(inputEvent[0]), Integer.parseInt(inputEvent[1]),
						Direction.valueOf(inputEvent[2].toUpperCase()), Integer.parseInt(inputEvent[3]));
				events.add(event);
				currentLine = br.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return events;
	}

}