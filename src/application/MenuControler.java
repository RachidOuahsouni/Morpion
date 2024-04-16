package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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

public class MenuControler implements Initializable {

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

	String chemin = "C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\models";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// cocher la case par-défaut des niveaux

		try (BufferedReader br = new BufferedReader(new FileReader("resources/config.txt"))) {
			String line;
			int lineNumber = 0;
			while ((line = br.readLine()) != null) {
				lineNumber++;
				if (lineNumber == 4) {
					if (line.equals("F")) {
						facileButton.setSelected(true);
					} else if (line.equals("M")) {
						moyenButton.setSelected(true);
					} else if (line.equals("D")) {
						difficileButton.setSelected(true);
					}
					break;
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur de lecture du fichier : " + e.getMessage());
		}
		/////////////

	}

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

			Stage modulsStage = new Stage();
			modulsStage.setScene(modulsScene);
			modulsStage.setTitle("IA Modules");
			
			modulsStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void human(ActionEvent event) {
		try {

			Parent root = FXMLLoader.load(getClass().getResource("humanVsHuman.fxml"));
			Scene scene = new Scene(root);
			Stage parentStage = new Stage();
			parentStage.setScene(scene);
			parentStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void choixFait(ActionEvent event) {

		String level = null;
		ConfigFileLoader configFileLoader = new ConfigFileLoader();
		configFileLoader.loadConfigFile("resources/config.txt");

		if (facileButton.isSelected()) {
			level = "F";
			CONFIG = configFileLoader.get("F");

		} else if (moyenButton.isSelected()) {
			level = "M";
			CONFIG = configFileLoader.get("M");

		} else if (difficileButton.isSelected()) {
			level = "D";
			CONFIG = configFileLoader.get("D");

		}

		// enregestrer le niveau de la dernire partie dans config.txt
		String filePath = "resources/config.txt";
		int lineNumberToReplace = 4;
		String replacementLine = level;

		// Lecture du contenu du fichier et remplacement de la ligne
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			int lineNumber = 0;
			while ((line = reader.readLine()) != null) {
				lineNumber++;
				if (lineNumber == lineNumberToReplace) {
					lines.add(replacementLine); // Remplace la ligne
				} else {
					lines.add(line); // Conserver les autres lignes inchangées
				}
			}
		} catch (IOException e) {
			System.err.println("Erreur de lecture du fichier : " + e.getMessage());
			return;
		}

		// Écriture du contenu mis à jour dans le fichier
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Erreur d'écriture dans le fichier : " + e.getMessage());
			return;
		}

		System.out.println("Ligne remplacée avec succès dans le fichier.");
		///////

		if (facileButton.isSelected() || moyenButton.isSelected() || difficileButton.isSelected()) {

			Boolean verifie = verifieModel(CONFIG.hiddenLayerSize, CONFIG.numberOfhiddenLayers, CONFIG.learningRate);
			String path = chemin + "\\model_" + CONFIG.hiddenLayerSize + "_" + CONFIG.learningRate + "_"
					+ CONFIG.numberOfhiddenLayers + ".srl";

			if (verifie) {
				// load model
				// charger la page fxml du jeu Human vs IA

				try {
					HumanVsIaController.NET = MultiLayerPerceptron.load(path);

					Parent root = FXMLLoader.load(getClass().getResource("humanVsIa.fxml"));
					Scene scene = new Scene(root);
					Stage parentStage = new Stage();
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

		} else {
			System.out.println("choisir le niveau de difficulté");
		}

	}

	public Boolean verifieModel(int hiddenLayerSize, int numberOfhiddenLayers, double learningRate) {
		String path = chemin + "\\model_" + hiddenLayerSize + "_" + learningRate + "_" + numberOfhiddenLayers + ".srl";
		File file = new File(path);

		if (file.exists()) {
			return true;
		} else
			return false;
	}

	@FXML
	private void onFacileSelected(ActionEvent event) {
		if (facileButton.isSelected()) {
			moyenButton.setSelected(false);
			difficileButton.setSelected(false);
		}
	}

	@FXML
	private void onMoyenSelected(ActionEvent event) {
		if (moyenButton.isSelected()) {
			facileButton.setSelected(false);
			difficileButton.setSelected(false);
		}
	}

	@FXML
	private void onDifficileSelected(ActionEvent event) {
		if (difficileButton.isSelected()) {
			facileButton.setSelected(false);
			moyenButton.setSelected(false);
		}
	}
}
