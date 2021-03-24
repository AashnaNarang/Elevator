package timers;

import main.Scheduler;

public class SchedulerTimer extends Thread {
	private Scheduler scheduler;
	private int numFloors;
	private boolean beforeArrivedAtSrcFloor;
	private int elevatorId;
	
	public SchedulerTimer(Scheduler scheduler, String name, boolean beforeArrivedAtSrcFloor, int elevatorId) {
		super(name);
		this.scheduler = scheduler;
		this.numFloors = 1;
		this.beforeArrivedAtSrcFloor = beforeArrivedAtSrcFloor;
		this.elevatorId = elevatorId;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Timer for elevator with ID " + elevatorId + " started with " + numFloors);
			Thread.sleep(numFloors * 200);
			System.out.println("Timer for elevator with ID " + elevatorId + " timed out");
			scheduler.permanentFault(beforeArrivedAtSrcFloor, elevatorId);
		} catch (InterruptedException e) {
			System.out.println("Timer for elevator with ID " + elevatorId + "cancelled");
		}
	}
	
	public void start(int numFloors) {
		this.numFloors = numFloors;
		this.start();
	}

}
