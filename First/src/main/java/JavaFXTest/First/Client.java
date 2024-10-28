package JavaFXTest.First;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Client {
	
	private static String host = "localhost";
	private static int port = 9463;
	public Socket socket;
	
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	
	public void connect() {
		
		try {
			socket = new Socket(host, port);
		} 
		catch (IOException e) {			
			System.out.println("connect function -> " + e);
		}
	}
	
	
	public void disconnect() {
		
		if(socket.isConnected()) {
			
			try {
				socket.close();
			} 
			catch (IOException e) {			
				System.out.println("disconnect function -> " + e);
			}
		}		
	}
	
	
	public void sandMessage(String message){		
		
		if(socket.isConnected()) {
			try {			
				
				outputStream.writeObject(message);
				
			} 
			catch (IOException e) {
				System.out.println("sandMessage function -> " + e);
			}
			
			System.out.println("\"" + message + "\" sent on server" );
		}				
	}
	

	
	public String reseiveMessage() {
		
		String result = "";	
		
		if(socket.isConnected()) {
			try {
				result = (String) inputStream.readObject();
			} 
			catch (IOException | ClassNotFoundException e) {			
				System.out.println("reseiveMessage function -> " + e);
			}
		}
		
		return result;
	}
	

	public void listenServer(VBox chastVBox) {
		
		CompletableFuture.runAsync(() -> {
			
			try {			
				
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				outputStream.flush();
				
				inputStream = new ObjectInputStream(socket.getInputStream());
				
				while(socket.isConnected()) {					
					try {
						String result = (String) inputStream.readObject();
						
						Platform.runLater(() -> {
							chastVBox.getChildren().add(new Label(result));
						});
					} 
					catch (ClassNotFoundException e) {						
						System.out.println(e);
					}
				}
			} 
			catch (IOException e) {				
				System.out.println("listenServer function -> " + e);
			}
		});
	}

}
