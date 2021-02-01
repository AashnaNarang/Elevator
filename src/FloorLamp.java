
public class FloorLamp {
	private boolean isOn;
	
	public FloorLamp(boolean isOn) {
		this.isOn = isOn; 
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	@Override
	public String toString() {
		return "FloorLamp [isOn=" + isOn + "]";
	} 
}
