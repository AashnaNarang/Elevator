/**
 * FloorButton class which contains the direction that the elevator is going and
 * the lamp status.
 */
public class FloorButton {
	private Direction direction;
	private Lamp lamp;

	/**
	 * Constructor for FloorButton
	 * 
	 * @param direction - The direction of the elevator (UP/DOWN)
	 */
	public FloorButton(Direction direction) {
		this.direction = direction;
		this.lamp = new Lamp(); 
	}

	/**
	 * Turns the lamp on/off based on the lamp status.
	 * 
	 * @param lampStatus - boolean which is true if the lamp is on, and off if
	 *                   false.
	 */
	public void switchOn(boolean lampStatus) {
		lamp.switchLampStatus(lampStatus);
	}

	public boolean getLampStatus() {
		return lamp.getLampStatus();
	}
}
