# Elevator
***Note*** This is the same file as the README.txt, but in markdown format

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

## FloorEvent.java
The FloorEvent Class handles the request being sent to scheduler it takes in the parsed information from the input and creates an event.

## FloorSubsystem.java 
Parses events and processes the information from the events, sending floorEvents and taking arrival events from scheduler.

## Lamp.java
The Lamp class is responsible for keeping track of the lamp's (on/off) status.

## Main.java
This main class is to basically start all our diffrent threads. 

## Scheduler.java 
Represents the scheduler which checks for new updates and sends to the elevator once there is something new. Also sends data to the floor when the elevator completes a job. The scheduler is also responsible for checking if the floor event and destination event should stop the Elevator.

## SchedulerEvent.java
This class represents the information being sent to elevator to either stop or have the elevator keepgoing.

##StationaryEvent.java
This class will handle when the stationary events are required. I will message, floorport, elevatorid, and The current floor. 

##TimeoutEvent.java
This class will handle the timeout events where it will provide the elevator id, and the status before it arrived at the source floor.

## DoorOpenState.java
This class will handle the opening and closing of the door with a given expiry time. This will also make it so that when the elevator is in moving state it will not open the doors. 

## MovingState.java
This class represents that the elevator is moving towards its destination location and update the elevator and the scheduler. It will also receive from the floor event if it should continue moving through the floors. 

## ElevatorState.java
This class represents the information on which state the elevator is in. This class will also update the Middleman with what floor the elevator is currently moving through. 

## StationaryState.java
This class is the representation of the state in which the elevator will have to decide what to do when the elevator has reached the destination or when it's at a stand still.

## IdleState.java
This class represents when the Scheduler is at a standstill and wait for floor event and arrival event to pass in events/information.

## ActiveState.java
It puts FloorEvents, DestinationEvents and ArrivalEvents into lists and moves to IdleState when they are empty. 

##SchedulerState.java
This class will get the infomation which state the scheduler is moving into.

## Serial.java
This class is a method for the information to be serialize and deserialize.

## NetworkCommunicator.java
This class will send the datagram packets in a byte array format to a destination port. This is all done using UDP.

##InvalidRequestException.java
This class is strictly designed so the the scheduler will not allow any invalid commands to elevator.

##ElevatorTimer.java
This class will provide us with the timer for the Elevator where it will provide us with time elevator started with, if the timer timed out or if the timer got cancelled.

##SchedulerTimer.java
This class will provide us with the timer for the scheduler where it will provide us with time scheduler started with, if the timer timed out or if the timer got cancelled.

## Work Breakdown for iteration 4
## Everyone 
Design how to handle permanent and transient faults 

## Aashna
Build timer class (SchedulerTimer.java and TimeoutEvent.java). 
Implement permanent fault (error code 2). 
Help fix failing test cases. 
Fix various bugs in the code. 
Clean up print statements. 
Debug issues appearing with 4 elevators. 

## Krishan
Implement transient fault (error code 1). 
Add usage of elevator Ids to Floor Event class. 
Debug issues appearing with 4 elevators. 

## Abdalla
Alter the system to parse error codes from text files. 
Add code in scheduler to thrown an exception/shut down system if the scheduler is about to tell the elevator to make an illegal move. 
Test cases. 

## Dani
Iteration 4 JUnit test cases.

## Issac 
JUnit Test cases for 4 elevators and 22 floors. 

## Yuvraj
ReadMe file. 
javadoc

## Work Breakdown for iteration 3
## Aashna
Fix bugs from iteration 2, 
Re-design scheduler state machine, 
Small update to the scheduler state diagram, 
Work with Krishan to design how UDP communication will work, 
Fix bug after switching to UDP,  
Work with Krishan on design for multiple elevators, 
Implement design for multiple cars (Elevator sends stationary events, fix elevator keeps going), 
Clean up print statements, 
Clean up javadocs
 
## Krishan
Work with Aashna to design how UDP communication will work, 
Implement UDP communication, 
Work with Aashna on design for multiple elevators, 
Implement design for multiple cars (Rest of implementation), 
Clean up javadocs

## Dani
IterationOneTest.java, 
IterationTwoTest.java, 
TestScenarios.java, 
Class Diagram,  
Sequence Diagram, 
Fix broken test cases after changes

## Abdalla
IterationOneTest.java, 
IterationTwoTest.java, 
IterationThreeTest.java, 
Configurations.java, 
Fix broken test cases after changes

## Yuvraj
Add meaningful print statements, 
Readme

## Issac 
Work with Krishan on UDP implementation 


## Work Breakdown for iteration 2
## Team
Design state diagram together + design basic idea of classes, 
Add new classes to UML that each person creates,  
Debug code

## Aashna
Implement State pattern,
Design Elevator+State pattern in more detail,
Merge + Test code together, 
Come up with test cases

## Krishan
Adjust the elevator class + middleMan to use the state pattern, 
Design Elevator+State pattern in more detail,
Sequence diagram,
Clean up elevator state diagram 

## Yuvraj
Write JUnit test cases,
README.MD

## Dani
Clean up UML Diagram (Combine new Elevator + Scheduler UMLs) + add a description,
Clean up Scheduler State Diagram,
Scheduler class,
Pair Programming Scheduler

## Abdalla
Clean up Scheduler State Diagram,
State pattern for Scheduler,
Pair Programming Scheduler 

## Issac
Come up with test cases, 
Add to UML


## Work Breakdown for iteration 1
## Aashna & Krishan
Scheduler class,  
Middleman class,
UML

## Yuvraj & Issac 
elevator class, 
event classes,  
elevator lamp, 
Sequence diagram

## Dani & Abdalla 
Floor class, 
Event classes, 
Floor lamp, 
ReadMe


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
4. Use steps 1-7 in the instructions shown above.

## Instructions for testing
1. Go to TestElevatorSystem.java inside src 
2. Click on the Run as Junit test
