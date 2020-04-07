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

public class UnreliableReceiver implements Runnable{
	
	private int rport;

	public UnreliableReceiver(int rport) {
		this.rport = rport;
	}

	public void receive() {
		try {
			DatagramSocket network = new DatagramSocket(rport);
			
			network.setSoTimeout(5000);
			while(true) {
				byte[] buf = new byte[2000];
				DatagramPacket pReceive = new DatagramPacket(buf, 0, buf.length);
				network.receive(pReceive);

				String m = new String(pReceive.getData());
				System.out.println("Message received: " + m);
			}
			
			
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Timed Out. Closed");
		}
		
		
	}

	@Override
	public void run() {
		receive();
		
	}
}
