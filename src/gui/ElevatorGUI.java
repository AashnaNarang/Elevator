package gui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.Configurations;
import main.Direction;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.Timer;

import main.Timing;

/**
 * The GUI that represents the different elevators that are running.
 *
 */
public class ElevatorGUI extends JFrame {

	private JPanel contentPane, elevatorOutput, elevatorPanel;
	// Create a file chooser
	final JFileChooser fc = new JFileChooser();
	public FloorSubsystem floorSubsystem;
	public File file;

	private List<JTextArea> elevatorsData;
	private List<Elevator> elevators;

	private JButton btnStart;
	
	private Hashtable<String, JButton> buttonDictionary; 
	
	private boolean isFinished = false; 

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
		int frameWidth = (int) screenSize.getWidth();
		int frameHeight = (int) screenSize.getHeight() / 2 + 100;

		int x = (int) (screenSize.getWidth() - frameWidth) / 2;
		int y = (int) (screenSize.getHeight() - frameHeight) / 2;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, 28, 29);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("Import Input File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int returnVal = fc.showOpenDialog(ElevatorGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
				} else {
				}
			}
		});

		elevatorFinished(this);

		Configurations.NUM_ELEVATORS = Integer
				.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Elevators: "));
		Configurations.NUMBER_OF_FLOORS = Integer
				.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Floors: "));

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				floorSubsystem = new FloorSubsystem(file.getName(), Configurations.FLOOR_PORT,
						Configurations.FLOOR_EVENT_PORT);
				RunElevator(floorSubsystem);
			}
		});
		addOutputConsoles();
		contentPane.add(btnStart);
		contentPane.add(btnNewButton);
		contentPane.add(elevatorOutput);
		createElevatorButtons(); 
	}

	/**
	 * Produces a pop up whenever the system is finished with an input file
	 */
	private void elevatorFinished(JFrame frame) {
		Timer checkIfFinished = new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String timingInfo = Timing.getTimingInfo();
				if (timingInfo != null) {
					isFinished = true; 
					JOptionPane.showMessageDialog(frame, "This is the resulting performance time: \n" + timingInfo,
							"Performance Results", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		checkIfFinished.setRepeats(true);
		checkIfFinished.start();
	}

	private void createElevatorButtons() {
//		JPanel panel = new JPanel();
//		// Create new grid panel
//		GridLayout gridlayout = new GridLayout(2, Configurations.NUM_ELEVATORS);
//		gridlayout.setVgap(30);
//
//		panel.setLayout(gridlayout);

		for (int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
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

			contentPane.add(elevatorButton);
			contentPane.add(upButton);
			contentPane.add(downButton);
			contentPane.add(transientErrorButton);
			contentPane.add(permErrorButton);
			contentPane.add(doorOpened);
			contentPane.add(doorClosed);
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
			Timer timer = new Timer(1, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					String elevatorStatus = "";
					if (!elevators.isEmpty()) {
						LinkedList<String> statuses = elevators.get(innerI).getStatuses();
						for (String s : statuses) {
							elevatorStatus = elevatorStatus + s + "\n";
							JButton buttonUp = buttonDictionary.get("Elevator: " + innerI + " UP");
							JButton buttonDown = buttonDictionary.get("Elevator: " + innerI + " DOWN");
							JButton transientError = buttonDictionary.get("Elevator: " + innerI + " Transient Error: " + innerI);
							JButton permError = buttonDictionary.get("Elevator: " + innerI + " Permanent Error: " + innerI);
							JButton openDoor = buttonDictionary.get("Door " + innerI + " Open");
							JButton closedDoor = buttonDictionary.get("Door " + innerI + " Closed");
							
							//handle up and down movement
							if(s.contains("moving") && elevators.get(innerI).getDirection() == Direction.UP) {
								buttonUp.setBackground(Color.GREEN);
								transientError.setBackground(null);
								buttonDown.setBackground(null);
								permError.setBackground(null);
							}
							else if(s.contains("moving") && elevators.get(innerI).getDirection() == Direction.DOWN) {
								buttonDown.setBackground(Color.GREEN);
								transientError.setBackground(null);
								buttonUp.setBackground(null);
								permError.setBackground(null);
							}
							
							//handle door open/closed
							if(elevators.get(innerI).getIsDoorsOpen()) {
								openDoor.setBackground(Color.YELLOW);
								closedDoor.setBackground(null);
							}
							else if(!(elevators.get(innerI).getIsDoorsOpen())) {
								openDoor.setBackground(null);
								closedDoor.setBackground(Color.YELLOW);
							}
							if(elevators.get(innerI).getErrorCode() == 1) {
								buttonDown.setBackground(null);
								buttonUp.setBackground(null);
								permError.setBackground(null);
								openDoor.setBackground(null); 
								closedDoor.setBackground(null); 
								transientError.setBackground(Color.RED);
							}
							else if(elevators.get(innerI).getErrorCode() == 2) {
								buttonDown.setBackground(null);
								buttonUp.setBackground(null);
								transientError.setBackground(null);
								openDoor.setBackground(null); 
								closedDoor.setBackground(null); 
								permError.setBackground(Color.RED);
							}
							if(isFinished) {
								buttonDown.setBackground(null);
								buttonUp.setBackground(null);
							}
						}
						elevatorsData.get(innerI).append(elevatorStatus);
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
