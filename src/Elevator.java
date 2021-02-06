import java.lang.Math;
import java.time.LocalTime;
import java.util.ArrayList;

/*
 * The Elevator class is designed so that it takes the task form the middleman
 * and moves the elevator up/down based on the given task it will receive.
 */

public class Elevator implements Runnable {
  private int currentFloor;
  int numFloor;
  private MiddleMan middleMan;
  private Direction direction;
  private ArrayList<ButtonLamp> buttons;

  /* constructor for Elevator
   * Defining the middleclass parameters that are by to the scheduler.
   *
   * @param middleman - sending information to Middleman
   */
  public Elevator(MiddleMan middleMan, int numFloor) {
    this.middleMan = middleMan;
    currentFloor = 0;
    direction = Direction.UP;
    buttons = new ArrayList<ButtonLamp>();

    for (int i = 0; i < numFloor; i++) {
      buttons.add(new ButtonLamp());
    }
  }

  /*
   * This run method will set the information to the middleman as we try to
   * update the middleman will the infomation This methond will also update the
   * current floor elevator is moving through.
   */
  public void run() {
    while (true) {
      FloorEvent floorEvent = middleMan.getFloorEvent();
      if (floorEvent != null) {
        buttons.get(floorEvent.getDestination()).click();

        boolean directionUp = true;
        int floors = floorEvent.getDestination() - floorEvent.getSource();
        if (floors < 0) {
          directionUp = false;
        }

        for (int i = 0; i < Math.abs(floors); i++) {
          if (directionUp) {
            currentFloor++;
          } else {
            currentFloor--;
          }
          ArrivalEvent arrivalEvent =
              new ArrivalEvent(this.currentFloor, LocalTime.now(), this);
          middleMan.putArrivalEvent(arrivalEvent);
        }
      }
      buttons.get(this.currentFloor).click();
    }
  }
}
