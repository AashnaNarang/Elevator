package main;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		Thread floorSubsystem = new Thread(new FloorSubsystem("input2.txt", 6, 23, 33), "floorSubsystem");
		Thread sched = new Thread(new Scheduler(33, 43, 53, 23, 63), "scheduler");
		Thread elevator = new Thread(new Elevator(6, 63, 43, 53), "elevator");
		floorSubsystem.start();
		sched.start();
		elevator.start(); 
		
	}

}
