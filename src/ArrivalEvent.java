import java.time.LocalTime;

public class ArrivalEvent {
  int currentFloor;
  int destinationFloor;
  LocalTime time;
  Elevator elevator;
  /*
   * Constructor for Arrival event.
   *
   * @param currentFloor - The currentfloor it is on.
   * @param time - The time stamp the of the elevator
   * @param elevator - The elevator itself
   * @param desitinationFloor - The floor that is wants to reach
   */
  public ArrivalEvent(int currentFloor, LocalTime time, Elevator elevator,
                      int destinationFloor) {
    this.currentFloor = currentFloor;
    this.destinationFloor = destinationFloor;
    this.time = time;
    this.elevator = elevator;
  }

  /* getter method for time
   *
   * @return the current time.
   */
  public LocalTime getTime() { return time; }
  /*
   * getter method for Elevator
   *
   * @return the elevator that needs the arrivalevent info
   */
  public Elevator getElevator() { return elevator; }
  /*
   * getter method for currentFloor
   *
   * @return the currentfloor the elevator is at.
   */
  public int getCurrentFloor() { return currentFloor; }
}
