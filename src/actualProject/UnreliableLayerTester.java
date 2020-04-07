package actualProject;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnreliableLayerTester {

	public static void main(String[] args) {
		int rPort = 4002;
		int sPort = 4003;
		
		UnreliableSender send = new UnreliableSender(sPort, rPort);
		UnreliableReceiver receive = new UnreliableReceiver(rPort);
		
		ExecutorService execServ = Executors.newFixedThreadPool(2);
		execServ.submit(receive);
		execServ.submit(send);
		

	}

}
