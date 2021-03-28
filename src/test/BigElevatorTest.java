package test;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import events.FloorEvent;
import main.Configurations;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class BigElevatorTest {

	private int[] finalFloors3 = {3,3,7,9};
	private int[] finalFloors2 = {21,19,20,14};
	private int[] finalFloors1 = {6,5,22,10};

	@Test
	public void basicScenario() {
		try {
			Elevator elevator1 = new Elevator(66, 76, 46, 123, 104);
			Elevator elevator2 = new Elevator(67, 77, 46, 123, 104);
			Elevator elevator3 = new Elevator(68, 78, 46, 123, 104);
			Elevator elevator4 = new Elevator(69, 79, 46, 123, 104);

			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input10.txt", 26, 36), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(36, 46, 123, 26, 104, Configurations.TIMER_PORT), "scheduler");

			Thread elevatorThread1 = new Thread(elevator1, "elevator1");
			Thread elevatorThread2 = new Thread(elevator2, "elevator2");
			Thread elevatorThread3 = new Thread(elevator3, "elevator3");
			Thread elevatorThread4 = new Thread(elevator4, "elevator4");

			floorSubsystemThread.start();
			schedThread.start();

			elevatorThread1.start();
			elevatorThread2.start();
			elevatorThread3.start();
			elevatorThread4.start();
			TimeUnit.SECONDS.sleep(60);

			int [] listofFloors = new int[4];
			listofFloors[0] = elevator1.getCurrentFloor();
			listofFloors[1] = elevator2.getCurrentFloor();
			listofFloors[2] = elevator3.getCurrentFloor();
			listofFloors[3] = elevator4.getCurrentFloor();

			Arrays.sort(listofFloors);
			Arrays.sort(finalFloors1);
			assertTrue(Arrays.equals(listofFloors, finalFloors1 ));
		} catch (Exception e) {

		}
	}

	@Test
	public void fifthteenFloorEvents() {
		try {
			Elevator elevator1 = new Elevator(62, 72, 44, 121, 102);
			Elevator elevator2 = new Elevator(63, 73, 44, 121, 102);
			Elevator elevator3 = new Elevator(64, 74, 44, 121, 102);
			Elevator elevator4 = new Elevator(65, 75, 44, 121, 102);

			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input11.txt", 22, 35), "floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(35, 44, 121, 22, 102, Configurations.TIMER_PORT), "scheduler");

			Thread elevatorThread1 = new Thread(elevator1, "elevator1");
			Thread elevatorThread2 = new Thread(elevator2, "elevator2");
			Thread elevatorThread3 = new Thread(elevator3, "elevator3");
			Thread elevatorThread4 = new Thread(elevator4, "elevator4");

			floorSubsystemThread.start();
			schedThread.start();

			elevatorThread1.start();
			elevatorThread2.start();
			elevatorThread3.start();
			elevatorThread4.start();
			TimeUnit.SECONDS.sleep(180);

			int [] listofFloors = new int[4];
			listofFloors[0] = elevator1.getCurrentFloor();
			listofFloors[1] = elevator2.getCurrentFloor();
			listofFloors[2] = elevator3.getCurrentFloor();
			listofFloors[3] = elevator4.getCurrentFloor();

			Arrays.sort(listofFloors);
			Arrays.sort(finalFloors2 );
			assertTrue(Arrays.equals(listofFloors, finalFloors2));
		}
		catch(Exception e) {

		}
	}

	@Ignore
	@Test
	public void mixedFloorEvents() {
		try {
			Elevator elevator1 = new Elevator(66, 76, 46, 123, 104);
			Elevator elevator2 = new Elevator(67, 77, 46, 123, 104);
			Elevator elevator3 = new Elevator(68, 78, 46, 123, 104);
			Elevator elevator4 = new Elevator(69, 79, 46, 123, 104);


			Thread floorSubsystemThread = new Thread(new FloorSubsystem("input12.txt", 26, 36));
			Thread schedThread = new Thread(new Scheduler(36, 46, 123, 26, 104, Configurations.TIMER_PORT), "scheduler");

			Thread elevatorThread1 = new Thread(elevator1, "elevator1");
			Thread elevatorThread2 = new Thread(elevator2, "elevator2");
			Thread elevatorThread3 = new Thread(elevator3, "elevator3");
			Thread elevatorThread4 = new Thread(elevator4, "elevator4");

			floorSubsystemThread.start();
			schedThread.start();

			elevatorThread1.start();
			elevatorThread2.start();
			elevatorThread3.start();
			elevatorThread4.start();

			TimeUnit.SECONDS.sleep(180);

			int [] listofFloors = new int[4];
			listofFloors[0] = elevator1.getCurrentFloor();
			listofFloors[1] = elevator2.getCurrentFloor();
			listofFloors[2] = elevator3.getCurrentFloor();
			listofFloors[3] = elevator4.getCurrentFloor();

			Arrays.sort(listofFloors);
			Arrays.sort(finalFloors3);
			assertTrue(Arrays.equals(listofFloors, finalFloors3));
		}
		catch(Exception e) {

		}
	}

}
