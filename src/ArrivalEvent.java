import java.sql.Time;

public class ArrivalEvent {
  int currentFloor;
  int destinationFloor;
  Time time;
  Elevator elevator;

  public ArrivalEvent(int currentFloor, int destinationFloor, Time time, Elevator elevator) {
    this.currentFloor = currentFloor;
    this.destinationFloor = destinationFloor;
    this.time = time;
    this.elevator = elevator;
  }
}


