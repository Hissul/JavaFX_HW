package JavaFXTest.First;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	private String host;
	private int port;
	private Socket socket;
	
	
	public Client() {
		this.host = "localhost";
		this.port = 9463;
	}
	
	
	public void connect() {
		
		try {
			socket = new Socket(host, port);
		} 
		catch (IOException e) {			
			System.out.println(e);
		}
	}
	
	
	public void disconnect() {
		
		try {
			socket.close();
		} 
		catch (IOException e) {			
			System.out.println(e);
		}
	}
	
	
	public void say(String message) throws IOException {
		
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		
		outputStream.writeObject("text");
		
		System.out.println("Type -> text <- sent on server" );		
		
		
		outputStream.writeObject(message);
		
		System.out.println("\"" + message + "\" sent on server" );
		
		//outputStream.close();
	}
	

	
	public String hear() throws IOException {
		
		String result = "";
		
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		
		try {
			result = (String) inputStream.readObject();
		} 
		catch (ClassNotFoundException | IOException e) {			
			System.out.println(e);
		}
		
		inputStream.close();
		
		return result;
	}
	
	public void sendFile(File file) throws IOException {
		
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		
		outputStream.writeObject("file");
		
		System.out.println("Type -> file <- sent on server" );
		
		outputStream.writeObject(file);
		
		System.out.println("File sent on server" );
		
		//outputStream.close();
	}

}
