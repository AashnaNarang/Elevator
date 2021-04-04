package main;


public class Timing {
	private static long startTime = 0;
	private static long endTime = -1;
	private static int numRequests = -1;
	private static int count = 0;

	/**
	 * @return the number of requests 
	 */
	public static int getNumRequests() {
		return numRequests;
	}

	/**
	 * @param numRequests the number of requests to set
	 */
	public static void setNumRequests(int numRequests) {
		System.out.println("Setting num requests " + numRequests);
		Timing.numRequests = numRequests;
	}
	
	/**
	 * Record the starting time
	 */
	public static void startTime() {
		if (numRequests == -1) {
			throw new IllegalStateException("Num requests has not been set yet");
		}
		System.out.println("Starting timer");
		startTime = System.nanoTime();
	}
	
	/**
	 * Increment the number of times an event has been services. If count is
	 * at number of requests in the input file, then record the end time
	 */
	public static void recordEvent() {
		count++;
		System.out.println("Incremented count to " + count);
		if (count == numRequests) {
			System.out.println("Got end time");
			Timing.endTime = System.nanoTime();
		}
	}
	
	public static String getTimingInfo() {
		if (endTime == -1) {
			return null;
		}
		if (numRequests == -1) {
			throw new IllegalStateException("In bad state, num requests was never set");
		}
		long timeElapsed = endTime - startTime;
		long avgTime = timeElapsed/numRequests;
		String s = "It took a total of " + timeElapsed + " nanoseconds to service " + numRequests + " events. The mean time per event is " + avgTime + " nanoseconds.";
		reset();
		return s;
	}
	
	private static void reset() {
		endTime = -1;
		startTime = 0;
		count = 0;
		numRequests = -1;
	}
	
	
}
