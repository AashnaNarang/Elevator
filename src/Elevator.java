import java.lang.Math;
import java.time.LocalTime;
import java.util.ArrayList;

/*
 * The Elevator class is designed so that it takes the task form the middleman
 * and moves the elevator up/down based on the given task it will receive.
 */

public class Elevator implements Runnable {
	private int currentFloor;
	private MiddleMan middleMan;
	private DirectionLamp upLamp;
	private DirectionLamp downLamp;
	private Direction direction;
	private ArrayList<ElevatorButton> buttons;

	/*
	 * constructor for Elevator Defining the middleclass parameters that are by to
	 * the scheduler.
	 *
	 * @param middleman - sending information to Middleman via middleman.
	 */
	public Elevator(MiddleMan middleMan, int numFloor) {
		this.middleMan = middleMan;
		this.currentFloor = -1;
		this.upLamp = new DirectionLamp(Direction.UP);
		this.downLamp = new DirectionLamp(Direction.DOWN);
		this.direction = Direction.UP;
		this.buttons = new ArrayList<ElevatorButton>();

		for (int i = 0; i < numFloor; i++) {
			buttons.add(new ElevatorButton(i));
		}
	}
	

	/*
	 * This run method will set the information to the middleman as we try to update
	 * the middleman will the information This method will also update the current
	 * floor elevator is moving through.
	 */
	public void run() {
		while (true) {
			FloorEvent floorEvent = middleMan.getFloorEvent();
			if (floorEvent != null) {
				buttons.get(floorEvent.getDestination()).switchOn(true);

				int floors = floorEvent.getDestination() - floorEvent.getSource();
				direction = floors < 0 ? Direction.DOWN : Direction.UP;
				this.switchLamps(true);

				for (int i = 0; i < Math.abs(floors); i++) {
					if (direction == Direction.UP) {
						currentFloor++;
					} else {
						currentFloor--;
					}
					ArrivalEvent arrivalEvent = new ArrivalEvent(this.currentFloor, LocalTime.now(), direction, this);
					middleMan.putArrivalEvent(arrivalEvent);
				}

				buttons.get(this.currentFloor).switchOn(false);
				this.switchLamps(false);
			}
		}
	}
	
	/**
	 * Switch the direction lamps depending on direction we are moving in
	 * @param on True if lamp should be turned on, otherwise false
	 */
	private void switchLamps(boolean on) {
		if (direction == Direction.UP) {
			upLamp.switchOn(on);
		} else {
			downLamp.switchOn(on);
		}
	}

}
