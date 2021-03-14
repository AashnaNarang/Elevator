package main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serial {
	
	/**
	 * 
	 * @param event The event that needs to be serialized
	 * @return A byte array that can be used to send over UDP.
	 */
	public static <T> byte[] serialize(T event) {

        try {
        	// Write client byte message to output stream
    		ByteArrayOutputStream msg = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(msg);
			
			oos.writeObject(event);
			
			return msg.toByteArray();
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		}
        return null;
	}
	
	/**
	 * 
	 * @param msg The message that was received over UDP and needs to be deserialized.
	 * @param id The Class of event that the message needs to be casted to.
	 * @return The actual event object that was sent over UDP. 
	 */
	public static <T> T deSerialize(byte[] msg, Class<T> id) {
		
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(msg);
	    	ObjectInput in = new ObjectInputStream(bis);
			return id.cast(in.readObject());
		} catch (IOException | ClassNotFoundException | ClassCastException e1) {
		}
        return null;
	}
}
