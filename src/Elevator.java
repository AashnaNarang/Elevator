import java.util.*;


public class Elevator implements Runnable{
	
	private boolean isStationary;
	private Scheduler scheduler;
	private int currentFloor;
	private Direction direction;
	private Arraylist<Elevatorbutton> requestbutton;
	
	//I might mremove this...
	/*
	public void FloorLamp(boolean isStationary) {
		currentFloor = 0;
		this.isStationary = isStationary; 
	}
	*/
	//this basically calls the scheduler with all the infomation on the buttons and movements. 
	public Elevator(Scheduler Scheduler) {
	requestbutton = new Arraylist<Elevatorbutton>();
    this.scheduler = scheduler;
	}
	
	private int getNumFloors() {
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
    	if (direction.equals(Elevator.UP)) {
    	    if ((currentFloor == getNumFloors() - 1)) { //this basically gets if the button pressed in higher then current floor. Hence we return
    	        System.out.println(" What have you even selected honestly "); //
                System.exit(1);
    	    }
    	    
    		try  { 
                Thread.sleep(100);
            } catch (InterruptedException ie)  {

            }              
            
            currentFloor++;
    	} else if (direction.equals(Elevator.DOWN)) {
    	    if (currentFloor == 0) {
    	        System.out.println(" You are not moving ");
                System.exit(1);
    	    }
    	    
    		try  { 
                Thread.sleep(100);
            } catch (InterruptedException ie)  {

            }              
            
            currentFloor--;
	}
	
}

	
