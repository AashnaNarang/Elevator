/**
 * The Lamp class is responsible for keeping track of the lamp's (on/off)
 * status.
 */
public class Lamp {
	private boolean lampState;

	/**
	 * Sets the status of the lamp based on the boolean parameter.
	 * 
	 * @param lampState - boolean TRUE if the lamp is on, FALSE if its off.
	 */
	public void switchLampState(boolean lampState) {
		this.lampState = lampState;
	}

	/**
	 * Gets the lamp status.
	 * 
	 * @return boolean lampStatus.
	 */
	public boolean getLampState() {
		return lampState;
	}
}
