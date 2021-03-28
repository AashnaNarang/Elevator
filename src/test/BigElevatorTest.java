package test;

import static org.junit.Assert.*;

import events.FloorEvent;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import main.Configurations;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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

  @Test
  public void fifthteenFloorEvents() {
    try {
      Elevator elevator1 = new Elevator(62, 72, 44, 121, 102);
      Elevator elevator2 = new Elevator(63, 73, 44, 121, 102);
      Elevator elevator3 = new Elevator(64, 74, 44, 121, 102);
      Elevator elevator4 = new Elevator(65, 75, 44, 121, 102);

      Scheduler scheduler = new Scheduler(35, 44, 121, 22, 102, 1);

      Thread floorSubsystemThread = new Thread(
          new FloorSubsystem("input11.txt", 22, 35), "floorSubsystem");
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

      assertEquals(15, scheduler.getNumOfProcessedEvents());
    } catch (Exception e) {
    }
  }

  @Test
  public void mixedFloorEvents() {
    try {
      Elevator elevator1 = new Elevator(58, 80, 43, 122, 103);
      Elevator elevator2 = new Elevator(59, 81, 43, 122, 103);
      Elevator elevator3 = new Elevator(60, 82, 43, 122, 103);
      Elevator elevator4 = new Elevator(61, 83, 43, 122, 103);

      Scheduler scheduler =
          new Scheduler(34, 43, 122, 21, 103, Configurations.TIMER_PORT);

      Thread floorSubsystemThread =
          new Thread(new FloorSubsystem("input12.txt", 21, 34));
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

      assertEquals(15, scheduler.getNumOfProcessedEvents());
    } catch (Exception e) {
    }
  }
}
