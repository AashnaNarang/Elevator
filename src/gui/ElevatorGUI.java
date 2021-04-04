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
import java.util.LinkedList;
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

	private JCheckBox debugMode;
	private JTextArea elevatorData0, elevatorData1, elevatorData2, elevatorData3;

	Thread elevator1, elevator0;
	private Elevator e0, e1, e2, e3;
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

		addOutputConsoles();

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
		contentPane.add(btnStart);
		contentPane.add(btnNewButton);
		contentPane.add(elevatorOutput);
	}

	public void elevatorFinished(JFrame frame) {
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

	public void addOutputConsoles() {
		// Create new grid panel
		GridLayout gridlayout = new GridLayout(0, 4);
		gridlayout.setVgap(10);
		elevatorOutput = new JPanel();
		elevatorOutput.setLayout(gridlayout);

		// Create generic border
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

		// Elevator data text output
		elevatorData0 = new JTextArea(30, 30);
		elevatorData0.setBorder(border);
		elevatorData0.setEditable(false);
		Timer timer0 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String elevatorStatus = "";
				if (e0 != null) {
					LinkedList<String> statuses = e0.getStatuses();
					for (String s : statuses) {
						elevatorStatus = elevatorStatus + s + "\n";
					}
					elevatorData0.append(elevatorStatus);
				}
			}
		});
		timer0.setRepeats(true);
		timer0.start();
		elevatorOutput.add(new JScrollPane(elevatorData0));

		// Elevator data text output
		elevatorData1 = new JTextArea(30, 30);
		elevatorData1.setBorder(border);
		elevatorData1.setEditable(false);
		Timer timer1 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String elevatorStatus = "";
				if (e1 != null) {
					LinkedList<String> statuses = e1.getStatuses();
					for (String s : statuses) {
						elevatorStatus = elevatorStatus + s + "\n";
					}
					elevatorData1.append(elevatorStatus);
				}
			}
		});
		timer1.setRepeats(true);
		timer1.start();
		elevatorOutput.add(new JScrollPane(elevatorData1));

		// Elevator data text output
		elevatorData2 = new JTextArea(30, 30);
		elevatorData2.setBorder(border);
		elevatorData2.setEditable(false);
		Timer timer2 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String elevatorStatus = "";
				if (e2 != null) {
					LinkedList<String> statuses = e2.getStatuses();
					for (String s : statuses) {
						elevatorStatus = elevatorStatus + s + "\n";
					}
					elevatorData2.append(elevatorStatus);
				}
			}
		});
		timer2.setRepeats(true);
		timer2.start();
		elevatorOutput.add(new JScrollPane(elevatorData2));

		// Elevator data text output
		elevatorData3 = new JTextArea(30, 30);
		elevatorData3.setBorder(border);
		elevatorData3.setEditable(false);
		Timer timer3 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String elevatorStatus = "";
				if (e3 != null) {
					LinkedList<String> statuses = e3.getStatuses();
					for (String s : statuses) {
						elevatorStatus = elevatorStatus + s + "\n";
					}
					elevatorData3.append(elevatorStatus);
				}
			}
		});
		timer3.setRepeats(true);
		timer3.start();
		elevatorOutput.add(new JScrollPane(elevatorData3));
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
		e0 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT, Configurations.ELEVATOR_SCHEDULAR_PORT,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		elevator0 = new Thread(e0, "elevator0");
		e1 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1, Configurations.ELEVATOR_SCHEDULAR_PORT + 1,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		elevator1 = new Thread(e1, "elevator1");
		e2 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 2, Configurations.ELEVATOR_SCHEDULAR_PORT + 2,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		Thread elevator2 = new Thread(e2, "elevator2");
		e3 = new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 3, Configurations.ELEVATOR_SCHEDULAR_PORT + 3,
				Configurations.ARRIVAL_PORT, Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT);
		Thread elevator3 = new Thread(e3, "elevator3");
		Thread threadFloorSubsystem = new Thread(floorSubsystem, "floorSubsystem");
		// start all the threads.
		threadFloorSubsystem.start();
		sched.start();
		elevator0.start();
		elevator1.start();
		elevator2.start();
		elevator3.start();
	}
}
