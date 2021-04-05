package timers;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalTime;

import events.TimeoutEvent;
import main.Configurations;
import main.Elevator;
import main.NetworkCommunicator;
import main.Scheduler;
import main.Serial;
/**
 * declaration of instance variables
 */
public class ElevatorTimer implements Runnable {
	private Elevator elevator;
	private Thread t;
	/**
	 * Elevator constructor to intialize instance variables
	 * @param elevator
	 */
	public ElevatorTimer(Elevator elevator) {
		this.elevator = elevator;
		t = new Thread(this, "ElevatorTimer " + elevator.getId());
	}
	
	/**
	 * Run the method so we can print he statements for timer which has either started, timed out, or cancelled 
	 */
	@Override
	public void run() {
		try {
			System.out.println("Timer for Door with ID " + elevator.getId() + " started" + ".  {Time: " + LocalTime.now() + "}");
			Thread.sleep((long) (Configurations.TIME_TO_LOAD_UNLOAD * 1.5));
			System.out.println("Timer for Door with ID " + elevator.getId() + " timed out" + ".  {Time: " + LocalTime.now() + "}");
			elevator.setDidTimeout(true);
			elevator.setDoorsOpen(true);
		} catch (InterruptedException e) {
			System.out.println("Timer for Door with ID " + elevator.getId() + " cancelled" + ".  {Time: " + LocalTime.now() + "}");
		}
	}
	
	/**
	 * start the timer
	 */
	public void start() {
		t.start();
	}
	
	/**
	 * stop/cancel the timer
	 */
	public void cancel() {
		t.interrupt();
	}
	
}
