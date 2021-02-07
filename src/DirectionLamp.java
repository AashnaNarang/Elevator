/**
 * This class represents the direction lamp in the elevator (UP/DOWN arrow).
 *
 */
public class DirectionLamp {
	private Direction direction;
	private Lamp lamp;
	
	/**
	 * Constructor for the DirectionLamp
	 * @param direction - UP/DOWN direction of the elevator. 
	 */
	public DirectionLamp(Direction direction) {
		this.direction = direction;
		this.lamp = new Lamp();
		this.lamp.switchLampState(false);
	}

	/**
	 * Getter method for direction. 
	 * @return the direction
	 */
	public Direction getDirection() {
		return direction;
	}

	
	/**
	 * Switch the lamp on or off
	 * @param on True if lamp should be on, otherwise false
	 */
	public void switchOn(boolean on) {
		lamp.switchLampState(on);
	}

	/**
	 * String representation of the direction lamp
	 */
	@Override
	public String toString() {
		return "DirectionalLamp [direction=" + direction + " on=" + lamp.getLampState() + "]";
	}
	
	/**
	 * Get the lamp's state
	 * @return True if the lamp is on, otherwise false
	 */
	public boolean getLampState() {
		return lamp.getLampState();
	}
}
