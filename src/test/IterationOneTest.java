package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import events.FloorEvent;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class IterationOneTest {

	Elevator elevator;
	Thread floorSubsystemThread;
	Thread schedThread;
	Thread elevatorThread;

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void parseEvents() {
			//08:10:23.100 1 up 3
		try {
			floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", 23, 33), "floorSubsystem");
			Scheduler scheduler = new Scheduler(33, 43, 120, 23, 101);
			floorSubsystemThread.start();
			schedThread = new Thread(scheduler, "scheduler");
			schedThread.start();
			TimeUnit.SECONDS.sleep(1);
			FloorEvent floorEvent = scheduler.getFloorEventsList().peek();
			assertEquals(floorEvent.getSource(), 1);
			assertEquals(floorEvent.getDestination(), 3);
			assertEquals(floorEvent.getDirection(), Direction.UP);
			assertEquals(floorEvent.getTime(), LocalTime.parse("08:10:23.100"));
			}catch (InterruptedException e) {
			}
	}
	
}


