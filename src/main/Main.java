package main;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		Thread floorSubsystem = new Thread(new FloorSubsystem("input4.txt", 6, 23, 33), "floorSubsystem");
		Thread sched = new Thread(new Scheduler(33, 43, 120, 23, 101), "scheduler");
		Thread elevator = new Thread(new Elevator(6, 63, 73, 43, 120, 101), "elevator");
		floorSubsystem.start();
		sched.start();
		elevator.start(); 
	}

}
