/*
 * This class is desigined for the buttons of the elevator.
 */
public class ButtonLamp {
  FloorLamp lamp;
  /*
   * Basically this will keep lamp of the button off
   *
   * @param
   */
  public ButtonLamp() { lamp = new FloorLamp(); }
  /*
   * When the button is clicked the lamp will turn on/off
   *
   * @param
   */
  public void click() {
    lamp.switchLampStatus(!this.getLampStatus());
  }
}
