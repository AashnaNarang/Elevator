package test;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import events.FloorEvent;
import main.Direction;
import main.Elevator;
import main.MiddleMan;
import main.Scheduler;
import states.DoorOpenState;
import states.ElevatorState;
import states.StationaryState;

public class IterationTwoTest {
	
	Elevator elevator; 
	MiddleMan middleMan1, middleMan2;
	FloorEvent floorEvent; 
	StationaryState stationaryState; 
	Scheduler scheduler; 
	
	@Before
	public void setUp() {
		middleMan1 = new MiddleMan();
		middleMan2 = new MiddleMan();
		floorEvent = new FloorEvent(LocalTime.now(), 1, Direction.UP, 4);
		scheduler = new Scheduler(middleMan1, middleMan2); 
		middleMan2.putFloorEvent(floorEvent);
		stationaryState = new StationaryState(elevator); 
	}

	@Test
	public void test() {
		elevator = new Elevator(middleMan2, 6);
		elevator.setState(new DoorOpenState(elevator, floorEvent));
		stationaryState.handleFloorEvent();
		assertEquals(elevator.getClass(), DoorOpenState.class);
	}

}
