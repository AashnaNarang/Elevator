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
		if (count > numRequests) {
			// this realistically won't be the case, but just in case if recordEvent gets called more times 
			// than there are events, we should throw an error because this means the system is in a weird state
			throw new IllegalStateException("Mystery request was serviced");
		}
		System.out.println("Incremented count to " + count);
		if (count == numRequests) {
			// if we've reached the last event in the input file, we must collect the end time
			System.out.println("Got end time");
			Timing.endTime = System.nanoTime();
		}
	}
	
	public static String getTimingInfo() {
		if (endTime == -1) {
			// this means we haven't collected an end time yet, so no timing info is available
			return null;
		}
		if (numRequests < 1) {
			// this means num requests was never set (or set to 0) and we cannot generate good timing info
			throw new IllegalStateException("In bad state, num requests must be above or equal to 1");
		}
		long timeElapsed = endTime - startTime;
		// mean time per event is calculated by dividing the total time elapsed by numRequests because since we have
		// a multi-threaded real time system, events aren't always finished in a synchronous fashion
		// so to ensure a single responsibility, to lower coupling, and reduce space complexity, we calculated it this way
		long avgTimePerEvent = timeElapsed/numRequests;
		String s = "It took a total of " + timeElapsed + " nanoseconds to service " + numRequests + " events. The mean time per event is " + avgTimePerEvent + " nanoseconds.";
		reset();
		return s;
	}
	
	/**
	 * Reset the instance variables so that the timer can be used again if needed
	 */
	private static void reset() {
		endTime = -1;
		startTime = 0;
		count = 0;
		numRequests = -1;
	}
	
	
}
