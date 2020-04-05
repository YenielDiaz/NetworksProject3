package simpleCStutorial;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class cClient implements Runnable{

	private final int port;
	
	public cClient(int port) {
		this.port = port;
	}
	@Override
	public void run() {
		try {
			//put port in which you expect to receive messages
			DatagramSocket clientSocket = new DatagramSocket(port);
			
			//create buffer in which we will receive the data
			byte[] buffer = new byte[65507]; //65507 is max UDP packet payload
			
			//if you do not receive anything in the amount of ms specified
			//it will throw a timeout exception
			clientSocket.setSoTimeout(3000);
			while(true) {
				/* This DatagramPacket takes as input:
				 *  the buffer where message will be stored
				 *  the offset of the message which will be 0
				 *  the size of the message
				 */
				DatagramPacket dpacket = new DatagramPacket(buffer, 0, buffer.length);
				
				//now we receive the data
				clientSocket.receive(dpacket);
				
				//put messages in strings and print them
				String receivedMessage = new String(dpacket.getData());
				System.out.println(receivedMessage);
			}
		
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Timeout. Client is closing");
		}
		
	}

}
