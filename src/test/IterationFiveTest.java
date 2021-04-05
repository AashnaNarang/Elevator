package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class IterationFiveTest {
	private List<String> elevatorListOfStatus;


	//This test is to check if the statuses are being appended correctly
	//The statuses is what we use to append
	@Test
	public void statusTest() {
		try {
			elevatorListOfStatus =  new ArrayList<>();
			Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
					Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
			Thread floorSubsystemThread = new Thread(
					new FloorSubsystem("input2.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT),
					"floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
					Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
					Configurations.TIMER_PORT), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(65);

			if (elevator !=null) {
				String elevatorStatus = "";
				LinkedList<String> statuses = elevator.getStatuses();
				for (String s : statuses) {
					elevatorStatus = elevatorStatus + s + "\n";
					elevatorListOfStatus.add(elevatorStatus);
				}
			}
			assertEquals(21, elevatorListOfStatus.size());
			String s = elevatorListOfStatus.get(18);
			assertTrue(s.contains("elevator arrived at floor 6"));


		} catch (InterruptedException e) {
		}
	}
}
