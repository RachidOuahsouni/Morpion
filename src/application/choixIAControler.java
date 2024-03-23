package application;

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

public class choixIAControler {

	public static Config CONFIG = new Config("D", 0, 0, 0);
	@FXML
	protected Button facileButton;

	@FXML
	protected Button moyenButton;

	@FXML
	protected Button difficileButton;

	String chemin = "C:\\Users\\HP\\Desktop\\PremProjet\\resources\\models";

	@FXML
	protected void choixFait(ActionEvent event) {
		String level;
		Button clickedButton = (Button) event.getSource();
		ConfigFileLoader configFileLoader = new ConfigFileLoader();
		configFileLoader.loadConfigFile("resources/config.txt");

		if (clickedButton == facileButton) {
			level = "F";
			CONFIG = configFileLoader.get("F");

		} else if (clickedButton == moyenButton) {
			level = "M";
			CONFIG = configFileLoader.get("M");

		} else if (clickedButton == difficileButton) {
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
