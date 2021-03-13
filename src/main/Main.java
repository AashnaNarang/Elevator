package main;

public class Main {

	public static void main(String[] args) {
		Thread floorSubsystem = new Thread(new FloorSubsystem("input4.txt", 4, 23, 33), "floorSubsystem");
		Thread sched = new Thread(new Scheduler(33, 43, 120, 23, 101), "scheduler");
		Thread elevator = new Thread(new Elevator(6, 63, 73, 43, 120, 101), "elevator");
		Thread elevator2 = new Thread(new Elevator(6, 64, 74, 43, 120, 101), "elevator2");
		floorSubsystem.start();
		sched.start();
		elevator.start();
		elevator2.start();
	}

}
