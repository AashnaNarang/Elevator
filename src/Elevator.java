public class Elevator implements Runnable {
  private int currentFloor;
  private boolean isStationary;
  private Scheduler scheduler;
  private Direction direction;
  private ArrayList<ElevatorButton> buttons;

  public Elevator(Scheduler scheduler) {
    this.scheduler = scheduler;
    isStationary = true;
    currentFloor = 0;
    direction = Direction.UP;
    buttons = new ArrayList<ElevatorButton>();

    //Create an array of elevator buttons
    for(int i = 0;i < 10;i++) {
      buttons.add(new ElevatorButton());
    }
  }

  public void clickButton(int button) {
    buttons.get(button).click();

    //TODO Time, how long?
    scheduler.putEvent(new ElevatorEvent(this.currentFloor, button, new Time(), this);
  }

  public void run() {

  }
}
