import java.time.LocalTime;

public class FloorEvent{
	
	private int source;
	private Direction direction;
	private LocalTime time;
	private int destination;

	public FloorEvent(LocalTime time, int source, Direction direction, int destination) {
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
	public LocalTime getTime() {
		return time;
	}
	public int getDestination() {
		return destination;
	}

}
