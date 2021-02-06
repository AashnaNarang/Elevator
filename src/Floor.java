/**
 * The floor class is responsible for tracking the floor number and also
 * toggling the lights for the up/down buttons.
 *
 */
public class Floor {
	private int floorNumber;
	private FloorButton upButton;
	private FloorButton downButton;

	/**
	 * Constructor for Floor.
	 * 
	 * @param floorNumber - The current floor of the elevator.
	 */
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		upButton = new FloorButton(Direction.UP); 
		downButton = new FloorButton(Direction.DOWN);
	}

	/**
	 * Turns on the Lamp depending on the direction of the elevator. If the elevator
	 * is going up, then the lamp will turn on the up lamp.
	 * 
	 * @param direction - The direction that the elevator is going to
	 * @param buttonStatus - The status of the lamps in the elevator. 
	 */
	public void switchButton(Direction direction, boolean buttonStatus) {
		if (direction == Direction.UP) {
			upButton.switchOn(buttonStatus);
		} else {
			downButton.switchOn(buttonStatus);
		}
	}

	/**
	 * Getter for the upButton lampStatus
	 * 
	 * @return true if lamp is on
	 */
	public boolean isUpButtonOn() {
		return upButton.getLampStatus();
	}

	/**
	 * Getter for the downButton lampStatus
	 * 
	 * @return true if lamp is on
	 */
	public boolean isDownButtonOn() {
		return downButton.getLampStatus();
	}

	/**
	 * Getter method for the floor number
	 * 
	 * @return the current floor number of the elevator.
	 */
	public int getFloorNumber() {
		return floorNumber;
	}

}
