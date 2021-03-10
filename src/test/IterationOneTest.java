package test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import main.Elevator;
import main.FloorSubsystem;
import main.MiddleMan;
import main.Scheduler;

public class IterationOneTest {
	
	MiddleMan middleMan1;
	MiddleMan middleMan2;
	
	Elevator elevator;
	Thread floorSubsystemThread;
	Thread schedThread;
	Thread elevatorThread;

	@Before
	public void setUp() throws Exception {
		middleMan1 = new MiddleMan();
		middleMan2 = new MiddleMan();
	}
	
	@Test
	public void test() {
		try {
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

}
