package actualProject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver implements Runnable{
	
	private final int rport;
	
	public Receiver(int port) {
		rport = port;
	}

	@Override
	public void run() {
		try {
			DatagramSocket cSocket = new DatagramSocket(rport);
			byte[] buf = new byte[2000];
			
			cSocket.setSoTimeout(5000);
			while(true) {
				DatagramPacket p = new DatagramPacket(buf, 0, buf.length);
				cSocket.receive(p);
				String m = new String(p.getData());
				System.out.println(m);
			}
			
			
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Timed Out. Closed");
		}
		
	}
	
	

	
	//Receiver///////////////////////////////////////
	
	//initialize variable that holds expected sequence number of frame that will be received
	//initialize two variables that will be buffers from frames
	//initialize a variable that will hold event type, will always be frame_arrival
	
	//make expected sequence number equal to 0
	//in while loop
		//wait until you get the signal from event type variable
		//if the event equals frame arrival:
			//get new message from the physical layer
			//if the message has the same sequence number as the one that is expected:
				//pass the message to network layer
				//increment expected sequence number
			//set ACK sequence number = to previous expected sequence number
			//send ACK to physical layer to awaken the sender
}
