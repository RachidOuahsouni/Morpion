package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ai.ConfigFileLoader;
import ai.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController implements Initializable {

	@FXML
	private TextField fTextField1;

	@FXML
	private TextField fTextField2;

	@FXML
	private TextField fTextField3;

	@FXML
	private TextField mTextField1;

	@FXML
	private TextField mTextField2;

	@FXML
	private TextField mTextField3;

	@FXML
	private TextField dTextField1;

	@FXML
	private TextField dTextField2;

	@FXML
	private TextField dTextField3;

	@FXML
	private Button save;

	public String lastCoise = "";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void show() {
		String filePath = "C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\config.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			int lineNumber = 0;
			while ((line = br.readLine()) != null) {
				lineNumber++;
				if (lineNumber == 4) {

					lastCoise = line;
					break;

				}
			}
		} catch (IOException e) {
			System.err.println("Erreur de lecture du fichier : " + e.getMessage());
		}

		ConfigFileLoader configFileLoader = new ConfigFileLoader();
		configFileLoader.loadConfigFile(filePath);

		Config configF = configFileLoader.get("F");
		Config configM = configFileLoader.get("M");
		Config configD = configFileLoader.get("D");

		if (configF != null) {
			fTextField1.setText(String.valueOf(configF.hiddenLayerSize));
			fTextField2.setText(String.valueOf(configF.learningRate));
			fTextField3.setText(String.valueOf(configF.numberOfhiddenLayers));
		}

		if (configM != null) {
			mTextField1.setText(String.valueOf(configM.hiddenLayerSize));
			mTextField2.setText(String.valueOf(configM.learningRate));
			mTextField3.setText(String.valueOf(configM.numberOfhiddenLayers));
		}

		if (configD != null) {
			dTextField1.setText(String.valueOf(configD.hiddenLayerSize));
			dTextField2.setText(String.valueOf(configD.learningRate));
			dTextField3.setText(String.valueOf(configD.numberOfhiddenLayers));
		}
	}

	@FXML
	public void save() {
		String filePath = "C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\config.txt";

		// Mettre à jour le contenu du fichier directement
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			// Écrire les nouvelles configurations dans le fichier
			writeConfig(writer, "F", fTextField1.getText(), fTextField2.getText(), fTextField3.getText());
			writeConfig(writer, "M", mTextField1.getText(), mTextField2.getText(), mTextField3.getText());
			writeConfig(writer, "D", dTextField1.getText(), dTextField2.getText(), dTextField3.getText());
			writer.write(lastCoise);
			// Fermer la fenêtre après avoir enregistré les configurations
			closeWindow();
		} catch (IOException e) {
			e.printStackTrace();
			// Gérer les erreurs d'écriture dans le fichier ici
		}
	}

	private void writeConfig(BufferedWriter writer, String level, String hiddenLayerSize, String learningRate,
			String numberOfHiddenLayers) throws IOException {
		// Écrire une ligne dans le fichier pour la configuration donnée
		writer.write(level + ":" + hiddenLayerSize + ":" + learningRate + ":" + numberOfHiddenLayers);
		writer.newLine(); // Ajouter un saut de ligne
	}

	// Méthode pour fermer la fenêtre
	private void closeWindow() {
		// Récupérer la scène parente à partir du bouton
		Stage stage = (Stage) save.getScene().getWindow();
		// Fermer la fenêtre
		stage.close();
	}

}
