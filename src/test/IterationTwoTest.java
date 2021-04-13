package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import events.FloorEvent;
import main.Configurations;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;
import states.DoorOpenState;
import states.ElevatorState;
import states.MovingState;
import states.StationaryState;


public class IterationTwoTest {
	
	Elevator elevator; 
	FloorEvent floorEvent; 
	StationaryState stationaryState; 
	Scheduler scheduler; 
	Thread elevatorThread;

	//This test is for state changes in the elevator`
	@Test
	public void test() throws InterruptedException {
		Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		Thread floorSubsystemThread = new Thread(
				new FloorSubsystem("input2.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT),
				"floorSubsystem");
		Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
				Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
				Configurations.TIMER_PORT), "scheduler");
		assertEquals(StationaryState.class, elevator.getState().getClass());
		Thread elevatorThread = new Thread(elevator, "elevator");
		elevatorThread.start();
		floorSubsystemThread.start();
		schedThread.start();	
		TimeUnit.SECONDS.sleep(10);
		assertEquals(DoorOpenState.class, elevator.getState().getClass());

	}

}
