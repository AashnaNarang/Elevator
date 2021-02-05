import java.util.Date;

public class FloorEvent{
	
	private int source;
	private Direction direction;
	private Date time;
	private int destination;

	public FloorEvent(Date time, int source, Direction direction, int destination) {
		this.time = time;
		this.source = source;
		this.direction = direction;
		this.destination = destination;

	}
	public int getSource() {
		return source;
	}
	public Direction getDirection() {
		return direction;
	}
	public Date getTime() {
		return time;
	}
	public int getDestination() {
		return destination;
	}
	

}
