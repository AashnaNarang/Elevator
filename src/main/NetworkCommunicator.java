package main;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class NetworkCommunicator {

	public NetworkCommunicator() {
	}

	/**
	 * Used to send Datagrams.
	 * @param sendSocket Socket that will be used to send the Datagram from.
	 * @param msg A byte array that represents the message in the Datagram.
	 * @param msgLength An Integer representing the length of the message.
	 * @param destPort An Integer representing the destination port.
	 * @return The DatagramPacket being sent from the host to the destination.
	 */
	protected DatagramPacket send(DatagramSocket sendSocket, byte[] msg, int msgLength, 
			int destPort) {
		
		DatagramPacket sendPacket = null;
		try {
			
			//Initialize Datagram
			sendPacket = new DatagramPacket(msg, msgLength,	InetAddress.getLocalHost(), destPort);

			//Send Datagram
			sendSocket.send(sendPacket);


		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return sendPacket;
	}

	/**
	 * Used to receive Datagrams.
	 * @param receiveSocket Socket that will be used to receive Datagrams. 
	 * @return The DatagramPacket that is being received by the host.
	 */
	protected DatagramPacket receive(DatagramSocket receiveSocket) {

		//initialize and receive DatagramPacket
		byte data[] = new byte[100000];
		DatagramPacket receivePacket = new DatagramPacket(data, data.length);
		
		try {
			// Block until a datagram is received via sendReceiveSocket.  
			receiveSocket.receive(receivePacket);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return receivePacket;
	}
	
}
