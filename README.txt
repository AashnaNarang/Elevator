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

## StationaryEvent.java
This class is used by the elevator to tell the scheduler that it is stationary and ready for a floor event. It includes the elevator's id, ports to use to communicate back, and the current floor the elevator is on. 

## TimeoutEvent.java
This class will handle the timeout events where it will provide the elevator id, and the status if the timeout occured before arriving at the source floor.

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

## SchedulerState.java
This class will get the infomation on which state the scheduler is moving into.

## Serial.java
This class is a method for the information to be serialize and deserialize.

## NetworkCommunicator.java
This class will send the datagram packets in a byte array format to a destination port. This is all done using UDP.

## InvalidRequestException.java
This class is strictly designed so the the scheduler will not allow any invalid commands to elevator.

## ElevatorTimer.java
This class will provide us with the timer for the Elevator where it will provide us with time elevator started with, if the timer timed out or if the timer got cancelled.

## SchedulerTimer.java
This class will provide us with the timer for the scheduler where it will provide us with time scheduler started with, if the timer timed out or if the timer got cancelled.

## Configurations.java
The configuration needed across many other classes.

## Timing.java
A static Timing class to measure the time it takes to finish servicing all of the events in the input file. It includes methods to start the timer, record when we�re done servicing an event, and then get timing info when the whole input file is complete. It is calculated by taking average time for one request by taking the total time and dividing it by the total number of requests because requests do not finish sequentially in our real time system.

## CustomRenderer.java
A class set so that it can force the background of the ElevatorGUI to be colored in red, white, or black.
 
## ElevatorGUI.java
This class is a representation of our Elevator system working in a graphical interface unit. This class provides a visual representation of the elevator moving throughout the building with an indication in which direction the elevator is headed.�

## ElevatorTimer.java
This Class is made for the specific print timing statements for the Elevator where it will print the time it started, timed out, or canceled.�
 
## SchedulerTimer.java
This Class is made for the specific print timing statements for the Scheduler where it will print the time it started, timed out, or canceled.�

## Test folder 
This folder contains multiple Junit test java files which were made to test numerous scenarios for our elevator system.�

## Work Breakdown for iteration 5

## Aashna
Add elevator error state instance variables and update to strings
Design and implement timing how long it takes to service an input file (Timing.java)
Write description for timing design choices + add comments to Timing.java
Debug issues regarding configuring time for elevator to move to one floor
Tested final product after integrating components and cleaned up some code/made small fixes
Fix timing diagram from Iteration 4
Fix state diagram to include faults
Timing diagram from iteration 5
Write up design reflection

## Krishan
Sequence diagrams for error sequences
Added methods to provide elevator events to GUI
Work Breakdown
Review code

## Abdalla
Made the GUI allow for configurable values for elevator 
Iteration 5 tests
Fixed iteration 2 tests
Fixed Load Balancing test
Added comments to tests 
Review Codebase to make sure everything has comments + java docs
Write test instructions - updated readme

## Dani
Create initial GUI JFrame
Get/Set # of elevators/floors
Status of elevator
UML class diagram

## Isaac
GUI layout
GUI error checks for user control
Moving elevator in the GUI
Put final report together
GUI console output and check for when the system is finished running
GUI java docs/comments

## Yuvi
Fix config file + fix code so you can specify the time it takes to move between floors and the time it takes to load/unload an elevator
Results from performance measurement
Measurement results for determining door and floor movement times.
ReadMe

## Work Breakdown for iteration 4
## Everyone 
Design how to handle permanent and transient faults 

## Aashna
Build timer class (SchedulerTimer.java and TimeoutEvent.java). 
Implement permanent fault (error code 2). 
Help fix failing test cases. 
Fix various bugs in the code. 
Clean up print statements. 
Test + debug issues appearing with 4 elevators. 

## Krishan
Implement transient fault (error code 1). 
Add usage of elevator Ids to Floor Event class. 
Test + debug issues appearing with 4 elevators. 

## Abdalla
Alter the system to parse error codes from text files. 
Add code in scheduler to thrown an exception/shut down system if the scheduler is about to tell the elevator to make an illegal move. 
Worked with Isaac and Dani to fix test cases. 

## Dani
Iteration 4 JUnit test cases.

## Issac 
JUnit Test cases for 4 elevators and 22 floors. 

## Yuvraj
ReadMe file. 
Javadoc

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
7. Run the java application using ElevatorGUI.java. 

## Instructions for running the file using git:
1. Open the git project
2. Click on the green button "Code"
3. Download the ZIP folder.  
4. Use steps 1-7 in the instructions shown above.

## Instructions for running the program
1. Go to ElevatorGUI.java class 
2. Right click on the class and select "run as java application" or click the top green button to run.
3. Input a number of elevators preferred
4. Input a number of Floors preferred
5. Select "Import Input File" and find an input file to run 
6. Click start
7. It will then run until all events have been parsed

## Instructions for testing
1. Go to TestElevatorSystem.java inside src 
2. Click on the Run as Junit test

## Screenshot of GUI
To the see the working GUI please open up the Iteration5Diagrams folder and double click on GUI.png 
