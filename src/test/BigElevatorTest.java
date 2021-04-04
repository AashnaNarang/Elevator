package test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

public class BigElevatorTest {

  @Test
  public void basicScenario() {
    try {
      Elevator elevator1 = new Elevator(66, 76, 46, 123, 104);
      Elevator elevator2 = new Elevator(67, 77, 46, 123, 104);
      Elevator elevator3 = new Elevator(68, 78, 46, 123, 104);
      Elevator elevator4 = new Elevator(69, 79, 46, 123, 104);

      Scheduler scheduler = new Scheduler(36, 46, 123, 26, 104, 3);

      Thread floorSubsystemThread = new Thread(
          new FloorSubsystem("input10.txt", 26, 36), "floorSubsystem");
      Thread schedThread = new Thread(scheduler, "scheduler");

      Thread elevatorThread1 = new Thread(elevator1, "elevator1");
      Thread elevatorThread2 = new Thread(elevator2, "elevator2");
      Thread elevatorThread3 = new Thread(elevator3, "elevator3");
      Thread elevatorThread4 = new Thread(elevator4, "elevator4");

      floorSubsystemThread.start();
      schedThread.start();

      elevatorThread1.start();
      elevatorThread2.start();
      elevatorThread3.start();
      elevatorThread4.start();
      TimeUnit.SECONDS.sleep(150);

      assertEquals(4, scheduler.getNumOfProcessedEvents());

    } catch (Exception e) {
    }
  }

}
