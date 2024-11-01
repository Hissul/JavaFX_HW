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
	
	public ObjectInputStream inputStream;
	public ObjectOutputStream outputStream;
	public String result;
	
	public Boolean connect() {
		
		try {
			socket = new Socket(host, port);			
			return true;
		} 
		catch (IOException e) {			
			System.out.println("connect function -> " + e);
		}
		
		return false;
	}
	
	
	public Boolean disconnect() {
		
		if(socket.isConnected()) {
			
			try {
				socket.close();
				
				return true;
			} 
			catch (IOException e) {			
				System.out.println("disconnect function -> " + e);
			}
			
			return false;
		}	
		
		return false;
	}
	
	
	public Boolean sandMessage(String message){		
		
		if(socket.isConnected()) {
			try {			
				
				outputStream.writeObject(message);
				
				return true;
			} 
			catch (IOException e) {
				System.out.println("sandMessage function -> " + e);
			}
			
			System.out.println("\"" + message + "\" sent on server" );
			return false;
		}		
		
		return false;
	}

	

	public void listenServer(VBox chastVBox) {
		
		CompletableFuture.runAsync(() -> {
			
			try {							
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				outputStream.flush();
				
				inputStream = new ObjectInputStream(socket.getInputStream());
				
				while(socket.isConnected()) {					
					try {
						result = (String) inputStream.readObject();
						
						System.out.println(result);
						
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
