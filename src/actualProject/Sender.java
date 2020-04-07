package actualProject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
			
			//this is supposed to be while(true) but im doing it a limited amount of times to test
			while(seqNumber < 40) {
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				DataOutputStream dout = new DataOutputStream(bout);
				dout.writeInt(seqNumber);
				dout.close();
				//insert sequence number in frame
				String m  = new String("Message"); //get input from console and put it in m
				bout.write(m.getBytes());
				DatagramPacket pSend = new DatagramPacket(bout.toByteArray(), bout.size(),
						InetAddress.getLocalHost(), rPort);
				
				DatagramPacket pACK = new DatagramPacket(ackBuf, 0, ackBuf.length);
				physical.send(pSend);
				physical.setSoTimeout(1000);
				physical.receive(pACK);
				String ack = new String(pACK.getData());
				System.out.println("ACK number received is: " + ack); //testing line
			
				if(ack.equals(seqNumber.toString())){
					//stop timer
					//get next message to send
					seqNumber++;
					System.out.println(seqNumber);
					
				}
				seqNumber++;
				bout.close();
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
	
	
	/*
	 * WAY TO INCLUDE SEQNUMBER IN DATAGRAM PACKET AND READ IT
	 * 
	 * 
	 *     Create a ByteArrayOutputStream.
    Wrap it in a DataOutputStream
    Use DataOutputStream.writeInt() to write the sequence number.
    Use write() to write the data.
    Construct the DatagramPacket from the byte array returned by the ByteArrayOutputStream.
    
	 * //mesage
		String m = "Message";
		//seq number
		int i = 478;
		
		//make byte array output stream and  wrap in data output stream
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(b);
		
		try {
			//write the seq number first
			dout.writeInt(i);
			dout.close();
			
			//write the message next
			b.write(m.getBytes(), 0, m.length());
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//make byte array input stream and wrap in data input stream
		ByteArrayInputStream bi = new ByteArrayInputStream(b.toByteArray());
		DataInputStream din = new DataInputStream(bi);
		
		try {
			//reading the sequence number
			System.out.println(din.readInt());
			din.close();
			bi.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 */
}
