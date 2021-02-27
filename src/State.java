import events.ArrivalEvent;
import events.FloorEvent;

public interface State {
	
	void handleFloorEvent(FloorEvent event); 
	void handleArrivalEvent(ArrivalEvent event); 
}
