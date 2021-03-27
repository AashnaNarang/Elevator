package timers;

import java.net.DatagramSocket;
import java.net.SocketException;

import events.TimeoutEvent;
import main.Elevator;
import main.NetworkCommunicator;
import main.Scheduler;
import main.Serial;

public class ElevatorTimer implements Runnable {
	private Elevator elevator;
	private Thread t;
	
	public ElevatorTimer(Elevator elevator) {
		this.elevator = elevator;
		t = new Thread(this, "ElevatorTimer " + elevator.getId());
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Timer for Door with ID " + elevator.getId() + " started.");
			Thread.sleep(750);
			System.out.println("Timer for Door with ID " + elevator.getId() + " timed out");
			elevator.setDidTimeout(true);
			elevator.setDoorsOpen(true);
		} catch (InterruptedException e) {
			System.out.println("Timer for Door with ID " + elevator.getId() + " cancelled");
		}
	}
	
	public void start() {
		t.start();
	}
	
	public void cancel() {
		t.interrupt();
	}

}
