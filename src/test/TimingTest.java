package test;

import static org.junit.Assert.assertNotNull;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;
import main.Timing;

public class TimingTest {
	
	//This is to test for timing 
	@Test
	public void timingInfoTest() throws InterruptedException {
		Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		Thread floorSubsystemThread = new Thread(
				new FloorSubsystem("input2.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT),
				"floorSubsystem");
		Scheduler scheduler = new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
				Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
				Configurations.TIMER_PORT);
		Thread schedThread = new Thread(scheduler, "scheduler");
		Thread elevatorThread = new Thread(elevator, "elevator");
		floorSubsystemThread.start();
		schedThread.start();
		elevatorThread.start();
		TimeUnit.SECONDS.sleep(5);
		//wait until all events are serviced
		while(scheduler.getNumOfProcessedEvents() != Timing.getNumRequests()) {
			TimeUnit.SECONDS.sleep(5);
		}
		assertNotNull(Timing.getTimingInfo()); // Check to see if the timing info is null
	}
}

