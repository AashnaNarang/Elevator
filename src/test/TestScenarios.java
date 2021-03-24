package test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class TestScenarios {
	
	
	@Test
	public void ScenarioTwo() {
		try {
			int numFloors = 6;
			Elevator elevator = new Elevator(63, 73, 43, 120, 101);
			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input2.txt", 23, 33), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(33, 43, 120, 23, 101), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevato2");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
		} catch (InterruptedException e) {
		}
	}
	
	@Test
	public void ScenarioThree() {
		try {
			int numFloors = 6;
			Elevator elevator = new Elevator(64, 74, 44, 121, 102);
			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input3.txt", 24, 34), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(34, 44, 121, 24, 102), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator3");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 4", elevator.toString());
		} catch (InterruptedException e) {
			
		}
	}
	
	@Test
	public void ScenarioFour() {
		try {
			int numFloors = 6;
			Elevator elevator = new Elevator(65, 75, 45, 122, 103);
			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input4.txt", 25, 35), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(35, 45, 122, 25, 103), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator4");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
		} catch (InterruptedException e) {
		}
	}
}