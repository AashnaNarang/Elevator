package test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class TestScenarios {
	Elevator elevator, elevator2, elevator3;
	Thread floorSubsystemThread, schedThread, elevatorThread, elevatorThread2
	, elevatorThread3; 
	
	@Test
	public void ScenarioTwo() {
		try {
			int numFloors = 6;
			Elevator elevator = new Elevator(numFloors, 63, 73, 43, 120, 101);
			floorSubsystemThread = new Thread(new FloorSubsystem("input2.txt", 6, 23, 33), "floorSubsystem");
			schedThread = new Thread(new Scheduler(33, 43, 120, 23, 101), "scheduler");
			elevatorThread = new Thread(elevator, "elevator");
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
			elevator = new Elevator(numFloors, 64, 74, 44, 121, 102);
			floorSubsystemThread = new Thread(new FloorSubsystem("input3.txt", 7, 24, 34), "floorSubsystem");
			schedThread = new Thread(new Scheduler(34, 44, 121, 24, 102), "scheduler");
			elevatorThread = new Thread(elevator, "elevator1");
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
			elevator = new Elevator(numFloors, 65, 75, 45, 121, 103);
			floorSubsystemThread = new Thread(new FloorSubsystem("input4.txt", 8, 25, 35), "floorSubsystem");
			schedThread = new Thread(new Scheduler(35, 45, 122, 25, 103), "scheduler");
			elevatorThread = new Thread(elevator, "elevator1");
			elevatorThread2 = new Thread(elevator2, "elevator2");
			elevatorThread3 = new Thread(elevator3, "elevator3");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 1", elevator.toString());
		} catch (InterruptedException e) {
		}
	}
}
