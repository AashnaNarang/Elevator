import events.ArrivalEvent;
import events.FloorEvent;
import main.Scheduler;

public class ActiveState extends SchedulerState{
	//will implement the state interface
	private Scheduler scheduler; 
	
	public ActiveState(Scheduler scheduler) {
		super(scheduler); 
		this.scheduler = scheduler; 
	}

	@Override
	public void handleFloorEvent(FloorEvent event) {
		scheduler.changeState(scheduler.getActiveState());
	}

	@Override
	public void handleArrivedFloor(ArrivalEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
