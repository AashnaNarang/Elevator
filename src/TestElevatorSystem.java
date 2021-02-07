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
	Thread elevatorThread;
	
	@Before
	public void setUp() throws Exception {
		middleMan1 = new MiddleMan();
		middleMan2 = new MiddleMan();
		elevator = new Elevator(middleMan2, 4);
		floorSubsystemThread = new Thread(new FloorSubsystem("input.txt", 4, middleMan1), "floorSubsystem");
		schedThread = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
		elevatorThread = new Thread(elevator, "elevator");
		floorSubsystemThread.start();
		schedThread.start();
		elevatorThread.start();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try {
			TimeUnit.SECONDS.sleep(5);
			assertEquals("The elevator is currently on floor: 3", elevator.toString());
		} catch (InterruptedException e) {
		}
	}

}
