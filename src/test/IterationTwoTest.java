package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import events.FloorEvent;
import main.Direction;
import main.Elevator;
import main.Scheduler;
import states.DoorOpenState;
import states.StationaryState;

public class IterationTwoTest {
	
	Elevator elevator; 
	FloorEvent floorEvent; 
	StationaryState stationaryState; 
	Scheduler scheduler; 
	Thread elevatorThread;

	@Before
	public void setUp() {
		floorEvent = new FloorEvent(LocalTime.now(), 1, Direction.UP, 4);
		scheduler = new Scheduler(33, 43, 120, 23, 101); 
		scheduler.addToFloorEventsList(floorEvent);
	}

	@Test
	public void test() {
		elevator = new Elevator(6, 63, 73, 43, 120, 101);
		stationaryState = new StationaryState(elevator);
		assertEquals(StationaryState.class, elevator.getState().getClass());
		stationaryState.handleFloorEvent();		
		assertEquals(DoorOpenState.class, elevator.getState().getClass());

	}

}
