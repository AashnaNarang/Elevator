package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import events.FloorEvent;
import main.Configurations;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;
import main.Timing;
import states.DoorOpenState;
import states.ElevatorState;
import states.MovingState;
import states.StationaryState;


public class IterationTwoTest {
	

	//This test is for state changes in the elevator
	//This test is not relying on time, once the elevator opens doors it will be in door state, so we can make sure when 
	//we check it
	@Test
	public void stateChangeTest() throws InterruptedException {
		Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		Thread floorSubsystemThread = new Thread(
				new FloorSubsystem("input.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT),
				"floorSubsystem");
		Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
				Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
				Configurations.TIMER_PORT), "scheduler");
		assertEquals(StationaryState.class, elevator.getState().getClass());
		Thread elevatorThread = new Thread(elevator, "elevator");
		elevatorThread.start();
		floorSubsystemThread.start();
		schedThread.start();	
		TimeUnit.SECONDS.sleep(25);
		if (elevator !=null) {
			String elevatorStatus = "";
			LinkedList<String> statuses = elevator.getStatuses();
			for (String s : statuses) {
				elevatorStatus = elevatorStatus + s + "\n";
				if(elevatorStatus.contains("Door open")) {
				assertEquals(DoorOpenState.class, elevator.getState().getClass());
				}

			}
			Timing.getTimingInfo(); // reset static var
		}

	}
	//This test is time reliant
	@Test
	public void testStateChanges() throws InterruptedException {
		Elevator elevator = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 3,
				Configurations.ELEVATOR_SCHEDULAR_PORT + 3, Configurations.ARRIVAL_PORT + 3,
				Configurations.DEST_PORT + 3, Configurations.ELEVATOR_STAT_PORT + 3);
		Thread floorSubsystemThread = new Thread(
				new FloorSubsystem("input.txt", Configurations.FLOOR_PORT + 3, Configurations.FLOOR_EVENT_PORT + 3),
				"floorSubsystem");
		Thread schedThread = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT + 3, Configurations.ARRIVAL_PORT + 3,
				Configurations.DEST_PORT + 3, Configurations.FLOOR_PORT + 3, Configurations.ELEVATOR_STAT_PORT + 3,
				Configurations.TIMER_PORT + 3), "scheduler");
		assertEquals(StationaryState.class, elevator.getState().getClass());
		Thread elevatorThread = new Thread(elevator, "elevator");
		elevatorThread.start();
		floorSubsystemThread.start();
		schedThread.start();	
		TimeUnit.SECONDS.sleep(7);
		assertEquals(DoorOpenState.class, elevator.getState().getClass());
		Timing.getTimingInfo(); // reset static var
	}
	

}
