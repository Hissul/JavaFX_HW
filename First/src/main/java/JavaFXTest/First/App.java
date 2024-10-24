package JavaFXTest.First;


import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class App extends Application {

    @Override
    public void start(Stage stage) {

    	Label label = new Label("Your message");
    	
    	TextField textField = new TextField();
    	
    	Button button = new Button("Send to server");
    	button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {	
				
				if(textField.getText() == null) {
					return;
				}
				
				// sending  message to server
				Client server = new Client();
				
				server.connect();
				
				try {
					
					server.say(textField.getText());
					
					System.out.println(server.hear());
						
				} 
				catch (IOException e) {			
					System.out.println(e);
				}
				
				textField.setText("");
			}
    		
    	});
    	
    	Label labelFile = new Label("Choose file");
    	Button fileButton = new Button("Push");
    	fileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Pick file");
				
				File file = fileChooser.showOpenDialog(stage);
				
				if(file != null) {	
					
					Client server = new Client();
					
					server.connect();
					
					try {						
						server.sendFile(file);
						
						System.out.println(server.hear());
						
					} 
					catch (IOException e) {						
						System.out.println(e);
					}
					
				}			
			}
    		
    	});
    	
    	VBox vbox = new VBox(label, textField, button, labelFile, fileButton);
    	vbox.setAlignment(Pos.CENTER);
    	
    	vbox.setMargin(textField, new Insets(10, 50, 10, 50));
    	vbox.setMargin(button, new Insets(0, 0, 70, 0));
    	
    	vbox.setMargin(labelFile, new Insets(10, 0, 10, 0));
        
        var scene = new Scene(new StackPane(vbox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}


