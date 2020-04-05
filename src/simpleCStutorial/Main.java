package simpleCStutorial;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		int port = 50002;
		sServer server = new sServer(port);
		cClient client = new cClient(port);
		
		ExecutorService execServ = Executors.newFixedThreadPool(2);
		execServ.submit(client);
		execServ.submit(server);
		
	}
}
