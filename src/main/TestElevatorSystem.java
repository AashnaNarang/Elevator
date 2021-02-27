package main;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestElevatorSystem {

	MiddleMan middleMan1;
	MiddleMan middleMan2;
	
	Elevator elevator;
	Thread floorSubsystemThread;
	Thread schedThread;
	Thread schedThread2;
	Thread schedThread3;
	Thread schedThread4;
	Thread schedThread5;
	Thread elevatorThread;
	Thread elevatorThread2;
	Thread elevatorThread3;
	Thread elevatorThread4;
	Thread elevatorThread5;
	Thread floorSubsystemThread2;
	Thread floorSubsystemThread3;
	Thread floorSubsystemThread4;
	Thread floorSubsystemThread5;
	/*
	@Before
	public void setUp() throws Exception {
		middleMan1 = new MiddleMan();
		middleMan2 = new MiddleMan();
		elevator = new Elevator(middleMan2, 4);
		floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", 4, middleMan1), "floorSubsystem");
		floorSubsystemThread2 = new Thread(new FloorSubsystem("input2.txt", 4, middleMan1), "floorSubsystem");
		floorSubsystemThread3 = new Thread(new FloorSubsystem("input3.txt", 4, middleMan1), "floorSubsystem");
		floorSubsystemThread4 = new Thread(new FloorSubsystem("input4.txt", 4, middleMan1), "floorSubsystem");
		floorSubsystemThread5 = new Thread(new FloorSubsystem("input5.txt", 4, middleMan1), "floorSubsystem");
		schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
		elevatorThread = new Thread(elevator, "elevator");
		floorSubsystemThread.start();
		floorSubsystemThread2.start();
		floorSubsystemThread3.start();
		floorSubsystemThread4.start();
		floorSubsystemThread5.start();
		schedThread.start();
		elevatorThread.start();
		
	}
	*/
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, 4);
			floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", 4, middleMan1), "floorSubsystem");
			schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread = new Thread(elevator, "elevator");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 3", elevator.toString());
		} catch (InterruptedException e) {
		}
	}
	
	public void test2() {
		try {
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, 4);
			floorSubsystemThread2 = new Thread(new FloorSubsystem("input2.txt", 4, middleMan1), "floorSubsystem");
			schedThread2 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread2 = new Thread(elevator, "elevator");
			floorSubsystemThread2.start();
			schedThread2.start();
			elevatorThread2.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 4", elevator.toString());
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	

	public void test3() {
		try {
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, 4);
			floorSubsystemThread3 = new Thread(new FloorSubsystem("input2.txt", 4, middleMan1), "floorSubsystem");
			schedThread3 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread3 = new Thread(elevator, "elevator");
			floorSubsystemThread3.start();
			schedThread3.start();
			elevatorThread3.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 4", elevator.toString());
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	
	public void test4() {
		try {
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, 4);
			floorSubsystemThread4 = new Thread(new FloorSubsystem("input2.txt", 4, middleMan1), "floorSubsystem");
			schedThread4 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread4 = new Thread(elevator, "elevator");
			floorSubsystemThread4.start();
			schedThread4.start();
			elevatorThread4.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	
	public void test5() {
		try {
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, 4);
			floorSubsystemThread5 = new Thread(new FloorSubsystem("input2.txt", 4, middleMan1), "floorSubsystem");
			schedThread5 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			elevatorThread5 = new Thread(elevator, "elevator");
			floorSubsystemThread5.start();
			schedThread5.start();
			elevatorThread5.start();
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	

}
