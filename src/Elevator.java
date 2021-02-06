<<<<<<< HEAD
import java.lang.Math;
import java.time.LocalTime;

public class Elevator implements Runnable {
  private int currentFloor;
  private boolean isStationary;
  private Middleman middleman;
  private Direction direction;
  private ArrayList<ElevatorButton> buttons;

  public Elevator(Middleman middleman) {
    this.middleman = middleman;
    isStationary = true;
=======
import java.sql.Time;
import java.util.*;

public class Elevator implements Runnable {
  private int currentFloor;
  //private boolean isStationary;
  private MiddleMan middleman;
  private Direction direction;
  private ArrayList<ElevatorButton> buttons;



  public Elevator(MiddleMan middleman) {
    this.middleman = middleman;
    //isStationary = true;
>>>>>>> 734bd3ace29235c42264383163dd272e1b20c24e
    currentFloor = 0;
    direction = Direction.UP;
    buttons = new ArrayList<ElevatorButton>();

   
	getNumFloors() {
		// TODO Auto-generated method stub
		return currentFloor;
	}
	
	public void clickbutton() {
		// runs the thread 
	}
	
	//I'm still unsure how to sync the information from scheduler.
	public void movement(Direction direction) {
		System.out.println(String.format("Elevator Moving in the direction ", direction.toString()));
    	//this is suppose to compare if the elevator is going up or down 
    	if (direction.equals(Direction.UP)) {
    	    if ((currentFloor == getNumFloors() - 1)) { //this basically gets if the button pressed in higher then current floor. Hence we return
    	        System.out.println(" What have you even selected honestly "); //
                System.exit(1);
    	    }
    	    
    		try  { 
                Thread.sleep(100);
            } catch (InterruptedException ie)  {

            }              
            
            currentFloor = currentFloor + 1;
    	} else if (direction.equals(Direction.DOWN)) {
    	    if (currentFloor == 0) {
    	        System.out.println(" You are not moving ");
                System.exit(1);
    	    }
    	    
    		try  { 
                Thread.sleep(100);
            } catch (InterruptedException ie)  {

            }              
            
            currentFloor = currentFloor - 1 ;
            //need to implement a method that will let us know when the elevator is arrives at the floor.
    	}else if (direction.equals(Direction.ArrivalSTOP)) {
            	if(currentFloor == currentFloor) {
            		System.out.println("The elevator is at floor requested => " + currentFloor);
            	}
        	    
        	    
        		try  { 
                    Thread.sleep(100);
                } catch (InterruptedException ie)  {

                }
            }
	}
	

    //Create an array of elevator buttons
    for(int i = 0;i < 10;i++) {
      buttons.add(new ElevatorButton());
    }
  }

<<<<<<< HEAD
  /*public void clickButton(int button) {
    buttons.get(button).click();

    //TODO Time, how long?
    scheduler.putEvent(new ElevatorEvent(this.currentFloor, button, new Time(), this);
  }*/
=======
  

public void clickButton(int button) {
    buttons.get(button).click();

    //TODO Time, how long?
    middleman.putEvent(new ArrivalEvent(this.currentFloor, button, new Time(button), this);
  }
>>>>>>> 734bd3ace29235c42264383163dd272e1b20c24e

  public void run() {
    FloorEvent floorevent = middleman.getFloorEvent();
    int floors = floorevent.getDestination() - floorevent.getSource();
    currentFloor += floors;

    ArrivalEvent arrivalEvent = new ArrivalEvent(this.currentFloor, LocalTime.now(), this);
    middleman.putArrivalEvent();


  }

}

	
