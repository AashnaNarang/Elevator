package main;

public class Main {

	//This is seperate from the GUI
	public static void main(String[] args) {
		Thread floorSubsystem = new Thread(new FloorSubsystem("input7.txt", Configurations.FLOOR_PORT, Configurations.FLOOR_EVENT_PORT), "floorSubsystem");
		Thread sched = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT
				, Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT, Configurations.TIMER_PORT), "scheduler");
		Thread elevator0 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT, 
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator0");
		Thread elevator1 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1, 
				Configurations.ELEVATOR_SCHEDULAR_PORT + 1,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator1");
		Thread elevator2 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 2, 
				Configurations.ELEVATOR_SCHEDULAR_PORT + 2,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator2");
		Thread elevator3 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 3, 
				Configurations.ELEVATOR_SCHEDULAR_PORT + 3,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator3");
		floorSubsystem.start();
		sched.start();
		elevator0.start();
		elevator1.start();
		elevator2.start();
		elevator3.start();
	}

}
