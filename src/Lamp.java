/**
 * The Lamp class is responsible for keeping track of the lamp's (on/off)
 * status.
 */
public class Lamp {
	private boolean lampStatus;

	/**
	 * Sets the status of the lamp based on the boolean parameter.
	 * 
	 * @param lampStatus - boolean TRUE if the lamp is on, FALSE if its off.
	 */
	public void switchLampStatus(boolean lampStatus) {
		this.lampStatus = lampStatus;
	}

	/**
	 * Gets the lamp status.
	 * 
	 * @return boolean lampStatus.
	 */
	public boolean getLampStatus() {
		return lampStatus;
	}
}
