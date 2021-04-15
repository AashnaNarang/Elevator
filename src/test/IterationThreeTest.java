package test;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import events.ArrivalEvent;
import events.FloorEvent;
import main.Configurations;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;
import main.Timing;
import states.ActiveState;

public class IterationThreeTest {

	private FloorEvent floorEvent;
	private FloorSubsystem floorSubsystems;
	private Elevator elevator;
	private Scheduler scheduler;

	@Before
	public void setUp() {
		floorEvent = new FloorEvent(LocalTime.now(), 1, Direction.UP, 4, 0);
	}

	@Test
	public void testFloorSubsystemToSchedulerToElevator() throws InterruptedException {
		elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		scheduler = new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
				Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
				Configurations.TIMER_PORT);
		floorSubsystems = new FloorSubsystem("input.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT);
		Thread floorSubsystem = new Thread(floorSubsystems, "floorSubsystem");
		Thread sched = new Thread(scheduler, "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
		floorSubsystem.start();
		sched.start();
		TimeUnit.SECONDS.sleep(1);
		assertEquals(1, scheduler.getFloorEventsList().size());
		elevatorThread.start();
		TimeUnit.SECONDS.sleep(3);
		assertEquals(0, scheduler.getFloorEventsList().size());
		Timing.getTimingInfo(); // reset static var
		//assertNotNull(scheduler.getArrivalEventFromElevator());
	}

	@Test
	public void testElevatorToScheduler() throws InterruptedException {
		elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1, Configurations.ELEVATOR_SCHEDULAR_PORT + 1,
				Configurations.ARRIVAL_PORT + 1, Configurations.DEST_PORT + 1, Configurations.ELEVATOR_STAT_PORT + 1);
		scheduler = new Scheduler(Configurations.FLOOR_EVENT_PORT + 1, Configurations.ARRIVAL_PORT + 1,
				Configurations.DEST_PORT + 1, Configurations.FLOOR_PORT + 1, Configurations.ELEVATOR_STAT_PORT + 1,
				Configurations.TIMER_PORT + 1);
		Thread sched = new Thread(scheduler, "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
		scheduler.addToFloorEventsList(floorEvent);
		scheduler.setState(new ActiveState(scheduler));
		sched.start();
		elevatorThread.start();
		TimeUnit.SECONDS.sleep(3);
		assertEquals(0, scheduler.getFloorEventsList().size());
		Timing.getTimingInfo(); // reset static var
	}

	@Test
	public void testSchedulerToFloor() throws InterruptedException {
		scheduler = new Scheduler(Configurations.FLOOR_EVENT_PORT + 2, Configurations.ARRIVAL_PORT + 2,
				Configurations.DEST_PORT + 2, Configurations.FLOOR_PORT + 2, Configurations.ELEVATOR_STAT_PORT + 2, Configurations.TIMER_PORT + 2);
		floorSubsystems = new FloorSubsystem("input6.txt", Configurations.FLOOR_PORT + 2,
				Configurations.FLOOR_EVENT_PORT + 2);
		// The input file is going up
		// 08:10:23.100 2 up 3
		Thread floorSubsystemThread = new Thread(floorSubsystems, "floorSubsystem");
		Thread sched = new Thread(scheduler, "scheduler");
		floorSubsystemThread.start();
		sched.start();
		TimeUnit.SECONDS.sleep(1);
		assertTrue(floorSubsystems.getFloorList().get(1).isUpButtonOn());
		ArrivalEvent ae = new ArrivalEvent(2, LocalTime.now(), Direction.UP, 45, 1);
		scheduler.addToArrivalEventsList(ae);
		scheduler.sendArrivalEventToFloor(ae);
		TimeUnit.SECONDS.sleep(3);
		assertFalse(floorSubsystems.getFloorList().get(1).isUpButtonOn());
		Timing.getTimingInfo(); // reset static var
	}

	@Test
	public void loadBalancingTest() throws InterruptedException {   
		Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 2,
				Configurations.ELEVATOR_SCHEDULAR_PORT + 2, Configurations.ARRIVAL_PORT + 3,
				Configurations.DEST_PORT + 3, Configurations.ELEVATOR_STAT_PORT + 3);
		
		Elevator elevator2 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 3,
				Configurations.ELEVATOR_SCHEDULAR_PORT + 3, Configurations.ARRIVAL_PORT + 3,
				Configurations.DEST_PORT + 3, Configurations.ELEVATOR_STAT_PORT + 3);
		
		Thread floorSubsystem = new Thread(
				new FloorSubsystem("input5.txt", Configurations.FLOOR_PORT + 3, Configurations.FLOOR_EVENT_PORT + 3),
				"floorSubsystem");
		Thread sched = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT + 3, Configurations.ARRIVAL_PORT + 3,
				Configurations.DEST_PORT + 3, Configurations.FLOOR_PORT + 3, Configurations.ELEVATOR_STAT_PORT + 3,
				Configurations.TIMER_PORT + 3), "scheduler");
		Thread elevatorThread0 = new Thread(elevator, "elevator 0");
		Thread elevatorThread1 = new Thread(elevator2, "elevator 1");
		floorSubsystem.start();
		sched.start();
		elevatorThread0.start();
		elevatorThread1.start();

		TimeUnit.SECONDS.sleep(10);
		assertNotEquals("The elevator is currently on floor: 1 with error code 0", elevator.toString());
		assertNotEquals("The elevator is currently on floor: 1 with error code 0", elevator2.toString());
		Timing.getTimingInfo(); // reset static var
	}
}
