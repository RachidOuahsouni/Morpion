package application;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

import ai.Config;
import ai.ConfigFileLoader;
import ai.MultiLayerPerceptron;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MenuControler {

	@FXML
	protected Button choixIA;

	@FXML
	private MenuItem menuItemSettings;

	@FXML
	private MenuItem menuItemModels;

	public static Config CONFIG = new Config("D", 0, 0, 0);
	@FXML
	protected CheckBox facileButton;

	@FXML
	protected CheckBox moyenButton;

	@FXML
	protected CheckBox difficileButton;

	String chemin = "C:\\Users\\HP\\Desktop\\PremProjet\\resources\\models";
	
	
	@FXML
	public void settings() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("settingsBar.fxml"));
			Parent settingsRoot = loader.load();
			Scene settingsScene = new Scene(settingsRoot);

			// Obtenir le contrôleur SettingsController
			SettingsController settingsController = loader.getController();
			// Appeler la méthode show() pour remplir les champs de texte
			settingsController.show();

			Stage settingsStage = new Stage();
			settingsStage.setScene(settingsScene);
			settingsStage.setTitle("Settings");
			settingsStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void moduls() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("modulsBar.fxml"));
			Parent modulsRoot = loader.load();
			Scene modulsScene = new Scene(modulsRoot);

			/*
			// Obtenir le contrôleur SettingsController
			SettingsController modulsController = loader.getController();
			// Appeler la méthode show() pour remplir les champs de texte
			modulsController.show();
			*/

			Stage modulsStage = new Stage();
			modulsStage.setScene(modulsScene);
			modulsStage.setTitle("Moduls");
			modulsStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*
	@FXML
	protected void choixIA(ActionEvent event) {
		try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("choixIA.fxml"));
			Screen screen = Screen.getPrimary();
			double screenWidth = screen.getBounds().getWidth();
			double screenHeight = screen.getBounds().getHeight();
			Scene scene = new Scene(root, screenWidth, screenHeight);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	

	@FXML
	protected void choixFait(ActionEvent event) {
		String level;
		ConfigFileLoader configFileLoader = new ConfigFileLoader();
		configFileLoader.loadConfigFile("resources/config.txt");

		if (facileButton.isSelected() ) {
			level = "F";
			CONFIG = configFileLoader.get("F");

		} else if (moyenButton.isSelected()) {
			level = "M";
			CONFIG = configFileLoader.get("M");

		} else if (difficileButton.isSelected()) {
			level = "D";
			CONFIG = configFileLoader.get("D");

		}

		Boolean verifie = verifieModel(CONFIG.hiddenLayerSize, CONFIG.numberOfhiddenLayers, CONFIG.learningRate);
		String path = chemin + "\\model_" + CONFIG.hiddenLayerSize + "_" + CONFIG.learningRate + "_"
				+ CONFIG.numberOfhiddenLayers + ".srl";

		if (verifie) {
			// load model
			// charger la page fxml du jeu Human vs IA

			try {
				MultiLayerPerceptron mlp = MultiLayerPerceptron.load(path);

				Parent root = FXMLLoader.load(getClass().getResource("humanVsIa.fxml"));
				Scene scene = new Scene(root);
				Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				parentStage.setScene(scene);
				parentStage.show();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (!verifie) {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("learn.fxml"));
				Parent learnRoot = loader.load();
				Scene learnScene = new Scene(learnRoot);

				Stage learnStage = new Stage();
				learnStage.setScene(learnScene);
				learnStage.setTitle("learn");
				learnStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * 
		 * Parent root = FXMLLoader.load(getClass().getResource("progressbar.fxml"));
		 * Stage stage = new Stage(); stage.initModality(Modality.APPLICATION_MODAL);
		 * stage.setTitle("Learning"); stage.setScene(new Scene(root));
		 * stage.setResizable(false); stage.show();
		 * 
		 */

	}

	public Boolean verifieModel(int hiddenLayerSize, int numberOfhiddenLayers, double learningRate) {
		String path = chemin + "\\model_" + hiddenLayerSize + "_" + learningRate + "_" + numberOfhiddenLayers + ".srl";
		File file = new File(path);

		if (file.exists()) {
			return true;
		} else
			return false;
	}
}
