package actualProject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	
	public static void main(String[] args) {
		int rPort = 49004;
		int sPort = 49005;
		Sender send = new Sender(rPort, sPort);
		Receiver receive = new Receiver(rPort, sPort);
		
		ExecutorService execServ = Executors.newFixedThreadPool(2);
		execServ.submit(receive);
		execServ.submit(send);

		
	}
	

}
