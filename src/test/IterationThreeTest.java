package test;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import events.ArrivalEvent;
import events.FloorEvent;
import main.Direction;
import main.Elevator;
import main.Floor;
import main.FloorSubsystem;
import main.Scheduler;
import states.ActiveState;

public class IterationThreeTest {
	
	private FloorEvent floorEvent; 
	private FloorSubsystem floorSubsystems;
	private Elevator elevator; 
	private Scheduler scheduler; 

	@Before
	public void setUp() {
		floorEvent = new FloorEvent(LocalTime.now(), 1, Direction.UP, 4);
	}
	
	@Test
	public void testFloorSubsystemToSchedulerToElevator() throws InterruptedException {
		elevator = new Elevator(63, 73, 43, 120, 101);
		scheduler = new Scheduler(33, 43, 120, 23, 101);
		floorSubsystems = new FloorSubsystem("input.txt", 23, 33);
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
	
	@Test
	public void testElevatorToScheduler() throws InterruptedException {
		elevator = new Elevator(64, 74, 44, 121, 102);
		scheduler = new Scheduler(34, 44, 121, 24, 102);
		Thread sched = new Thread(scheduler, "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
		scheduler.addToFloorEventsList(floorEvent);
		scheduler.setState(new ActiveState(scheduler));
		sched.start();
		elevatorThread.start();
		TimeUnit.SECONDS.sleep(1);
		assertEquals(0, scheduler.getFloorEventsList().size());
	}
	
	@Test
	public void testSchedulerToFloor() throws InterruptedException {
		scheduler = new Scheduler(35, 45, 122, 25, 103);
		floorSubsystems = new FloorSubsystem("input6.txt", 25, 35);
		//The input file is going up
		//08:10:23.100 2 up 3
		Thread floorSubsystemThread = new Thread(floorSubsystems, "floorSubsystem");
		Thread sched = new Thread(scheduler, "scheduler");
		floorSubsystemThread.start();
		sched.start();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(floorSubsystems.getFloorList().get(1).isUpButtonOn());
		ArrivalEvent ae = new ArrivalEvent(2,LocalTime.now(),Direction.UP,45,1);
		scheduler.addToArrivalEventsList(ae);
		scheduler.sendArrivalEventToFloor(ae);
		TimeUnit.SECONDS.sleep(1);	
		assertFalse(floorSubsystems.getFloorList().get(1).isUpButtonOn());
		}
	
	@Test
	public void loadBalancingTest() throws InterruptedException {
		Elevator elevator = new Elevator(66, 76, 46, 123, 104);
		Elevator elevator2 = new Elevator(67, 77, 46, 123, 104);
		Thread floorSubsystem = new Thread(new FloorSubsystem("input5.txt", 26, 36), "floorSubsystem");
		Thread sched = new Thread(new Scheduler(36, 46, 123, 26, 104), "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
		Thread elevatorThread2 = new Thread(elevator2, "elevator2");
		floorSubsystem.start();
		sched.start();
		elevatorThread.start();
		TimeUnit.SECONDS.sleep(1);
		elevatorThread2.start();
		TimeUnit.SECONDS.sleep(10);
		assertNotEquals("The elevator is currently on floor: 1", elevator.toString());
		assertNotEquals("The elevator is currently on floor: 1", elevator2.toString());
	}
}

