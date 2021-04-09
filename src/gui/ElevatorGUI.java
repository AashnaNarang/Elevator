package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;

import main.Configurations;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;
import main.Timing;

/**
 * The GUI that represents the different elevators that are running. Includes both a console like view and a visual view
 * that shows the locations of the elevators and their states.
 */
public class ElevatorGUI extends JFrame {

	private JPanel elevatorOutput;

	// Create a file chooser
	final JFileChooser fc = new JFileChooser();
	public FloorSubsystem floorSubsystem;
	public File file;

	private List<JTextArea> elevatorsData;
	private List<Elevator> elevators;

	private JButton btnStart, fileImport;

	private Hashtable<String, JButton> buttonDictionary;

	//Holds the locations and previous locations of the elevators
	private JTable jtable;
	private ArrayList<Integer> previousSpot = new ArrayList<Integer>();

	//Helper Panels/Layouts for the GUI
	private JTabbedPane mainLayout;
	private JPanel mainContentConsole, tableContent, buttonList;
	private GridLayout consoleAndButton;
	private BoxLayout mainContentVisual;

	//Keep the computer screen dimensions
	private int frameWidth;
	private int frameHeight;
	private int x;
	private int y;

	//Handle error cases for users
	private boolean selectedFile = false;
	private boolean systemRan = false;

	private JFrame frame = this;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElevatorGUI frame = new ElevatorGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ElevatorGUI() {
		// This is to create the output of the GUI, to display data
		elevatorsData = new ArrayList<>();
		elevators = new ArrayList<>();
		buttonDictionary = new Hashtable<String, JButton>();

		// Get the screen size of the computer
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frameWidth = (int) screenSize.getWidth();
		frameHeight = (int) screenSize.getHeight();

		x = (int) (screenSize.getWidth() - frameWidth) / 2;
		y = (int) (screenSize.getHeight() - frameHeight) / 2;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, frameWidth, frameHeight);

		//Create a check that checks when the system is finished running
		elevatorFinished(this);

		//Asks the user for the number of elevators/floors
		Configurations.NUM_ELEVATORS = Integer
				.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Elevators: "));
		Configurations.NUMBER_OF_FLOORS = Integer
				.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Floors: "));

		//Display a help message
		JOptionPane.showMessageDialog(this,
				"How to use the system:\nThe system is meant to be used to simulate one scenario. "
						+ "After running once, the system must reset in order to simulate a new scenario."
						+ "\n\nHow the system works:\nThe Frame is a tabbed pane. "
						+ "In the top left you can click the tabs to change looks."
						+ "\nIn the console tab you can import a file and start the simulation."
						+ " The white squares you see will display SIMPLE lines describing the location of the elevator and whenever errors occur when you run a simulation."
						+ "\n\nThe visual tab shows the elevators moving, which is represented by the red boxes. "
						+ "It also has a section below which shows the current state of the elevator."
						+ "\nAs the simulation runs, you will see the labelled buttons turn on for corresponding elevators, reporting its current state (ex. UP will be highlighted when the elevator is moving up)"
						+ "\nHave fun with the elevator control system :)");

		addOutputConsoles(); //Creates a console like output displaying where the elevator is
		createVisualElevator();	//Creates a visual look of the elevator, displaying where they currently are
		elevatorLayoutSetup();	//Creates the layout of the GUI
		createElevatorButtons(); //Creates the elevator state buttons

		this.add(mainLayout);
		this.setTitle("ELEVATOR CONTROL SYSTEM");
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Creates an organized layout of the GUI and sets up the location of each panel
	 */
	private void elevatorLayoutSetup() {
		// Tabs Layout
		mainLayout = new JTabbedPane();

		// The two main panels
		mainContentConsole = new JPanel();
		tableContent = new JPanel();

		// Adding the two main panels into the tabs layout
		mainLayout.addTab("Console", mainContentConsole);
		mainLayout.addTab("Visual", tableContent);

		// Setup the console side layout
		consoleAndButton = new GridLayout(2, 0);
		mainContentConsole.setLayout(consoleAndButton);
		mainContentConsole.add(elevatorOutput);

		// Setup button side layout
		JPanel buttonHold = new JPanel();

		//Creates the start button
		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedFile && !systemRan) {
					floorSubsystem = new FloorSubsystem(file.getName(), Configurations.FLOOR_PORT,
							Configurations.FLOOR_EVENT_PORT);
					RunElevator(floorSubsystem);
					systemRan = true;
				} else if (!selectedFile) {
					JOptionPane.showMessageDialog(frame, "Select a file first");
				}
				if (systemRan && selectedFile) {
					JOptionPane.showMessageDialog(frame,
							"Thank you for using the system, please restart the system if you want to try another simulation");
				}
			}
		});

		//Creates the file import button
		fileImport = new JButton("Import Input File");
		fileImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int returnVal = fc.showOpenDialog(ElevatorGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					if (file != null) {
						selectedFile = true;
					}
				}
			}
		});
		buttonHold.add(btnStart);
		buttonHold.add(fileImport);
		mainContentConsole.add(buttonHold);

		// Setup Visual Side
		mainContentVisual = new BoxLayout(tableContent, BoxLayout.Y_AXIS);
		tableContent.setLayout(mainContentVisual);
		tableContent.add(jtable);

		buttonList = new JPanel();
		buttonList.setLayout(new BoxLayout(buttonList, BoxLayout.Y_AXIS));
		tableContent.add(buttonList);

	}

	/**
	 * Produces a pop up whenever the system is finished with an input file
	 */
	private void elevatorFinished(JFrame frame) {
		//Every 5 seconds, checks if the simulation is finished running
		Timer checkIfFinished = new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String timingInfo = Timing.getTimingInfo();
				if (timingInfo != null) {
					//Displays the performance time when the simulation is finished running
					JOptionPane.showMessageDialog(frame, "This is the resulting performance time: \n" + timingInfo,
							"Performance Results", JOptionPane.INFORMATION_MESSAGE);
					//When the system is finished running, reset all the up and down labelled buttons
					//so they are no longer highlighted
					for (JButton button : buttonDictionary.values()) {
						if (button.getText().contains("UP") || button.getText().contains("DOWN"))
							button.setBackground(null);
					}
				}
			}
		});
		checkIfFinished.setRepeats(true);
		checkIfFinished.start();
	}

	/**
	 * Creates buttons that are used to display the elevator state
	 */
	private void createElevatorButtons() {

		for (int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
			JPanel buttons = new JPanel();
			JButton elevatorButton = new JButton("Elevator: " + i);
			JButton upButton = new JButton("UP");
			JButton downButton = new JButton("DOWN");
			JButton transientErrorButton = new JButton("Transient Error: " + i);
			JButton permErrorButton = new JButton("Permanent Error: " + i);
			JButton doorOpened = new JButton("Door " + i + " Open");
			JButton doorClosed = new JButton("Door " + i + " Closed");

			elevatorButton.setBorderPainted(false);
			elevatorButton.setFocusPainted(false);

			upButton.setBorderPainted(false);
			upButton.setFocusPainted(false);

			downButton.setBorderPainted(false);
			downButton.setFocusPainted(false);

			transientErrorButton.setBorderPainted(false);
			transientErrorButton.setFocusPainted(false);

			permErrorButton.setBorderPainted(false);
			permErrorButton.setFocusPainted(false);

			doorOpened.setBorderPainted(false);
			doorOpened.setFocusPainted(false);

			doorClosed.setBorderPainted(false);
			doorClosed.setFocusPainted(false);

			buttonDictionary.put(elevatorButton.getText() + " " + upButton.getText(), upButton);
			buttonDictionary.put(elevatorButton.getText() + " " + downButton.getText(), downButton);
			buttonDictionary.put(elevatorButton.getText() + " " + transientErrorButton.getText(), transientErrorButton);
			buttonDictionary.put(elevatorButton.getText() + " " + permErrorButton.getText(), permErrorButton);
			buttonDictionary.put(doorOpened.getText(), doorOpened);
			buttonDictionary.put(doorClosed.getText(), doorClosed);

			buttons.add(elevatorButton);
			buttons.add(upButton);
			buttons.add(downButton);
			buttons.add(transientErrorButton);
			buttons.add(permErrorButton);
			buttons.add(doorOpened);
			buttons.add(doorClosed);

			buttonList.add(buttons);
		}
	}

	/**
	 * Creates a table that is used to color cells indicating the elevator's position
	 */
	private void createVisualElevator() {
		JPanel visualElevator = new JPanel();
		jtable = new JTable(Configurations.NUMBER_OF_FLOORS + 1, Configurations.NUM_ELEVATORS + 1);
		jtable.setDefaultEditor(Object.class, null);
		jtable.setDefaultRenderer(Object.class, new CustomRenderer());
		jtable.setFont(jtable.getFont().deriveFont(Font.BOLD, jtable.getFont().getSize()));

		// Set up the initial locations of the elevators
		for (Elevator e : elevators) {
			jtable.setValueAt("Elevator Here", Configurations.NUMBER_OF_FLOORS, e.getId());
		}

		// Set up floor labels
		jtable.setValueAt("Floor", Configurations.NUMBER_OF_FLOORS, Configurations.NUM_ELEVATORS);
		for (int i = 1; i < jtable.getRowCount(); i++) {
			jtable.setValueAt(i, jtable.getRowCount() - i - 1, jtable.getColumnCount() - 1);
		}
		visualElevator.add(jtable);

		// Set up list
		for (int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
			previousSpot.add(Configurations.NUMBER_OF_FLOORS);
		}
	}

	/**
	 * Creates the text area outputs and handles whenever an status update is
	 * available for a particular elevator
	 */
	private void addOutputConsoles() {
		// Create new grid panel
		GridLayout gridlayout = new GridLayout(2, Configurations.NUM_ELEVATORS);

		gridlayout.setVgap(10);

		elevatorOutput = new JPanel();
		elevatorOutput.setLayout(gridlayout);

		// Create generic border
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		for (int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
			elevatorsData.add(new JTextArea(30, 30));
			elevatorsData.get(i).setBorder(border);
			elevatorsData.get(i).setEditable(false);
			final int innerI = i;
			Timer timer = new Timer(50, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					String elevatorStatus = "";
					if (!elevators.isEmpty()) {
						LinkedList<String> statuses = elevators.get(innerI).getStatuses();
						for (String s : statuses) {
							elevatorStatus = elevatorStatus + s + "\n";
							JButton buttonUp = buttonDictionary.get("Elevator: " + innerI + " UP");
							JButton buttonDown = buttonDictionary.get("Elevator: " + innerI + " DOWN");
							JButton transientError = buttonDictionary
									.get("Elevator: " + innerI + " Transient Error: " + innerI);
							JButton permError = buttonDictionary
									.get("Elevator: " + innerI + " Permanent Error: " + innerI);
							JButton openDoor = buttonDictionary.get("Door " + innerI + " Open");
							JButton closedDoor = buttonDictionary.get("Door " + innerI + " Closed");

							// handle up and down movement
							if (s.contains("moving") && elevators.get(innerI).getDirection() == Direction.UP) {
								buttonUp.setBackground(Color.GREEN);
								transientError.setBackground(null);
								buttonDown.setBackground(null);
								permError.setBackground(null);
							} else if (s.contains("moving") && elevators.get(innerI).getDirection() == Direction.DOWN) {
								buttonDown.setBackground(Color.GREEN);
								transientError.setBackground(null);
								buttonUp.setBackground(null);
								permError.setBackground(null);
							}

							// handle door open/closed
							if (elevators.get(innerI).getIsDoorsOpen()) {
								openDoor.setBackground(Color.YELLOW);
								closedDoor.setBackground(null);
							} else if (!(elevators.get(innerI).getIsDoorsOpen())) {
								openDoor.setBackground(null);
								closedDoor.setBackground(Color.YELLOW);
							}
							if (elevators.get(innerI).getErrorCode() == 1) {
								buttonDown.setBackground(null);
								buttonUp.setBackground(null);
								permError.setBackground(null);
								openDoor.setBackground(null);
								closedDoor.setBackground(null);
								transientError.setBackground(Color.RED);
							} else if (elevators.get(innerI).getErrorCode() == 2) {
								buttonDown.setBackground(null);
								buttonUp.setBackground(null);
								transientError.setBackground(null);
								openDoor.setBackground(null);
								closedDoor.setBackground(null);
								permError.setBackground(Color.RED);
							}
						}
						elevatorsData.get(innerI).append(elevatorStatus);

						for (Elevator e : elevators) {
							if (jtable.getValueAt(Configurations.NUMBER_OF_FLOORS - e.getCurrentFloor(),
									e.getId()) == null) {
								jtable.setValueAt("", previousSpot.get(e.getId()), e.getId());
								jtable.setValueAt("Elevator Here",
										Configurations.NUMBER_OF_FLOORS - e.getCurrentFloor(), e.getId());
								jtable.setValueAt("Elevator: " + e.getId(), Configurations.NUMBER_OF_FLOORS, e.getId());
								previousSpot.set(e.getId(), Configurations.NUMBER_OF_FLOORS - e.getCurrentFloor());
							} else {
								if (!jtable.getValueAt(Configurations.NUMBER_OF_FLOORS - e.getCurrentFloor(), e.getId())
										.equals("Elevator Here")) {
									jtable.setValueAt("", previousSpot.get(e.getId()), e.getId());
									jtable.setValueAt("Elevator Here",
											Configurations.NUMBER_OF_FLOORS - e.getCurrentFloor(), e.getId());
									jtable.setValueAt("Elevator: " + e.getId(), Configurations.NUMBER_OF_FLOORS,
											e.getId());
									previousSpot.set(e.getId(), Configurations.NUMBER_OF_FLOORS - e.getCurrentFloor());
								}
							}
						}
					}
				}
			});
			timer.setRepeats(true);
			timer.start();
			elevatorOutput.add(new JScrollPane(elevatorsData.get(i)));
		}

	}

	/**
	 * Creates the different threads for the elevators and schedulers - then runs
	 * them.
	 *
	 * @param floorSubsytem of the elevator.
	 */
	private void RunElevator(FloorSubsystem floorSubsystem) {
		// Create thread for the scheduler
		Thread sched = new Thread(
				new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT, Configurations.DEST_PORT,
						Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT, Configurations.TIMER_PORT),
				"scheduler");
		// create the elevators and their threads.
		for (int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
			elevators.add(
					new Elevator(Configurations.ELEVATOR_FLOOR_PORT + i, Configurations.ELEVATOR_SCHEDULAR_PORT + i,
							Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT));
		}
		Thread threadFloorSubsystem = new Thread(floorSubsystem, "floorSubsystem");
		threadFloorSubsystem.start();
		sched.start();
		for (int i = 0; i < elevators.size(); i++) {
			Thread elevator = new Thread(elevators.get(i), "elevator " + i);
			elevator.start();

		}
	}
}
