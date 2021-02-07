# Elevator

## ArrivalEvent.java 
Is an event that happens when an elevator arrives at a floor. 

## Direction.java
Enum containing UP and DOWN directions.

## DirectionLamp.java
Is a class representing the lamp that contains either an up or down status. 

## Elevator.java 
The Elevator class is designed so that it takes the task form the middleman and moves the elevator up/down based on the given task it will receive. 

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
This class is responsible for connecting the floor to scheduler, scheduler to elevator, and vise-versa. 

## Scheduler.java 
Represents the scheduler which checks for new updates and sends to the elevator once there is something new. Also sends data to the floor when the elevator completes a job. 


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