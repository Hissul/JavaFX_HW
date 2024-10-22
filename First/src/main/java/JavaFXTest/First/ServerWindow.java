package JavaFXTest.First;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerWindow extends Application  {
	
	private Desktop desktop = Desktop.getDesktop();
	
	public File file;
	
	public ServerWindow(File file) {
		this.file= file;
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Server window");
		
		Label buttonLabel = new Label("SERVER WINDOW");
		
		Button showFileButton = new Button("Show picture");
		 
		 if(file == null) {
			 showFileButton.setDisable(true);
		 }else {
			 showFileButton.setDisable(false);
		 }		 
		 
		 showFileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {	
					 desktop.open(file);
					 
				} catch (IOException e) {						
					e.printStackTrace();
				}				
				
			}			 
		 });
		 
		 VBox vbox = new VBox(10);
	     vbox.setAlignment(Pos.CENTER);
	     vbox.getChildren().addAll(buttonLabel, showFileButton);
		 
		 var serverScene = new Scene(new StackPane(vbox), 640, 480);
		 
		 primaryStage.setScene(serverScene);
		 primaryStage.show();
		
	}

}
