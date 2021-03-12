package main;

public class FloorMain {
	public static void main(String[] args) {
		MiddleMan middleMan1 = new MiddleMan();
		Thread floorSubsystem = new Thread(new FloorSubsystem("input5.txt", 6, middleMan1), "floorSubsystem");
		floorSubsystem.start();
	}
}
