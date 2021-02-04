public class ElevatorEvent() {
  int currentFloor;
  int destinationFloor;
  Time time;
  Elevator elevator;

  public ElevatorEvent(int currentFloor, int destinationFloor, Time time, Elevator elevator) {
    this.currentFloor = currentFloor;
    this.destinationFloor = destinationFloor;
    this.time = time;
    this.elevator = elevator;
  }
}
