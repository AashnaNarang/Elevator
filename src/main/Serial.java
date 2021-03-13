package main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serial {
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
	
	public static <T> T deSerialize(byte[] msg, Class<T> id) {
		
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(msg);
	    	ObjectInput in = new ObjectInputStream(bis);
			return id.cast(in.readObject());
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        return null;
	}
}
