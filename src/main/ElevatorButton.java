package main;
/**
 * This class represents the buttons in the elevator. 
 *
 */
public class ElevatorButton {

	private Lamp lamp;
	private int floorNumber;

	/**
	 * Constructor for the ElevatorButton class. 
	 * @param floorNumber - the floor number the user presses in the elevator. 
	 */
	public ElevatorButton(int floorNumber) {
		this.lamp = new Lamp();
		this.lamp.switchLampState(false);
		this.floorNumber = floorNumber;
	}

	/**
	 * Switch the lamp on or off
	 * @param on True if lamp should be on, otherwise false
	 */
	public void switchOn(boolean on) {
		lamp.switchLampState(on);
	}
	
	/**
	 * Get the button's floor number
	 * @return
	 */
	public int getFloorNumber() {
		return this.floorNumber;
	}
	
	/**
	 * Get the lamps state
	 * @return True if lamp is on or false if it is off
	 */
	public boolean getLampStatus() {
		return lamp.getLampState();
	}

}
