public class ElevatorButton {
  FloorLamp lamp;

  public ElevatorButton() {
    lamp = new FloorLamp(false);
  }

  public void click() {
    if(lamp.isOn()) {
      lamp.setOn(false);
    }
    else {
      lamp.setOn(true);
    }
  }
}
