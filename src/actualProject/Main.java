package actualProject;

import actualProject.Frame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	
	public static void main(String[] args) {
		int port = 49001;
		Sender send = new Sender(port);
		Receiver receive = new Receiver(port);
		
		ExecutorService execServ = Executors.newFixedThreadPool(2);
		execServ.submit(receive);
		execServ.submit(send);
		
	}
	
	/*
	 * 
	 * 
	 * STEPS TO INCLUDE SEQUENCE NUMNBER IN DATAGRAM PACKET
    Create a ByteArrayOutputStream.
    Wrap it in a DataOutputStream
    Use DataOutputStream.writeInt() to write the sequence number.
    Use write() to write the data.
    Construct the DatagramPacket from the byte array returned by the ByteArrayOutputStream.

	 * 
	 * 
	 * 
	 */
}
