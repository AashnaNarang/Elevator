package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Configurations;
import main.Elevator;
import main.FloorSubsystem;
import main.Scheduler;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ElevatorGUI extends JFrame {

	private JPanel contentPane;
	// Create a file chooser
	final JFileChooser fc = new JFileChooser();
	public FloorSubsystem floorSubsystem;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 778, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("Import Input File");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int returnVal = fc.showOpenDialog(ElevatorGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					floorSubsystem = new FloorSubsystem(file.getName(), Configurations.FLOOR_PORT,
							Configurations.FLOOR_EVENT_PORT);
					RunElevator(floorSubsystem);
				} else {
				}
			}


			private void RunElevator(FloorSubsystem floorSubsystem) {
				Thread sched = new Thread(new Scheduler(Configurations.FLOOR_EVENT_PORT, Configurations.ARRIVAL_PORT,
						Configurations.DEST_PORT, Configurations.FLOOR_PORT, Configurations.ELEVATOR_STAT_PORT,
						Configurations.TIMER_PORT), "scheduler");
				Thread elevator0 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT,
						Configurations.ELEVATOR_SCHEDULAR_PORT, Configurations.ARRIVAL_PORT, Configurations.DEST_PORT,
						Configurations.ELEVATOR_STAT_PORT), "elevator0");
				Thread elevator1 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 1,
						Configurations.ELEVATOR_SCHEDULAR_PORT + 1, Configurations.ARRIVAL_PORT,
						Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator1");
				Thread elevator2 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 2,
						Configurations.ELEVATOR_SCHEDULAR_PORT + 2, Configurations.ARRIVAL_PORT,
						Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator2");
				Thread elevator3 = new Thread(new Elevator(Configurations.ELEVATOR_FLOOR_PORT + 3,
						Configurations.ELEVATOR_SCHEDULAR_PORT + 3, Configurations.ARRIVAL_PORT,
						Configurations.DEST_PORT, Configurations.ELEVATOR_STAT_PORT), "elevator3");
				Thread threadFloorSubsystem = new Thread(floorSubsystem, "floorSubsystem");
				threadFloorSubsystem.start();
				sched.start();
				elevator0.start();
				elevator1.start();
				elevator2.start();
				elevator3.start();
			}
		});
		
		JComboBox cmboNumElevator = new JComboBox();
		
		final int elevatorUserInput = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Elevators: "));
		for (int i = 1; i <= elevatorUserInput; i++) {
			cmboNumElevator.addItem(new Integer(i));
		}
		
		cmboNumElevator.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        Configurations.NUM_ELEVATORS = elevatorUserInput;
		        cmboNumElevator.setSelectedIndex(cmboNumElevator.getSelectedIndex());
		    }
		});
		
		JLabel lblNumElevators = new JLabel("Elevators:");
		contentPane.add(lblNumElevators);
		contentPane.add(cmboNumElevator);
		
		JComboBox cmboNumFloors = new JComboBox();
		final int floorUserInput = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of Floors: "));
		for (int i = 1; i <= floorUserInput; i++) {
			cmboNumFloors.addItem(new Integer(i));
		}
		
		cmboNumFloors.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        Configurations.NUMBER_OF_FLOORS = floorUserInput;
		        cmboNumFloors.setSelectedItem(cmboNumFloors.getSelectedIndex());
		    }
		});
		
		JLabel lblNumFloors = new JLabel("Floors: ");
		contentPane.add(lblNumFloors);
		contentPane.add(cmboNumFloors);
	}
}
