package application;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("menuBarIA.fxml"));
			Screen screen = Screen.getPrimary();
			//double screenWidth = screen.getBounds().getWidth();
			//double screenHeight = screen.getBounds().getHeight();
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}


/*
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Letter letterO = new Letter('O', 50);
        Letter letterX = new Letter('X', 50);
        
        // Allumer la lettre O
        letterO.setLit(true);
        
        HBox root = new HBox(letterO, letterX);
        Scene scene = new Scene(root, 200, 100);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


*/