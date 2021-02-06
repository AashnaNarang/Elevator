import java.time.LocalTime;

public class ArrivalEvent() {
  int currentFloor;
  LocalTime time;
  Elevator elevator;

  public ArrivalEvent(int currentFloor, LocalTime time, Elevator elevator) {
    this.currentFloor = currentFloor;
    this.time = time;
    this.elevator = elevator;
  }

  public LocalTime getTime() {
    return time;
  }

  public Elevator getElevator() {
    return elevator;
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

}
