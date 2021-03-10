package test;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import main.Elevator;
import main.FloorSubsystem;
import main.MiddleMan;
import main.Scheduler;

public class IterationThreeTests {
	MiddleMan middleMan1;
	MiddleMan middleMan2;
	Elevator elevator, elevator2, elevator3, elevator4;
	Thread floorSubsystemThread, schedThread, elevatorThread, elevatorThread2
	, elevatorThread3; 
	
	@Before
	public  void setUp() {
		middleMan1 = new MiddleMan();
		middleMan2 = new MiddleMan();
	}

	@Test
	public void ScenarioTwo() {
		try {
			int numFloors = 6;
			elevator = new Elevator(middleMan2, numFloors);
			elevator2 = elevator; 
			floorSubsystemThread = new Thread(new FloorSubsystem("input2.txt", numFloors, middleMan1), "floorSubsystem");
			schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread = new Thread(elevator, "elevator1");
			elevatorThread2 = new Thread(elevator2, "elevator2");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			elevatorThread2.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
			assertEquals("The elevator is currently on floor: 4", elevator2.toString());
		} catch (InterruptedException e) {
		}
	}
	
	@Test
	public void ScenarioThree() {
		try {
			int numFloors = 6;
			elevator = new Elevator(middleMan2, numFloors);
			elevator2 = elevator; 
			elevator3 = elevator; 
			floorSubsystemThread = new Thread(new FloorSubsystem("input3.txt", numFloors, middleMan1), "floorSubsystem");
			schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread = new Thread(elevator, "elevator1");
			elevatorThread2 = new Thread(elevator2, "elevator2");
			elevatorThread3 = new Thread(elevator3, "elevator3");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			elevatorThread2.start();
			elevatorThread3.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 4", elevator.toString());
			assertEquals("The elevator is currently on floor: 2", elevator2.toString());
			assertEquals("The elevator is currently on floor: 6", elevator3.toString());
		} catch (InterruptedException e) {
		}
	}
	
	@Test
	public void ScenarioFour() {
		try {
			int numFloors = 6;
			elevator = new Elevator(middleMan2, numFloors);
			elevator2 = elevator; 
			elevator3 = elevator; 
			floorSubsystemThread = new Thread(new FloorSubsystem("input4.txt", numFloors, middleMan1), "floorSubsystem");
			schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread = new Thread(elevator, "elevator1");
			elevatorThread2 = new Thread(elevator2, "elevator2");
			elevatorThread3 = new Thread(elevator3, "elevator3");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			elevatorThread2.start();
			elevatorThread3.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 2", elevator2.toString());
			assertEquals("The elevator is currently on floor: 2", elevator3.toString());
		} catch (InterruptedException e) {
		}
	}
	
	@Test
	public void ScenarioFive() {
		try {
			int numFloors = 6;
			elevator = new Elevator(middleMan2, numFloors);
			elevator2 = elevator; 
			floorSubsystemThread = new Thread(new FloorSubsystem("input5.txt", numFloors, middleMan1), "floorSubsystem");
			schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread = new Thread(elevator, "elevator1");
			elevatorThread2 = new Thread(elevator2, "elevator2");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			elevatorThread2.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
			assertEquals("The elevator is currently on floor: 6", elevator2.toString());
		} catch (InterruptedException e) {
		}
	}

}