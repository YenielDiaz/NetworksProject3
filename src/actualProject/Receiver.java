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
	int expectedSeqNumber;
	int ackNumber;
	
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
			expectedSeqNumber = 10;
			boolean isFirst = true;
			
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
				int receivedSeqNumber = din.readInt();
				if(isFirst) {
					expectedSeqNumber = receivedSeqNumber;
					isFirst = false;
				}
				//comparing expected seq number to the seq number received
				if(expectedSeqNumber == receivedSeqNumber) {
					//insert message into string
					String m = new String(din.readLine());
					//print message
					System.out.println("Receiver: Message with expected SeqNumber received: " + m);
					//increment expected sequence number
					expectedSeqNumber++;
					ackNumber = expectedSeqNumber-1;
					//make output streams to write in the ack number
					ByteArrayOutputStream bout = new ByteArrayOutputStream();
					DataOutputStream dout = new DataOutputStream(bout);
					
					
					dout.writeInt(ackNumber);
					//make packet to send the ack number
					DatagramPacket pACK = new DatagramPacket(bout.toByteArray(), bout.size(),
							InetAddress.getLocalHost(), sPort);
					
					//send the ack number
					network.send(pACK);
					dout.close();
					bout.close();
				}else {
					System.out.println("Receiver: Discarded duplicate.");
				}
				
				din.close();
				
				bin.close();
			}
			
			
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Timed Out. Closed");
		}
		
	}
	
}
