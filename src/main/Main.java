package main;

public class Main {

	public static void main(String[] args) {
		Thread floorSubsystem = new Thread(new FloorSubsystem("input7.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT), "floorSubsystem");
		Thread sched = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT
				, Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT, Configurations.TIMER_PORT), "scheduler");
		Thread elevator = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT, 
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator");
		Thread elevator2 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1, 
				Configurations.ELEVATOR_SCHEDULAR_PORT + 1,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator2");
		floorSubsystem.start();
		sched.start();
		elevator.start();
		elevator2.start();
	}

}
