package timers;


public class SchedulerTimer extends Thread {
	
	public SchedulerTimer(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Timer started");
			Thread.sleep(5000);
			System.out.println("Timer finished");
		} catch (InterruptedException e) {
			System.out.println("Timer cancelled");
		}
	}

}
