package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import org.json.*;

public class Client {

	public InetSocketAddress address;
	Socket server;
	
	public static void main(String[] args) {
		Client client = new Client("localhost", 8000);
		try {
			client.sendMessage("gude!", "Mathis");
		} catch (InterruptedException e) {
			System.out.println("[Client] An error occurred.");
		}
		

	}
	
	public void connectToServer() {
		server = new Socket();
		try {
			server.connect(address, 10000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disconnectFromServer() {
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Client(String hostname, int port) {
		address = new InetSocketAddress(hostname, port);
	}
	
	public void sendMessage(String msg, String recipient) throws InterruptedException {
		try {
			
			JSONObject jo = new JSONObject();
			jo.putOpt("recipient", recipient);
			jo.putOpt("message", msg);
			
			PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(server.getOutputStream())));

			pw.write(jo.toString());
			pw.flush();
			
			Scanner s =  new Scanner(new BufferedReader(new InputStreamReader(server.getInputStream())));
			//this.wait(2000);
			//while(s.hasNext())
			//	System.out.println("[Client] Received answer by the server: " + s.next());
			
			s.close();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
