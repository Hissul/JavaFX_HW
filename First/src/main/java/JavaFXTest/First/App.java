package JavaFXTest.First;


import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class App extends Application {
	
	public String name = "";
	static Client server;
	static VBox chastVBox = new VBox();

    @Override
    public void start(Stage stage) {

    	Label label = new Label("Tipe your name");
    	label.setPadding(new Insets(20));
    	
    Button connectButton = new Button("Connect");
    connectButton.setMaxWidth(200);
    connectButton.setDisable(true);
    	
    TextField nameField = new TextField();
    	nameField.setMaxWidth(200);
    	
    	//событие TextField
    	nameField.textProperty().addListener(new ChangeListener() {  
    		
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				
				if(nameField.getText().length() > 0) {
					 connectButton.setDisable(false);
				}
				else {
					 connectButton.setDisable(true);
				}
			}
    	});    	

    
    //событие ConnectButton
    connectButton.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			
			name = nameField.getText();
			
			server = new Client();
			server.connect();	
			
			
			server.ListenServer(chastVBox);
			
			
			Button disconectButton = new Button("Disconect");
			
			 //событие DisconectButton
			disconectButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {					
					server.disconnect();
					
					stage.close();
				}				
			});
			
			HBox disHbox = new HBox(disconectButton);
			disHbox.setAlignment(Pos.TOP_RIGHT);
			
			TextField message = new TextField();
			message.setMaxWidth(480);
			message.setMinWidth(480);			

			chastVBox.setPadding(new Insets(0, 0 , 400 , 0));
			
			Button sendButton = new Button("Send");
			
			//событие SendButton
			sendButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					if(message.getText().length() < 1) {
						return;
					}
					
					server.sandMessage(name + " : " + message.getText());
				}
				
			});
			
			HBox hbox = new HBox(message,sendButton);
			hbox.setMaxWidth(640);
			hbox.setAlignment(Pos.BOTTOM_CENTER);
			
			
			VBox vbox = new VBox(disHbox, chastVBox, hbox);
			
			
			var newScene = new Scene(new StackPane(vbox), 640, 480);
			 
	        stage.setScene(newScene);	
	        stage.show();
	        
	        
		}
    	
    });
    	

    	VBox vbox = new VBox(label, nameField, connectButton);
    	vbox.setAlignment(Pos.CENTER);    	

    	vbox.setMargin(connectButton, new Insets(20));    	
    
        
    var scene = new Scene(new StackPane(vbox), 640, 480);
    stage.setScene(scene);
    stage.show();  
   }
    
    
    
    
    public class ReseiveFromSevrer implements Callable {

		@Override
		public Object call() throws Exception {
			
			System.out.println("RUN START");
			
			
				System.out.println("RUN START WHILE");
				String fromServer = server.reseiveMessage();			
				chastVBox.getChildren().add(new Label(fromServer));
			
			return null;
		}
    }
    
    
    

    public static void main(String[] args) {
        launch();
    }

}


