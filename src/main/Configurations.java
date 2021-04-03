package main;

/**
 * This class contains configurations required across the different classes.
 *
 */
public final class Configurations {

	/**
	 * Ensure that an instance of Configurations cannot be created.
	 */
	private Configurations() {
	}

	// Amount of time required for a socket timeout to occur
	public static final int TIMEOUT_MILLIS = 60000;

	public static final int FLOOR_EVENT_PORT = 33;

	public static final int ARRIVAL_PORT = 43;

	public static final int DEST_PORT = 120;

	public static final int FLOOR_PORT = 23;

	public static final int ELEVATOR_FLOOR_PORT = 63;

	public static final int ELEVATOR_SCHEDULAR_PORT = 73;
	
	public static final int TIMER_PORT = 130;

	public static final int ELEVATOR_STAT_PORT = 101;
	
	public static final int NUMBER_OF_FLOORS = 22;
	
	public static final int NUM_ELEVATORS = 4;

	// Amount of time that should occur between receiving a packet and sending
	// another
	// This can be set to 1000 if the code should run quickly
	public static final int SLEEP_DURATION_MILLIS = 1000;
	
	public static final int TIME_MOVING_BETWEEN_FLOOR = 1000;
	
	public static final int TIME_TO_LOAD_UNLOAD = 5000;
	

}
