import java.io.*;
import java.net.*;
import java.util.*;

public class UDPHelper {

  private DatagramPacket sendPacket;
  private DatagramSocket sendSocket;
  private byte[] sendData;

  private DatagramPacket receivePacket;
  private byte[] receiveData;

  private final int MAX_PACKET_SIZE = 100;

  private boolean debug = false;

  public UDPHelper() {
    try {
      sendSocket = new DatagramSocket();
    } catch (SocketException se) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Send a packet to a destination socket
   * @message The message to send
   * @socket The destination socket
   */
  public void sendPacket(String message, int socket) {
    // Convert the info from the scheduler into byte data
    sendData = message.getBytes();

    // Create and send the packet to the particular socket
    try {
      sendPacket = new DatagramPacket(sendData, sendData.length,
                                      InetAddress.getLocalHost(), socket);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    // Debug details for the packet being sent
    if (debug) {
      System.out.println("Send Packet (contents): " + new String(sendData));
      System.out.println("Send Packet (to host): " + sendPacket.getAddress());
      System.out.println("Send Packet (destination port): " +
                         sendPacket.getPort());
    }

    // Send the packet through the socket
    try {
      sendSocket.send(sendPacket);
    } catch (Exception e) {
      cleanUp();
      e.printStackTrace();
      System.exit(1);
    }

    if (debug) {
      System.out.println("Send Packet (notification): Send Packet");
    }
  }

  /**
   * Receives a packet and returns the data that was returned
   * @return The data received from the socket
   */
  public byte[] receivePacket(DatagramSocket socket) {
    // Create a packet that will be used to hold the received data sent to the
    // socket
    receiveData = new byte[MAX_PACKET_SIZE];
    receivePacket = new DatagramPacket(receiveData, receiveData.length);

    try {
      socket.receive(receivePacket);
    } catch (IOException e) {
      cleanUp();
      e.printStackTrace();
      System.exit(1);
    }

    if (debug) {
      System.out.println("Receive Packet (contents): " +
                         new String(receiveData));
      System.out.println("Receive Packet (from port): " +
                         receivePacket.getPort());
      System.out.println("Receive Packet (from source): " +
                         receivePacket.getAddress());
    }

    return receiveData;
  }

  /**
   * Closes all the sockets that were used
   */
  private void cleanUp() { sendSocket.close(); }
}
