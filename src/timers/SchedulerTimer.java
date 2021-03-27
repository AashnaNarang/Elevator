package timers;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalTime;

import events.TimeoutEvent;
import main.NetworkCommunicator;
import main.Scheduler;
import main.Serial;

public class SchedulerTimer extends NetworkCommunicator implements Runnable {
	private Scheduler scheduler;
	private int numFloors;
	private boolean beforeArrivedAtSrcFloor;
	private int elevatorId;
	private int timerPort;
	private DatagramSocket sendTimerSocket;
	private Thread t;
	
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
	
	public void start(int numFloors) {
		this.numFloors = numFloors;
		t.start();
	}
	
	public void cancel() {
		t.interrupt();
	}

}
