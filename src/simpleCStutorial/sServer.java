package simpleCStutorial;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class sServer implements Runnable{

	private final int clientPort;
	private final int messageCount = 3;
	
	public sServer(int port) {
		this.clientPort  = port;
	}
	
	
	@Override
	public void run() {
		//create socket
			try {
				//port you use for socket does not matter as long as its not used
				DatagramSocket serverSocket = new DatagramSocket(50003);
				
				//generate messages that will be sent
				for(int i=0; i<messageCount; i++) {
					
					String message = "Message number " + i;	
					
					//wrap strings in DatagramPackets
					/*DatagramPacket takes:
					 * 	message that will be sent in byte array form
					 * 	length of the message
					 *  ip address of the client in this case localhost
					 *  port the client is using
					 */
					
					DatagramPacket p = new DatagramPacket(message.getBytes(),
							message.length(),
							InetAddress.getLocalHost(),
							clientPort);
					
					//send to the socket
					serverSocket.send(p);
				}
			} catch (SocketException e) {
				System.out.println("SOCKETEXCEPTION");
			} catch (UnknownHostException e) {
				System.out.println("UNKNOWNHOSTEXCEPTION");
			} catch (IOException e) {
				System.out.println("IOEXCEPTION");
			}
		
		
	}
	

}
