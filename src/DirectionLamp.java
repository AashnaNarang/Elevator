
public class DirectionLamp {
	private Direction direction;
	private Lamp lamp;
	
	/**
	 * 
	 * @param direction
	 */
	public DirectionLamp(Direction direction) {
		this.direction = direction;
		this.lamp = new Lamp();
		this.lamp.switchLampState(false);
	}

	/**
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
	 * Get the lamp's state
	 * @return True if the lamp is on, otherwise false
	 */
	public boolean getLampState() {
		return lamp.getLampState();
	}


	@Override
	public String toString() {
		return "DirectionalLamp [direction=" + direction + "]";
	} 
}
