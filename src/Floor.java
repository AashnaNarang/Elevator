/**
 * The floor class is responsible for tracking the floor number and also toggling the lights for the up/down buttons. 
 * @author danih
 *
 */
public class Floor {
	private int floorNumber;
	private FloorButton upButton;
	private FloorButton downButton;
	
	/**
	 * Constructor for Floor. 
	 * @param floorNumber - The current floor of the elevator. 
	 */
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	/**
	 * Turns on the Lamp depending on the direction of the elevator. 
	 * If the elevator is going up, then the lamp will turn on the up lamp. 
	 * @param direction
	 * @param lampStatus
	 */
	public void turnButtonOn(Direction direction, boolean lampStatus) {
		if(direction == Direction.UP){
			upButton.switchOn(lampStatus);
		}
		else {
			downButton.switchOn(lampStatus);
		}
	}
	
	/**
	 * Getter method for the floor number
	 * @return the current floor number of the elevator. 
	 */
	public int getFloorNumber() {
		return floorNumber;
	}

}
