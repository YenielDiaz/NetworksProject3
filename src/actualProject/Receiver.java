package actualProject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Receiver implements Runnable{
	
	//receiver port
	private final int rPort;
	//sender port
	private final int sPort;
	Integer expectedSeqNumber;
	Integer ackNumber;
	
	public Receiver(int rport, int sport) {
		this.rPort = rport;
		this.sPort = sport;
	}

	@Override
	public void run() {
		try {
			DatagramSocket network = new DatagramSocket(rPort);
			byte[] buf = new byte[2000];
			expectedSeqNumber = 0;
			
			network.setSoTimeout(5000);
			while(true) {
				DatagramPacket pReceive = new DatagramPacket(buf, 0, buf.length);
				network.receive(pReceive);
				String m = new String(pReceive.getData());
				System.out.println("Message received: " + m);
				//check if received seqNumber equals expected one
				ackNumber = expectedSeqNumber;
				String ackString = ackNumber.toString();
				DatagramPacket pACK = new DatagramPacket(ackString.getBytes(), ackString.length(),
						InetAddress.getLocalHost(), sPort);
				network.send(pACK);
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
