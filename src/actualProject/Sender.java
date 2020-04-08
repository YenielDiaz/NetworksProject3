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
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class Sender implements Runnable{

	//receiver port
	private final int rPort;
	//sender port
	private final int sPort;
	private int seqNumber;

	DatagramSocket physical;
	ByteArrayOutputStream bout;
	DataOutputStream dout;
	DatagramPacket pSend;
	DatagramPacket pACK;
	String m;
	Scanner sc;
	Random r = new Random();

	public Sender(int rport, int sport) {
		this.rPort = rport;
		this.sPort = sport;
	}


	//send method
	@Override
	public void run() {

		//creating socket which will send messages
		try {
			physical = new DatagramSocket(sPort);
		} catch (SocketException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		seqNumber = 0;
		//scanner to get input
		sc = new Scanner(System.in); 

		while(true) {
			byte[] ackBuf = new byte[2000];
			try {

				//we make byte array output stream to which will write in our message
				bout = new ByteArrayOutputStream();
				//we wrap it in a data output stream to be able to write in the sequence number
				dout = new DataOutputStream(bout);
				dout.writeInt(seqNumber); //writing sequence number into message
				dout.close();

				m  = sc.nextLine();//getting user input for message
				bout.write(m.getBytes()); //writing message into byte array output stream

				//making packet to send
				pSend = new DatagramPacket(bout.toByteArray(), bout.size(),
						InetAddress.getLocalHost(), rPort);

				//setting packet that will receive ack message
				pACK = new DatagramPacket(ackBuf, 0, ackBuf.length);

				while(true) {
					
					//Including probability of discarding and duplicating packet
					//20% chance of discarding packet
						if(!(r.nextInt(5) % 5 == 0)) { 
							physical.send(pSend);
							//10% chance of sending duplicate package
							if(r.nextInt(10) % 10 == 0) {
								System.out.println("Sender: Duplicated the packet.");
								physical.send(pSend);
							}
						}else {
							System.out.println("Sender: Message discarded.");
						}

						//waiting for 1 second
						physical.setSoTimeout(1000);
					try {
						physical.receive(pACK);
					}catch (IOException e) {
						System.out.println("Sender: ACK wasn't received.");
						continue;
					}
					//preparing input streams. bytearray wrapped in input array to read the message as an int
					ByteArrayInputStream bin = new ByteArrayInputStream(pACK.getData());
					DataInputStream din = new DataInputStream(bin);
					int ack = din.readInt();

					//if the received message is the same as our current sequence number then increase sequence number and continue
					if(ack == seqNumber){
						System.out.println("Sender: Expected ACK number received: " + ack); //testing line
						seqNumber++;
						break;
					}
				}
			} catch (SocketException e) { 
				e.printStackTrace();
			} catch (IOException e) { 
				e.printStackTrace();
			}  

		}


	}
}
