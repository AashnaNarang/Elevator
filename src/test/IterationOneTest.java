package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import events.FloorEvent;
import main.Configurations;
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
	
	//This test is to see if the events have been parsed
	@Test
	public void parseEvents() {
			//08:10:23.100 1 up 3
		try {
			floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT), "floorSubsystem");
			Scheduler scheduler = new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, 
					Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT, Configurations.TIMER_PORT);
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


