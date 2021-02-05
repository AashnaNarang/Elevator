
public class Floor implements Runnable {
	
	private int floorNumber;
	private FloorLamp directionLamp;
	private FloorButton upButton;
	private FloorButton downButton;
	
	public Floor(int floorNumber, FloorLamp directionLamp, 
			FloorButton upButton, FloorButton downButton) {
		this.floorNumber = floorNumber;
		this.directionLamp = directionLamp;
		this.upButton = upButton;
		this.downButton = downButton;
	}
	
	public void click() {
		
	}

	@Override
	public void run() {
		
	}
	
	

}
