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
import java.util.Random;
import java.util.Scanner;

public class UnreliableSender implements Runnable{

	int sport;
	int rport;
	
	Random r = new Random();
	public UnreliableSender(int sport, int rport) {
		this.sport = sport;
		this.rport = rport;
	}

	public void send() {

			
		try {
			DatagramSocket sout = new DatagramSocket(sport);	
			Scanner sc = new Scanner(System.in);
			while(true) {
				String m = sc.nextLine();
				DatagramPacket pout = new DatagramPacket(m.getBytes(), m.length(),
						InetAddress.getLocalHost(), rport);
				//20% chance of discarding packet
				if(!(r.nextInt(5) % 5 == 0)) {
					sout.send(pout);
					//10% chance of sending duplicate package
					if(r.nextInt(10) % 10 == 0) 
						sout.send(pout);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		send();
		
	}
	
}
