package main;

public class ElevatorMain {
	public static void main(String[] args) {
		MiddleMan middleMan1 = new MiddleMan();
		Thread elevator = new Thread(new Elevator(middleMan1, 6), "elevator");
		elevator.start();
	}
}
