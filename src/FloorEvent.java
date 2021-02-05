import java.awt.Event;
import java.util.Date;

public class FloorEvent extends Event{
	
	private int Floor;
	private Direction direction;
	private Date time;

	public FloorEvent(Object arg0, int arg1, Object arg2) {
		super(arg0, arg1, arg2);
		// TODO Auto-generated constructor stub
	}
	public int getFloor() {
		return Floor;
	}
	public Direction getDirection() {
		return direction;
	}
	public Date getTime() {
		return time;
	}
	

}
