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
	Integer lossProbability = 1;
	Integer duplicateProbability = 9; //has to start at different otherwise loss probability will override duplicate

	public Sender(int rport, int sport) {
		this.rPort = rport;
		this.sPort = sport;
	}


	//send method
	@Override
	public void run() {

		DatagramSocket physical = null;
		try {
			physical = new DatagramSocket(sPort);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		byte[] ackBuf = new byte[2000];
		seqNumber = 0;

		//this is supposed to be while(true) but im doing it a limited amount of times to test
		while(seqNumber < 40) {
			try {
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
				//Including probability of discarding packet

				if(! (lossProbability % 5 ==0)) {
					physical.send(pSend);
				}
				if(duplicateProbability %10 == 0) {
					physical.send(pSend);
					//physical.send(pSend);
				}
				//physical.send(pSend);

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
				System.out.println("*lossProbapility = " + lossProbability + "\n**duplicateProbabilit = "+ duplicateProbability );
				lossProbability++;
				duplicateProbability++;
				seqNumber++;
				bout.close();
			} catch (SocketException e) {
				e.printStackTrace();
			}  catch (IOException e) {
				System.out.println("Timed out. Did not receive ACK");
				lossProbability++;
			}
		}



	}

}
