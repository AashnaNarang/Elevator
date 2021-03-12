package main;

import events.ArrivalEvent;
import events.Event;
import events.FloorEvent;
import util.UDPHelper;

public class FloorProxy implements Runnable {
	
	private MiddleMan middleman;
	private UDPHelper udphelper;
	private final int RECEIVE_SOCKET = 26;
	
	public FloorProxy(MiddleMan middleman) {
		this.middleman = middleman;
		udphelper = new UDPHelper(RECEIVE_SOCKET);
	}
	
	public void run() {
		while(true) {
			byte[] data = udphelper.receivePacket(this.udphelper.getReceiveSocket());
			//TODO: Deserialize data
			if(data != null) {
				//If the data is not null, deserialize the data here
				//Three checks -> checking for floor event, destination event, arrival event
				//TODO: For the if statement, If deserialized object is an instanceOf floor event, destination event or arrival event
				if(DeserializedObject instanceof FloorEvent) {
					middleman.putFloorEvent(DeserializedObject);
				}
			}
		}
	}

}
