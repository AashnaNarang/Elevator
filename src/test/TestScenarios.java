package test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class TestScenarios {

	@Test
	public void ScenarioTwo() {
		try {
			Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
					Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
			Thread floorSubsystemThread = new Thread(
					new FloorSubsystem("input2.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT),
					"floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
					Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
					Configurations.TIMER_PORT), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevato2");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 5 with error code 0", elevator.toString());
		} catch (InterruptedException e) {
		}
	}

	@Test
	public void ScenarioThree() {
		try {
			Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1,
					Configurations.ELEVATOR_SCHEDULAR_PORT + 1, Configurations.ARRIVAL_PORT + 1,
					Configurations.DEST_PORT + 1, Configurations.ELEVATOR_STAT_PORT + 1);
			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input3.txt", Configurations.FLOOR_PORT + 1,
					Configurations.FLOOR_EVENT_PORT + 1), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT + 1,
					Configurations.ARRIVAL_PORT + 1, Configurations.DEST_PORT + 1, Configurations.FLOOR_PORT + 1,
					Configurations.ELEVATOR_STAT_PORT + 1, Configurations.TIMER_PORT + 1), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator3");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(15);
			assertEquals("The elevator is currently on floor: 4 with error code 0", elevator.toString());
		} catch (InterruptedException e) {

		}
	}

	@Test
	public void ScenarioFour() {
		try {
			Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 2,
					Configurations.ELEVATOR_SCHEDULAR_PORT + 2, Configurations.ARRIVAL_PORT + 2,
					Configurations.DEST_PORT + 2, Configurations.ELEVATOR_STAT_PORT + 2);
			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input4.txt", Configurations.FLOOR_PORT + 2,
					Configurations.FLOOR_EVENT_PORT + 2), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT + 2,
					Configurations.ARRIVAL_PORT + 2, Configurations.DEST_PORT + 2, Configurations.FLOOR_PORT + 2,
					Configurations.ELEVATOR_STAT_PORT + 2, Configurations.TIMER_PORT + 2),
					"scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator4");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 2 with error code 0", elevator.toString());
		} catch (InterruptedException e) {
		}
	}
}