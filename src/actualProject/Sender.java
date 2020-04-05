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
	private final int sPort;
	private Integer seqNumber;
	
	public Sender(int rport, int sport) {
		this.rPort = rport;
		this.sPort = sport;
	}


	//send method
	@Override
	public void run() {
		try {
			DatagramSocket physical = new DatagramSocket(sPort);
			
			byte[] ackBuf = new byte[2000];
			seqNumber = 0;
			
			while(true) {
				//insert sequence number in frame
				String m  = new String("Message"); //get input from console and put it in m
				DatagramPacket pSend = new DatagramPacket(m.getBytes(), m.length(),
						InetAddress.getLocalHost(), rPort);
				
				DatagramPacket pACK = new DatagramPacket(ackBuf, 0, ackBuf.length);
				physical.send(pSend);
				physical.setSoTimeout(1000);
				physical.receive(pACK);
				String ack = new String(pACK.getData());
				System.out.println("ACK number received is: " + ack);
				/*
				if(pACK.getData().equals(seqNumber.toString().getBytes())){
					//stop timer
					//get next message to send
					seqNumber++;
					
				}
				*/
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Timed out. Did not receive ACK");
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
		//insert sequence number in frame
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
