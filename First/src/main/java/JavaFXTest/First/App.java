package JavaFXTest.First;

import java.awt.Desktop;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class App extends Application {
	
    @Override
    public void start(Stage stage) {  
    	
    		stage.setTitle("Client window");   		
    		
    		
    	
    		Label buttonLabel = new Label("CLIENT WINDOW");
        
        Button openFileButton = new Button("Choose a file ...");
        openFileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Pick file");
				
				File file = fileChooser.showOpenDialog(stage);
				
				if(file != null) {						
					
					// copy the file in new directory
					ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
					
					CopyClass copyClass = new CopyClass(file);

					Future<File> result = executor.submit(copyClass);					
					
					File newFile = null;
					
					try {
						newFile = result.get();
					} catch (InterruptedException | ExecutionException e) {						
						e.printStackTrace();
					}				
							
					
					if(newFile != null) {	
						
						 ServerWindow server = new ServerWindow(file);
						 
						try {
							// server window
							server.start(stage);
						} catch (Exception e) {						
							e.printStackTrace();
						}
					}
				}
			}
        	
        });
        
        
        
        
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(buttonLabel, openFileButton);
        
        var scene = new Scene(new StackPane(vbox), 640, 480);
        
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}


