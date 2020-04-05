package actualProject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	
	public static void main(String[] args) {
		int rPort = 49002;
		int sPort = 49003;
		Sender send = new Sender(rPort, sPort);
		Receiver receive = new Receiver(rPort, sPort);
		
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
