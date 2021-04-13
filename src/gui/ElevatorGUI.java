package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
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

	private JPanel contentPane, elevatorOutput;
	// Create a file chooser
	final JFileChooser fc = new JFileChooser();
	public FloorSubsystem floorSubsystem;
	public File file;

	//This is to allow multiple instances of the elevators 
	private List<JTextArea> elevatorsData;
	private List<Elevator> elevators;

	private JButton btnStart;

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
		//This is to create the output of the GUI, to display data
		elevatorsData = new ArrayList<>();
		elevators =  new ArrayList<>();
		// Get the screen size of the computer
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidth = (int) screenSize.getWidth();
		int frameHeight = (int) screenSize.getHeight() / 2 + 100;

		int x = (int) (screenSize.getWidth() - frameWidth) / 2;
		int y = (int) (screenSize.getHeight() - frameHeight) / 2;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(x, y, frameWidth, frameHeight);
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

	}

	/**
	* Produces a pop up whenever the system is finished with an input file
	*/
	private void elevatorFinished(JFrame frame) {
		Timer checkIfFinished = new Timer(5000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String timingInfo = Timing.getTimingInfo();
				if(timingInfo != null) {
					JOptionPane.showMessageDialog(frame, "This is the resulting performance time: \n" + timingInfo, "Performance Results", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		checkIfFinished.setRepeats(true);
		checkIfFinished.start();
	}

	/**
	* Creates the text area outputs and handles whenever an status update is available
	* for a particular elevator
	*/
	private void addOutputConsoles() {
		// Create new grid panel
		GridLayout gridlayout = new GridLayout(2, Configurations.NUM_ELEVATORS);

		gridlayout.setVgap(10);

		elevatorOutput = new JPanel();
		elevatorOutput.setLayout(gridlayout);


		// Create generic border
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		for(int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
			elevatorsData.add(new JTextArea(30,30));
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
		for(int i = 0; i < Configurations.NUM_ELEVATORS; i++) {
			elevators.add(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + i, Configurations.ELEVATOR_SCHEDULAR_PORT + i,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT));
		}
		Thread threadFloorSubsystem = new Thread(floorSubsystem, "floorSubsystem");
		threadFloorSubsystem.start();
		sched.start();
		for(int i = 0; i < elevators.size(); i++) {
			Thread elevator = new Thread(elevators.get(i) , "elevator " + i);
			elevator.start();

		}
	}
}
