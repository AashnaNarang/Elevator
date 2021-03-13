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
import main.MiddleMan;
import main.Scheduler;

public class IterationOneTest {
	
	Elevator elevator;
	Thread floorSubsystemThread;
	Thread schedThread;
	Thread elevatorThread;

	@Test
	public void test() {
		try {
			elevator = new Elevator(1, 6, 63, 73, 43, 120, 101);
			floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", 4, 23 ,33), "floorSubsystem");
			schedThread = new Thread(new Scheduler(33, 43, 120, 23, 101), "scheduler");
			elevatorThread = new Thread(elevator, "elevator");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();	
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 3", elevator.toString());
		} catch (InterruptedException e) {
		}
	}
	
	@Test
	public void parseEvents() {
			//08:10:23.100 1 up 3
		try {
			floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", 4, 23 ,33), "floorSubsystem");
			floorSubsystemThread.start();
			TimeUnit.SECONDS.sleep(1);
			FloorEvent floorEvent = middleMan1.getFloorEvent();
			assertEquals(floorEvent.getSource(), 1);
			assertEquals(floorEvent.getDestination(), 3);
			assertEquals(floorEvent.getDirection(), Direction.UP);
			assertEquals(floorEvent.getTime(), LocalTime.parse("08:10:23.100"));
			}catch (InterruptedException e) {
			}
	}
	
}


