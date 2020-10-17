package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public int port;
	
	public static void main(String[] args) {
		new Server(8000).startListening();;

	}
	
	public Server(int port) {
		this.port = port;
	}
	
	public void startListening() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("[Server] Server is starting");
				while(true) {
					try {
						ServerSocket socket = new ServerSocket(port);
						System.out.println("[Server] Waiting for connections");
						Socket client = socket.accept();
						System.out.println("[Server] Connection established");
						Scanner s = new Scanner(new BufferedReader(new InputStreamReader(client.getInputStream())));
						while(s.hasNext())
							System.out.println("[Server] Received message: " + s.next());
						s.close();
						client.close();
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			
		}).run();
	}

}
