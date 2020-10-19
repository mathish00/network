package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.json.*;

public class Server {

	public int[] ports;
	
	public static void main(String[] args) {
		Server s = new Server();
		s.startListening();

	}
	
	public Server() {
		createPorts();
	}
	
	public void startListening() {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("[Server] Server is starting");
				while(true) {
					try {
						//get ready for connections
						ServerSocket socket = new ServerSocket(ports[0]);
						System.out.println("[Server port 0] Waiting for connections");
						Socket client = socket.accept();
						System.out.println("[Server port 0] Connection established");
						
						//receive a message
						Scanner s = new Scanner(new BufferedReader(new InputStreamReader(client.getInputStream())));
						
						JSONObject reception = new JSONObject(s.next());
						//TODO: on which port can the server find the specified recipient?
						String recipient = reception.getString("recipient");
						
						System.out.println("[Server port 0] " + reception.get("message"));
						
						//send an answer to the client 
						PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
						pw.write("This is an auto-generated answer by the server.");
						pw.flush();
						
						
						//close 
						pw.close();
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
	
	/**
	 * Create 10 ports for possible clients
	 * @return
	 */
	private void createPorts() {
		int[] ports = new int[10];
		for(int i = 0; i < 10; i++)
			ports[i] = 8000 + i;
		this.ports = ports;
	}

}
