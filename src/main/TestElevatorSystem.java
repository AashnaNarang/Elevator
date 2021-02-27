package main;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class TestElevatorSystem {
	MiddleMan middleMan1;
	MiddleMan middleMan2;
	Elevator elevator;

	@Test
	public void test() {
		try {
			int numFloors = 4;
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, numFloors);
			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", numFloors, middleMan1), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator");
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
			int numFloors = 6;
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, numFloors);
			Thread floorSubsystemThread2 = new Thread(new FloorSubsystem("input2.txt", numFloors, middleMan1), "floorSubsystem");
			Thread schedThread2 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			Thread elevatorThread2 = new Thread(elevator, "elevator");
			floorSubsystemThread2.start();
			schedThread2.start();
			elevatorThread2.start();
			TimeUnit.SECONDS.sleep(5);
//			assertEquals("The elevator is currently on floor: 4", elevator.toString());
//			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	
	public void test3() {
		try {
			int numFloors = 6;
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, numFloors);
			Thread floorSubsystemThread3 = new Thread(new FloorSubsystem("input2.txt", numFloors, middleMan1), "floorSubsystem");
			Thread schedThread3 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			Thread elevatorThread3 = new Thread(elevator, "elevator");
			floorSubsystemThread3.start();
			schedThread3.start();
			elevatorThread3.start();
			TimeUnit.SECONDS.sleep(5);
//			assertEquals("The elevator is currently on floor: 4", elevator.toString());
//			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	
	public void test4() {
		try {
			int numFloors = 6;
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, numFloors);
			Thread floorSubsystemThread4 = new Thread(new FloorSubsystem("input2.txt", numFloors, middleMan1), "floorSubsystem");
			Thread schedThread4 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			Thread elevatorThread4 = new Thread(elevator, "elevator");
			floorSubsystemThread4.start();
			schedThread4.start();
			elevatorThread4.start();
			TimeUnit.SECONDS.sleep(5);
//			assertEquals("The elevator is currently on floor: 2", elevator.toString());
//			assertEquals("The elevator is currently on floor: 2", elevator.toString());
			assertEquals("The elevator is currently on floor: 2", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	
	public void test5() {
		try {
			int numFloors = 6;
			middleMan1 = new MiddleMan();
			middleMan2 = new MiddleMan();
			elevator = new Elevator(middleMan2, numFloors);
			Thread floorSubsystemThread5 = new Thread(new FloorSubsystem("input2.txt", numFloors, middleMan1), "floorSubsystem");
			Thread schedThread5 = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
			Thread elevatorThread5 = new Thread(elevator, "elevator");
			floorSubsystemThread5.start();
			schedThread5.start();
			elevatorThread5.start();
			TimeUnit.SECONDS.sleep(5);
//			assertEquals("The elevator is currently on floor: 6", elevator.toString());
			assertEquals("The elevator is currently on floor: 6", elevator.toString());
		}catch (InterruptedException e) {
		}
	}
	

}
