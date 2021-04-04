package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

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
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.Timer;

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
		//Get the screen size of the computer
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int frameWidth = (int)screenSize.getWidth()/2 + 100;
		int frameHeight = (int)screenSize.getHeight()/2;

		int x = (int)(screenSize.getWidth() - frameWidth)/2;
		int y = (int)(screenSize.getHeight() - frameHeight)/2;

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

			private void RunElevator(FloorSubsystem floorSubsystem) {
				Thread sched = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
						Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
						Configurations.TIMER_PORT), "scheduler");
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
				threadFloorSubsystem.start();
				sched.start();
				elevator0.start();
				elevator1.start();
				elevator2.start();
				elevator3.start();
			}
		});

		addOutputConsoles();

		JComboBox<Integer> cmboNumElevator = new JComboBox<Integer>();

		final int elevatorUserInput = Integer
				.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Elevators: "));
		for (int i = 1; i <= elevatorUserInput; i++) {
			cmboNumElevator.addItem(i);
		}

		cmboNumElevator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Configurations.NUM_ELEVATORS = elevatorUserInput;
				cmboNumElevator.setSelectedIndex(cmboNumElevator.getSelectedIndex());
			}
		});

		JLabel lblNumElevators = new JLabel("Elevators:");
		contentPane.add(lblNumElevators);
		contentPane.add(cmboNumElevator);

		JComboBox<Integer> cmboNumFloors = new JComboBox<Integer>();
		final int floorUserInput = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Floors: "));
		for (int i = 1; i <= floorUserInput; i++) {
			cmboNumFloors.addItem(i);
		}

		cmboNumFloors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Configurations.NUMBER_OF_FLOORS = floorUserInput;
				cmboNumFloors.setSelectedIndex(cmboNumFloors.getSelectedIndex());
			}
		});

		JLabel lblNumFloors = new JLabel("Floors: ");
		contentPane.add(lblNumFloors);
		contentPane.add(cmboNumFloors);
		contentPane.add(btnNewButton);
		contentPane.add(elevatorOutput);
	}

	public void addOutputConsoles() {
		//Create new grid panel
		GridLayout gridlayout = new GridLayout(0,4);
		gridlayout.setVgap(10);
		elevatorOutput = new JPanel();
		elevatorOutput.setLayout(gridlayout);

		//Create generic border
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

		// Elevator data text output
		elevatorData0 = new JTextArea(20,20);
		elevatorData0.setBorder(border);
		elevatorData0.setEditable(false);
		Timer timer0 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int end = elevatorData0.getDocument().getLength();
				int start = 0;
				try {
					start = Utilities.getRowStart(elevatorData0, end);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (start == end) {
					end--;
					try {
						start = Utilities.getRowStart(elevatorData0, end);
					} catch (BadLocationException e) {

					}
				}
				String text = "";
				try {
					text = elevatorData0.getText(start, end - start);
				} catch (BadLocationException e) {

				}
				if (e0 != null && !text.equals(e0.toString())) {
					elevatorData0.append(e0.toString() + "\n");
				}
			}
		});
		timer0.setRepeats(true);
		timer0.start();
		elevatorOutput.add(elevatorData0);

		// Elevator data text output
		elevatorData1 = new JTextArea(20,20);
		elevatorData1.setBorder(border);
		elevatorData1.setEditable(false);
		Timer timer1 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int end = elevatorData1.getDocument().getLength();
				int start = 0;
				try {
					start = Utilities.getRowStart(elevatorData1, end);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (start == end) {
					end--;
					try {
						start = Utilities.getRowStart(elevatorData1, end);
					} catch (BadLocationException e) {

					}
				}
				String text = "";
				try {
					text = elevatorData1.getText(start, end - start);
				} catch (BadLocationException e) {

				}
				if (e1 != null && !text.equals(e1.toString())) {
					elevatorData1.append(e1.toString() + "\n");
				}
			}
		});
		timer1.setRepeats(true);
		timer1.start();
		elevatorOutput.add(elevatorData1);

		// Elevator data text output
		elevatorData2 = new JTextArea(20,20);
		elevatorData2.setBorder(border);
		elevatorData2.setEditable(false);
		Timer timer2 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int end = elevatorData2.getDocument().getLength();
				int start = 0;
				try {
					start = Utilities.getRowStart(elevatorData2, end);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (start == end) {
					end--;
					try {
						start = Utilities.getRowStart(elevatorData2, end);
					} catch (BadLocationException e) {

					}
				}
				String text = "";
				try {
					text = elevatorData2.getText(start, end - start);
				} catch (BadLocationException e) {

				}
				if (e2 != null && !text.equals(e2.toString())) {
					elevatorData2.append(e2.toString() + "\n");
				}
			}
		});
		timer2.setRepeats(true);
		timer2.start();
		elevatorOutput.add(elevatorData2);

		// Elevator data text output
		elevatorData3 = new JTextArea(20,20);
		elevatorData3.setBorder(border);
		elevatorData3.setEditable(false);
		Timer timer3 = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int end = elevatorData3.getDocument().getLength();
				int start = 0;
				try {
					start = Utilities.getRowStart(elevatorData3, end);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (start == end) {
					end--;
					try {
						start = Utilities.getRowStart(elevatorData3, end);
					} catch (BadLocationException e) {

					}
				}
				String text = "";
				try {
					text = elevatorData3.getText(start, end - start);
				} catch (BadLocationException e) {

				}
				if (e3 != null && !text.equals(e3.toString())) {
					elevatorData3.append(e3.toString() + "\n");
				}
			}
		});
		timer3.setRepeats(true);
		timer3.start();
		elevatorOutput.add(elevatorData3);
	}
}
