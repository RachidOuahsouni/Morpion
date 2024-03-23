package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class ModulsController implements Initializable {

	@FXML
	Button deleteButton;

	@FXML
	Label label1;

	@FXML
	Label label2;

	@FXML
	Label label3;

	@FXML
	CheckBox check1;

	@FXML
	CheckBox check2;

	@FXML
	CheckBox check3;

	public void show() {
		File directory = new File("C:\\Users\\HP\\Desktop\\PremProjet\\resources\\models");

		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				int nbr = 0;
				for (File file : files) {
					nbr++;
					if (nbr == 1)
						label1.setText(file.getName());
					else if (nbr == 2)
						label2.setText(file.getName());
					else if (nbr == 3)
						label3.setText(file.getName());

				}
				if (nbr == 0) {
					label3.setVisible(false);
					check3.setVisible(false);
					label2.setVisible(false);
					check2.setVisible(false);
					label1.setVisible(false);
					check1.setVisible(false);
				} else if (nbr == 1) {
					label3.setVisible(false);
					check3.setVisible(false);
					label2.setVisible(false);
					check2.setVisible(false);
				} else if (nbr == 2) {
					label3.setVisible(false);
					check3.setVisible(false);
				}
			}
		} else {
			System.err.println("Le chemin spécifié n'est pas un répertoire.");
		}
		
	}

	@FXML
	public void delete(ActionEvent event) {
		if (check1.isSelected()) {
			String path = "C:\\Users\\HP\\Desktop\\PremProjet\\resources\\models" + "\\" + label1.getText();
			File file = new File(path);

			if (file.exists()) {
				if (file.delete()) {
					System.out.println("Le fichier a été supprimé avec succès.");
				} else {
					System.out.println("Impossible de supprimer le fichier.");
				}
			} else {
				System.out.println("Le fichier spécifié n'existe pas.");
			}

		}
		if (check2.isSelected()) {
			String path = "C:\\Users\\HP\\Desktop\\PremProjet\\resources\\models" + "\\" + label2.getText();
			File file = new File(path);

			if (file.exists()) {
				if (file.delete()) {
					System.out.println("Le fichier a été supprimé avec succès.");
				} else {
					System.out.println("Impossible de supprimer le fichier.");
				}
			} else {
				System.out.println("Le fichier spécifié n'existe pas.");
			}

		}
		if (check3.isSelected()) {
			String path = "C:\\Users\\HP\\Desktop\\PremProjet\\resources\\models" + "\\" + label3.getText();
			File file = new File(path);

			if (file.exists()) {
				if (file.delete()) {
					System.out.println("Le fichier a été supprimé avec succès.");
				} else {
					System.out.println("Impossible de supprimer le fichier.");
				}
			} else {
				System.out.println("Le fichier spécifié n'existe pas.");
			}

		}
		show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		show();

	}

}
