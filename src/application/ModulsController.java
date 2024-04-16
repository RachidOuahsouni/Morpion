package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import ai.Config;
import ai.ConfigFileLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModulsController implements Initializable {

	@FXML
	Button deleteButton;

	@FXML
	ListView listView;
	
	

	public void show() {
		
		
		listView.getItems().clear();
        File directory = new File("C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\models");
        List<Label> labels = new ArrayList<>(); // Liste pour contenir les labels
        List<CheckBox> checkBoxes = new ArrayList<>(); // Liste pour contenir les cases à cocher

        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    Label label = new Label(file.getName()); // Créer un label pour chaque fichier
                    labels.add(label); // Ajouter le label à la liste
                }
            }
        }

        listView.getItems().addAll(labels); // Ajouter les labels à la ListView
    }

	@FXML
	public void delete(ActionEvent event) {
	    Label selectedLabel = (Label) listView.getSelectionModel().getSelectedItem(); // Récupérer le label sélectionné

	    if (selectedLabel != null) {
	        String fileName = selectedLabel.getText();
	        String filePath = "C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\models\\" + fileName;
	        File file = new File(filePath);

	        if (file.exists()) {
	            if (file.delete()) {
	                System.out.println("Le fichier " + fileName + " a été supprimé avec succès.");
	                listView.getItems().remove(selectedLabel); // Supprimer l'élément sélectionné de la ListView

	            } else {
	                System.out.println("Impossible de supprimer le fichier " + fileName + ".");
	            }
	        } else {
	            System.out.println("Le fichier " + fileName + " n'existe pas.");
	        }

	    } else {
	        System.out.println("Veuillez sélectionner un fichier à supprimer.");
	    }
	    listView.getSelectionModel().clearSelection();
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        
		show();
		
	}
	
	

}
