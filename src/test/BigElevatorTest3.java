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

public class BigElevatorTest3 {

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
