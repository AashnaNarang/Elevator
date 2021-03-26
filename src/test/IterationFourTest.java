package test;

import org.junit.Test;

import events.FloorEvent;
import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class IterationFourTest {

	private FloorEvent floorEvent; 
	private FloorSubsystem floorSubsystem;
	private Elevator elevator; 
	private Scheduler scheduler; 
	
	@Test
	public void testInputFile() {
		elevator = new Elevator(66, 76, 46, 123, 104);
		Thread floorSubsystem = new Thread(new FloorSubsystem("input7.txt", 26, 36), "floorSubsystem");
		Thread scheduler = new Thread(new Scheduler(36, 46, 123, 26, 104, Configurations.TIMER_PORT), "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
	}

}
