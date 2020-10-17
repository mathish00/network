package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

	public InetSocketAddress address;
	
	public static void main(String[] args) {
		Client client = new Client("localhost", 8000);

	}
	
	public Client(String hostname, int port) {
		address = new InetSocketAddress(hostname, port);
	}
	
	public void sendMessage(String msg) {
		try {
			Socket socket = new Socket();
			socket.connect(address, 10000);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			pw.write(msg);
			pw.flush();
			
			pw.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
