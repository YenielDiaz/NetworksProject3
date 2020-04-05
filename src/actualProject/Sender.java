package actualProject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Sender implements Runnable{

	//receiver port
	private final int rPort;
	//sender port
	private final int sPort = 49000;
	
	public Sender(int port) {
		rPort = port;
	}


	//send method
	@Override
	public void run() {
		try {
			DatagramSocket sSocket = new DatagramSocket(sPort);
			String m = "Messsage";
			
			DatagramPacket p = new DatagramPacket(m.getBytes(), m.length(),
					InetAddress.getLocalHost(), rPort);
			
			sSocket.send(p);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	//Sender///////////////////////////////////////
	
	//initialize variable that will hold sequence number of next outgoing frame
	//initialize buffer for outbound frame
	//initialize buffer for outbound packet
	//initialize variable to hold event type, will always be frame_arrival
	
	//initialize outbound sequence numbers by setting it to 0
	//get first packet from network layer

	//in while loop
		//construct a frame from transmission by putting the info in outbound frame equal to the outbound packet
		//insert sequence in number in frame
		//send outbound frame variable to physical layer
		//start timer (if it takes too long in receiving the ACK message send it again
		//wait until you get the sign to continue from the even type variable
		//if event == frame_arrival:
			//get ACK from physical layer
			//if ACK == current sequence number:
				//stop timer
				//get next message to send from network layer
				//increment sequence number

	///////////////////////////////////////////////
}
