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
		this.lamp.switchLampStatus(false);
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

	/**
	 * Get the lamp's state
	 * @return True if the lamp is on, otherwise false
	 */
	public boolean getLampStatus() {
		return lamp.getLampStatus();
	}
}
