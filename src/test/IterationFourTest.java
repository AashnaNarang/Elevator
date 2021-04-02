package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import events.FloorEvent;
import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class IterationFourTest {

	@Test
	public void TestInputSeven() {
		try {
			Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
					Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
			Elevator elevator2 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1,
					Configurations.ELEVATOR_SCHEDULAR_PORT + 1, Configurations.ARRIVAL_PORT, Configurations.DEST_PORT,
					Configurations.ELEVATOR_STAT_PORT);
			Thread floorSubsystemThread = new Thread(
					new FloorSubsystem("input7.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT),
					"floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
					Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
					Configurations.TIMER_PORT), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator 1");
			Thread elevatorThread2 = new Thread(elevator2, "elevator 2");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			elevatorThread2.start();
//			elevatorThread2.sleep(4000);
			elevatorThread2.sleep(5000);
			TimeUnit.SECONDS.sleep(80);
//			assertEquals("The elevator is currently on floor: 5", elevator.toString());
//			assertEquals("The elevator is currently on floor: 7", elevator2.toString());
			ArrayList<String> elevStrings = new ArrayList<String>();
			elevStrings.add("The elevator is currently on floor: 1 with error code 2");
			elevStrings.add("The elevator is currently on floor: 7 with error code 0");
			// Check if elevator final state string is in a list of possible states because it is difficult to control 
			// which elevator object will pick up with floor event
			assertTrue(elevStrings.contains(elevator.toString()));
			assertTrue(elevStrings.contains(elevator2.toString()));
//			assertEquals("The elevator is currently on floor: 1 with error code 2", elevator.toString());
//			assertEquals("The elevator is currently on floor: 7 with error code 0", elevator2.toString());
		} catch (InterruptedException e) {
		}
	}

}
