package timers;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalTime;

import events.TimeoutEvent;
import main.NetworkCommunicator;
import main.Scheduler;
import main.Serial;

/**
 * declaration of instance variables
 */
public class SchedulerTimer extends NetworkCommunicator implements Runnable {
	private Scheduler scheduler;
	private int numFloors;
	private boolean beforeArrivedAtSrcFloor;
	private int elevatorId;
	private int timerPort;
	private DatagramSocket sendTimerSocket;
	private Thread t;
	/**
	 * Scheduler constructor to intialize instance variables 
	 * @param scheduler
	 * @param name
	 * @param beforeArrivedAtSrcFloor
	 * @param elevatorId
	 * @param timerPort
	 */
	public SchedulerTimer(Scheduler scheduler, String name, boolean beforeArrivedAtSrcFloor, int elevatorId, int timerPort) {
		this.scheduler = scheduler;
		this.numFloors = 1;
		this.beforeArrivedAtSrcFloor = beforeArrivedAtSrcFloor;
		this.elevatorId = elevatorId;
		this.timerPort = timerPort;
		t = new Thread(this, "SchedulerTimer " + elevatorId);
		try {
			this.sendTimerSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Run the method and get back the print statemensts for elevator where the timer starts with, timed out, and cancelled.
	 */
	@Override
	public void run() {
		try {
			System.out.println("Timer for elevator with ID " + elevatorId + " started with " + numFloors + ".  {Time: " + LocalTime.now() + "}");
			Thread.sleep(numFloors * 10000);
			System.out.println("Timer for elevator with ID " + elevatorId + " timed out" + ".  {Time: " + LocalTime.now() + "}");
			byte[] data = Serial.serialize(new TimeoutEvent(elevatorId, beforeArrivedAtSrcFloor));
			send(sendTimerSocket, data, data.length, timerPort);
			// send UDP to scheduler? but that would cause a delay
		} catch (InterruptedException e) {
			System.out.println("Timer for elevator with ID " + elevatorId + " cancelled" + ".  {Time: " + LocalTime.now() + "}");
		}
	}
	
	/**
	 * start the number of floors
	 * @param numFloors
	 */
	public void start(int numFloors) {
		this.numFloors = numFloors;
		t.start();
	}
	
	/**
	 * cancel causing interrupt 
	 */
	public void cancel() {
		t.interrupt();
	}

}
