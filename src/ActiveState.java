import events.ArrivalEvent;
import events.FloorEvent;
import main.Scheduler;

public class ActiveState implements State{
	//will implement the state interface
	private Scheduler scheduler; 
	
	public ActiveState(Scheduler scheduler) {
		this.scheduler = scheduler; 
	}
	
	@Override
	public void handleFloorEvent(FloorEvent event) {
		scheduler.changeState(scheduler.getActiveState());
	}

	@Override
	public void handleArrivalEvent(ArrivalEvent event) {
		
	}
}
