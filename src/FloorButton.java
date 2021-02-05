/**
 * FloorButton class which contains the direction that the elevator is going and the lamp status. 
 */
public class FloorButton {
	private Direction direction; 
	private Lamp lamp; 
	
	/**
	 * Constructor for FloorButton
	 * 
	 * @param direction - The direction of the elevator (UP/DOWN)
	 * @param lamp - Contains the status of the lamp for the elevator. 
	 */
	public FloorButton(Direction direction, Lamp lamp) {
		this.direction = direction; 
		this.lamp = lamp; 
	}
	
	/**
	 * Turns the lamp on/off based on the lamp status. 
	 * @param lampStatus - boolean which is true if the lamp is on, and off if false. 
	 */
	public void switchOn(boolean lampStatus) {
		lamp.switchLampStatus(lampStatus);
	}
}
