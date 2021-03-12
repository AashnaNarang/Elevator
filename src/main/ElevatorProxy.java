package main;

import events.ArrivalEvent;
import events.Event;
import util.UDPHelper;

public class ElevatorProxy implements Runnable {
	
	private MiddleMan middleman;
	private UDPHelper udphelper;
	private final int RECEIVE_SOCKET = 25;
	
	public ElevatorProxy(MiddleMan middleman) {
		this.middleman = middleman;
		udphelper = new UDPHelper(RECEIVE_SOCKET);
	}
	public void run() {
		while(true) {
			byte[] data = udphelper.receivePacket(this.udphelper.getReceiveSocket());
			
			if(data != null) {
				if(DeserializedObject instanceof Event) {
					middleman.putDestinationEvent(DeserializedObject);
				}
				else if(DeserializedObject instanceof ArrivalEvent) {
					middleman.putArrivalEvent(DeserializedObject);
				}
			}
		}
	}
}
