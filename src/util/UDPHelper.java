package util;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPHelper {

  private DatagramPacket sendPacket;
  private DatagramSocket sendSocket;
  private byte[] sendData;

  private DatagramPacket receivePacket;
  private DatagramSocket receiveSocket;
  private byte[] receiveData;

  private final int MAX_PACKET_SIZE = 100;

  private boolean debug = false;

  public UDPHelper(int socket) {
    try {
      sendSocket = new DatagramSocket();
      receiveSocket = new DatagramSocket(socket);
      receiveSocket.setSoTimeout(1000); //Only allow a 1 second wait
    } 
    catch (SocketException se) {
    }
  }

  /**
   * Send a packet to a destination socket
   * @message The message to send
   * @socket The destination socket
   */
  public void sendPacket(byte [] sendData, int socket) {
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
    
    //Try to receive a packet, if timeout, return null
    try {
      socket.receive(receivePacket);
    } catch (IOException e) {
    	return null;
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
  
  public DatagramSocket getSendSocket() {
	  return sendSocket;
  }
  
  public DatagramSocket getReceiveSocket() {
	  return receiveSocket;
  }

  /**
   * Closes all the sockets that were used
   */
  private void cleanUp() { sendSocket.close(); }
}
