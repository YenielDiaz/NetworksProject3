package actualProject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
			//creating socket which will receive the message
			DatagramSocket network = new DatagramSocket(rPort);
			//resetting expected sequence number
			expectedSeqNumber = 0;
			
			
			while(true) {
				//make buffer that will store received message
				byte[] buf = new byte[2000];
				//make packet that will receive message
				DatagramPacket pReceive = new DatagramPacket(buf, 0, buf.length);
				//receive message
				network.receive(pReceive);
				
				//make input streams to read the sequence number and the message
				ByteArrayInputStream bin = new ByteArrayInputStream(pReceive.getData());
				DataInputStream din = new DataInputStream(bin);

				//comparing expected seq number to the seq number received
				if(expectedSeqNumber == din.readInt()) {
					//insert message into string
					String m = new String(din.readLine());
					//print message
					System.out.println("Message with expected SeqNumber received: " + m);
					//increment expected sequence number
					expectedSeqNumber++;
				}
				
				din.close();
				//make output streams to write in the ack number
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				DataOutputStream dout = new DataOutputStream(bout);
				ackNumber = expectedSeqNumber-1;
				
				dout.writeInt(ackNumber);
				//make packet to send the ack number
				DatagramPacket pACK = new DatagramPacket(bout.toByteArray(), bout.size(),
						InetAddress.getLocalHost(), sPort);
				
				//send the ack number
				network.send(pACK);
				bin.close();
				dout.close();
				bout.close();
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
