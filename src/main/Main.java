package main;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		MiddleMan middleMan1 = new MiddleMan();
		MiddleMan middleMan2 = new MiddleMan();
		Thread floorSubsystem = new Thread(new FloorSubsystem("input5.txt", 6, middleMan1), "floorSubsystem");
		Thread sched = new Thread(new Scheduler(middleMan1, middleMan2), "scheduler");
		Thread elevator = new Thread(new Elevator(middleMan2, 6), "elevator");
		floorSubsystem.start();
		sched.start();
		elevator.start();
//		try {
//			TimeUnit.SECONDS.sleep(3);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.exit(0);
		
	}

}
