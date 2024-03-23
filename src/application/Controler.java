package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controler implements Initializable {

	@FXML
	private Button button1;
	
	@FXML 
	private Button diminuer;

	@FXML
	private Circle circle;
	/*
	@FXML
    private ChoiceBox<String> myChoiceBox;
	*/

/*
	@FXML
	protected void setings(ActionEvent e) {
		try {

			
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	*/
	@FXML
	protected void click(ActionEvent e) {

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), circle);
		scaleTransition.setToX(2.0);
		scaleTransition.setToY(2.0);

		scaleTransition.play();
		
		
		
	}

	@FXML
	protected void click2(ActionEvent e) {

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), circle);
		scaleTransition.setToX(1.0);
		scaleTransition.setToY(1.0);

		scaleTransition.play();
		
		
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
