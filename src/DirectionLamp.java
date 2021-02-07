
public class DirectionLamp {
	private Direction direction;
	private Lamp lamp;
	
	public DirectionLamp(Direction direction) {
		this.direction = direction;
		this.lamp = new Lamp();
		this.lamp.switchLampStatus(false);
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
		lamp.switchLampStatus(on);
	}


	@Override
	public String toString() {
		return "DirectionalLamp [direction=" + direction + "]";
	} 
}
