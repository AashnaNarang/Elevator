# Elevator

## ArrivalEvent.java 
Is an event that holds information when an elevator arrives at a floor. 

## Event.java
The class has the local time and destination information of a person clicking a button in a elevator.

## Direction.java
Enum containing UP and DOWN directions.

## DirectionLamp.java
Is a class representing the lamp that contains either an up or down status. 

## Elevator.java 
The Elevator class is designed so that it takes the task from the MiddleMan and moves the elevator up/down based on the given task it will receive. 

## ElevatorButton.java
This class represents the buttons in the elevator. 

## Floor.java 
The floor class is responsible for tracking the floor number and also toggling the lights for the up/down buttons.

## FloorButton.java 
This class represents the button pressed on the floor for the elevator. 

## FloorEvent.java
The FloorEvent Class handles the request being sent to scheduler it takes in the parsed information from the input and creates an event.

## FloorSubsystem.java 
Parses events and processes the information from the events, sending floorEvents and taking arrival events from scheduler.

## Lamp.java
The Lamp class is responsible for keeping track of the lamp's (on/off) status.

## MiddleMan.java 
This class is responsible for connecting the floor to scheduler, scheduler to elevator, and vice-versa. 

## Scheduler.java 
Represents the scheduler which checks for new updates and sends to the elevator once there is something new. Also sends data to the floor when the elevator completes a job. The scheduler is also responsible for checking if the floor event and destination event should stop the Elevator.

## SchedulerEvent.java
This class represents the information being sent to elevator to either stop or have the elevator keepgoing.

## DoorOpenState.java
This class will handle the opening and closing of the door with a given expiry time. This will also make it so that when the elevator is in moving state it will not open the doors. 

## MovingState.java
This class represents that the elevator is moving towards its destination location and update the elevator and the scheduler. It will also receive from the floor event if it should continue moving through the floors. 

## ElevatorState.java
This class represents the information on which state the elevator is in. 

## StationaryState.java
This class is the representation of the state in which the elevator will have to decide what to do when the elevator has reached the destination or when it's at a stand still.

## IdleState.java
This class represents when the Scheduler is at a standstill and wait for floor event and arrival event to pass in events/information.

## ActiveState.java
It puts FloorEvents, DestinationEvents and ArrivalEvents into lists and moves to IdleState when they are empty. 

## TestElevatorSystem
This class will test the behavior of our elevator and display in the console the messages of what the elevator is currently doing. 

## Instructions for running the file using the ZIP folder: 
Make sure you extract the zip folder first. 
1. Open eclipse
2. Right click anywhere in the package explorer on the left
3. Click import
4. General
5. Existing projects into workplace
6. Select the folder. 
7. Run the java application using Main.java. 

## Instructions for running the file using git:
1. Open the git project
2. Click on the green button "Code"
3. Download the ZIP folder.  
4. Use steps 1-7 in the instructions shown [Here](#Instructions-for-running-the-file-using-the-ZIP-folder:). 

## Instructions for testing
1. Go to TestElevatorSystem.java inside src 
2. Click on the Run as Junit test
