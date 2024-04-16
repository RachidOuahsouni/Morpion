package application;

import java.util.HashMap;

import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import ai.Test;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class progressBarControler {
	String chemin = "C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\models";

	@FXML
	private Button startButton;

	@FXML
	private Label affichageLabel;

	@FXML
	public void pressStartButton() {

		MultiLayerPerceptron net = null;
		int currentEpoch;
		int size = 9;
		affichageLabel.setText("START TRADING.....");
		int[] layers = new int[] { size, 1, size };

		System.out.println("---");
		System.out.println("Load data ...");
		HashMap<Integer, Coup> mapTrain = Test.loadCoupsFromFile("resources/train/train.txt");

		System.out.println("---");

		if (net == null) {
			net = new MultiLayerPerceptron(layers, choixIAControler.CONFIG.learningRate,
					new SigmoidalTransferFunction());
			currentEpoch = 0;
		}
		/*
		String path = chemin + "\\model_" + choixIAControler.CONFIG.hiddenLayerSize + "_"
				+ choixIAControler.CONFIG.learningRate + "_" + choixIAControler.CONFIG.numberOfhiddenLayers + ".srl";
		net.save(path);
		*/

	}

}
