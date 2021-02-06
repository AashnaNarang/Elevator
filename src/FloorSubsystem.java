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

public class FloorSubsystem implements Runnable{
	
	private Queue<FloorEvent> eventList;
	private List<Floor> floors;
	private int numOfFloors;
	private MiddleMan box;
	private String filename;
	
	
	public FloorSubsystem(String filename, int numOfFloors, MiddleMan box) {
		this.filename = filename;
		this.box = box;
		this.numOfFloors = numOfFloors;
		this.eventList = new LinkedList<>();
		this.floors = new ArrayList<>();
	}
	


	//a work in progress 
	@Override
	public void run() {
		eventList = parseFile(filename);
		while(true) {
			FloorEvent e = eventList.peek();
			box.putFloorEvent(eventList.remove(0));
			ArrivalEvent arrivalEvent = box.getArrivalEvent();
			//turn off the button light?
//			if((e.getSource() == ArrivalEvent.getCurrentFloor())) {
//				floors.get(e.getSource()).turnButtonOn(, false);
//			}
			//what direction the elevator is going, depending on the direction remove one of the lights
		}
		
	}
	
	
	private Queue<FloorEvent> parseFile(String filename){
		Queue<FloorEvent> events = new LinkedList<FloorEvent>();
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			String currentLine = br.readLine();
			while(currentLine != null) {
				String[] inputEvent = currentLine.split(" "); 
				FloorEvent event = new FloorEvent(LocalTime.parse(inputEvent[0]),Integer.parseInt(inputEvent[1]),
													Direction.valueOf(inputEvent[2].toUpperCase()),Integer.parseInt(inputEvent[1]));
				events.add(event);
				currentLine = br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return events;
	}


//testing
//	public static void main(String args[]) throws IOException, ParseException {
//		FloorSubsystem fs = new FloorSubsystem("input.txt");
//		fs.eventList = fs.parseFile("input.txt");
//		for(FloorEvent eventSent : fs.eventList ) {
//			System.out.println(eventSent.toString());
//		}
//		
//	}
	

}
