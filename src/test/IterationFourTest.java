package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import events.FloorEvent;
import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class IterationFourTest {

	@Test
	public void TestInputNine() {
		try {
			Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
					Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
			Elevator elevator1 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1,
					Configurations.ELEVATOR_SCHEDULAR_PORT + 1, Configurations.ARRIVAL_PORT, Configurations.DEST_PORT,
					Configurations.ELEVATOR_STAT_PORT);
			Thread floorSubsystemThread = new Thread(
					new FloorSubsystem("input9.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT),
					"floorSubsystem");
			Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
					Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
					Configurations.TIMER_PORT), "scheduler");
			Thread elevatorThread = new Thread(elevator, "elevator 0");
			Thread elevatorThread1 = new Thread(elevator1, "elevator 1");
			floorSubsystemThread.start();
			schedThread.start();
			elevatorThread.start();
			elevatorThread1.start();
			TimeUnit.SECONDS.sleep(120);
			boolean elevatorStatus = elevator.isRunning();
			boolean elevator1Status = elevator1.isRunning();
			assertNotEquals(elevatorStatus, elevator1Status);
			if(elevatorStatus) {
				assertEquals("The elevator is currently on floor: 6", elevator.toString());
			}else {
				assertEquals("The elevator is currently on floor: 6", elevator1.toString());
			}
			
		} catch (InterruptedException e) {
		}
	}

//	@Test
//	public void TestPermanentError() {
//		Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 2, Configurations.ELEVATOR_SCHEDULAR_PORT + 2,
//				Configurations.ARRIVAL_PORT + 2, Configurations.DEST_PORT + 2, Configurations.ELEVATOR_STAT_PORT + 2);
//		Elevator elevator2 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 2,
//				Configurations.ELEVATOR_SCHEDULAR_PORT + 3, Configurations.ARRIVAL_PORT + 2, Configurations.DEST_PORT + 2,
//				Configurations.ELEVATOR_STAT_PORT + 2);
//		Thread floorSubsystemThread = new Thread(
//				new FloorSubsystem("input7.txt", Configurations.FLOOR_PORT + 1, Configurations.FLOOR_EVENT_PORT + 1),
//				"floorSubsystem");
//		Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT + 1, Configurations.ARRIVAL_PORT + 2,
//				Configurations.DEST_PORT + 2, Configurations.FLOOR_PORT + 1, Configurations.ELEVATOR_STAT_PORT + 2,
//				Configurations.TIMER_PORT + 2), "scheduler");
//		
//	}

}
