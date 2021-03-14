package test;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import events.FloorEvent;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class IterationThreeTest {
	
	private FloorEvent floorEvent; 
	private FloorSubsystem floorSubsystems;
	private Elevator elevator; 
	private Scheduler scheduler; 

	@Before
	public void setUp() {
		floorEvent = new FloorEvent(LocalTime.now(), 1, Direction.UP, 4);

////		floorSubsystem = new FloorSubsystem("input.txt", 6, 23, 33);
////		elevator =  new Elevator(6, 63, 73, 43, 120, 101);
//		Thread floorSubsystem = new Thread(new FloorSubsystem("input1.txt", 6, 23, 33), "floorSubsystem");
//		Thread sched = new Thread(new Scheduler(33, 43, 120, 23, 101), "scheduler");
//		Thread elevatorThread = new Thread(elevator, "elevator");
//	    Thread elevator2 = new Thread(new Elevator(6, 64, 74, 43, 120, 101), "elevator2");
//		floorSubsystem.start();
//		sched.start();
//		elevator2.start();
		//Thread elevatorThread = new Thread(elevator, "elevator");

	}
	
	@Test
	public void testFloorSubsystemToSchedulerToElevator() throws InterruptedException {
		elevator = new Elevator(6, 63, 73, 43, 120, 101);
		scheduler = new Scheduler(33, 43, 120, 23, 101);
		floorSubsystems = new FloorSubsystem("input.txt", 6, 23, 33);
		Thread floorSubsystem = new Thread(floorSubsystems, "floorSubsystem");
		Thread sched = new Thread(scheduler, "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
		floorSubsystem.start();
		sched.start();
		TimeUnit.SECONDS.sleep(1);
		assertEquals(1, scheduler.getFloorEventsList().size());
		elevatorThread.start();
		TimeUnit.SECONDS.sleep(1);
		assertEquals(0, scheduler.getFloorEventsList().size());
		assertNotNull(scheduler.getArrivalEventFromElevator());
	}
//	
//	public void testSchedulerToElevator() throws InterruptedException {
//		elevator = new Elevator(6, 64, 74, 44, 121, 102);
//		scheduler = new Scheduler(34, 44, 121, 24, 102);
//		Thread sched = new Thread(scheduler, "scheduler");
//		Thread elevatorThread = new Thread(elevator, "elevator");
//		scheduler.addToFloorEventsList(floorEvent);
//		sched.start();
//		TimeUnit.SECONDS.sleep(1);
//		assertEquals(1, scheduler.getFloorEventsList().size());
//		elevatorThread.start();
//		TimeUnit.SECONDS.sleep(1);
//		assertEquals(0, scheduler.getFloorEventsList().size());
//	}
//	
	@Test
	public void testElevatorToScheduler() throws InterruptedException {
		elevator = new Elevator(6, 64, 74, 44, 121, 102);
		scheduler = new Scheduler(34, 44, 121, 24, 102);
		Thread sched = new Thread(scheduler, "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
		sched.start();
		scheduler.addToFloorEventsList(floorEvent);
		Thread.sleep(3);
		elevatorThread.start();
		assertEquals(1, scheduler.getArrivalEventsList().size());

	}
	
//	@Test 
//	public void testSchedulerToElevator() throws InterruptedException {
//		elevator = new Elevator(6, 63, 73, 43, 120, 101);
//		Thread sched = new Thread(scheduler, "scheduler");
//		Thread elevatorThread = new Thread(elevator, "elevator");
//		scheduler.addToFloorEventsList(floorEvent);
//		assertEquals(1, scheduler.getFloorEventsList().size());
//		elevatorThread.start();
//		sched.start();
//		TimeUnit.SECONDS.sleep(3);
//		assertEquals(0, scheduler.getFloorEventsList().size());
//
//	}

}

