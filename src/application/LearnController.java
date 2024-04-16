package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import ai.Coup;
import ai.MultiLayerPerceptron;
import ai.SigmoidalTransferFunction;
import ai.Test;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

public class LearnController {

	String chemin = "C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\models";

	@FXML
	ProgressIndicator progressIndic;

	@FXML
	Button ProgButton;

	@FXML
	Label progressInfo;

	public MultiLayerPerceptron learn(int size, HashMap<Integer, Coup> mapTrain, int h, double lr, int l,
			boolean verbose, double epochs) {
		try {
			if (verbose) {
				System.out.println();
				System.out.println("START TRAINING ...");
				System.out.println();
				progressInfo.setText("START TRAINING ...");
			}

			int[] layers = new int[l + 2];
			layers[0] = size;
			for (int i = 0; i < l; i++) {
				layers[i + 1] = h;
			}
			layers[layers.length - 1] = size;

			MultiLayerPerceptron net = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());

			Task<Double> task = new Task<Double>() {
				@Override
				protected Double call() throws Exception {
					double error = 0.0;

					if (verbose) {
						System.out.println("---");
						System.out.println("Load data ...");
						System.out.println("---");
						Platform.runLater(() -> {
							progressInfo.setText("Load data ...");
						});

					}

					for (int i = 0; i < epochs; i++) {
						Coup c = null;
						while (c == null)
							c = mapTrain.get((int) (Math.round(Math.random() * mapTrain.size())));

						error += net.backPropagate(c.in, c.out);

						if (i % 10000 == 0 && verbose)
							updateMessage("Error at step " + i + " is " + (error / (double) i));

						updateProgress(i, epochs); // Mise à jour de la progression

						if (isCancelled()) // Vérifie si la tâche a été annulée
							return error;
					}
					if (verbose) {
						System.out.println("Learning completed!");
						Platform.runLater(() -> {
							progressInfo.setText("Learning completed!");

						});
					}

					// Mettre à jour l'interface utilisateur lorsque la progression est terminée
					Platform.runLater(() -> {
						progressIndic.progressProperty().unbind();
						progressIndic.setProgress(1.0); // Assurez-vous que la progression est à 100%
						progressIndic.setStyle("-fx-text-fill: green;"); // Définissez la couleur du texte sur vert
						// progressIndic.setDisable(true); // Désactivez le ProgressIndicator pour
						// indiquer qu'il est terminé
					});

					// Signaler que la tâche a réussi à terminer son travail
					succeeded();

					return error;
				}

			};

			progressIndic.progressProperty().unbind();

			// S'assurer que la propriété progress du ProgressIndicator est initialisée à 0
			progressIndic.setProgress(0);

			// Liaison de la propriété progress du ProgressIndicator avec la propriété
			// progress de la tâche
			progressIndic.progressProperty().bind(task.progressProperty());

			Thread thread = new Thread(task);
			thread.start();

			return net;
		} catch (Exception e) {
			System.out.println("Test.learn()");
			e.printStackTrace();
			System.exit(-1);
		}

		return null;
	}

	@FXML
	public void clickButtonProgress(ActionEvent event) throws InterruptedException, IOException {
		double epochs = 10000;
		HashMap<Integer, Coup> mapTrain = Test
				.loadCoupsFromFile("C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\train\\train.txt");
		boolean verbose = true;
		int size = 9;
		int h = MenuControler.CONFIG.hiddenLayerSize;
		double lr = MenuControler.CONFIG.learningRate;
		int l = MenuControler.CONFIG.numberOfhiddenLayers;

		try {
			if (verbose) {
				System.out.println();
				System.out.println("START TRAINING ...");
				System.out.println();
				progressInfo.setText("START TRAINING ...");
			}

			int[] layers = new int[l + 2];
			layers[0] = size;
			for (int i = 0; i < l; i++) {
				layers[i + 1] = h;
			}
			layers[layers.length - 1] = size;

			HumanVsIaController.NET = new MultiLayerPerceptron(layers, lr, new SigmoidalTransferFunction());

			Task<Double> task = new Task<Double>() {
				@Override
				protected Double call() throws Exception {
					double error = 0.0;

					if (verbose) {
						System.out.println("---");
						System.out.println("Load data ...");
						System.out.println("---");
						Platform.runLater(() -> {
							progressInfo.setText("Load data ...");
						});

					}

					for (int i = 0; i < epochs; i++) {
						Coup c = null;
						while (c == null)
							c = mapTrain.get((int) (Math.round(Math.random() * mapTrain.size())));

						error += HumanVsIaController.NET.backPropagate(c.in, c.out);

						if (i % 10000 == 0 && verbose)
							updateMessage("Error at step " + i + " is " + (error / (double) i));

						updateProgress(i, epochs); // Mise à jour de la progression

						if (isCancelled()) // Vérifie si la tâche a été annulée
							return error;
					}
					if (verbose) {
						System.out.println("Learning completed!");
						

					}

					// Mettre à jour l'interface utilisateur lorsque la progression est terminée
					Platform.runLater(() -> {
						progressInfo.setText("Learning completed!");
						progressIndic.progressProperty().unbind();
						progressIndic.setProgress(1.0); // Assurez-vous que la progression est à 100%
						progressIndic.setStyle("-fx-text-fill: green;"); // Définissez la couleur du texte sur vert
						// progressIndic.setDisable(true); // Désactivez le ProgressIndicator pour
						// indiquer qu'il est terminé
						
						
						Task<Void> playerTask = new Task<Void>() {
						    @Override
						    protected Void call() throws Exception {
						        try {
						            Thread.sleep(2000); 
						        } catch (InterruptedException e) {
						            e.printStackTrace();
						        }
						        
						        Platform.runLater(() -> {

									Parent root;
									try {
										root = FXMLLoader.load(getClass().getResource("humanVsIa.fxml"));
										Scene scene = new Scene(root);
										Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
										parentStage.setScene(scene);
										parentStage.show();
									} catch (IOException e) {
										e.printStackTrace();
									}			        });
						        return null;
						    }
						};   
						new Thread(playerTask).start();
					});

					// Signaler que la tâche a réussi à terminer son travail
					succeeded();

					return error;
				}

			};
			
			

			progressIndic.progressProperty().unbind();

			// S'assurer que la propriété progress du ProgressIndicator est initialisée à 0
			progressIndic.setProgress(0);

			// Liaison de la propriété progress du ProgressIndicator avec la propriété
			// progress de la tâche
			progressIndic.progressProperty().bind(task.progressProperty());

			Thread thread = new Thread(task);
			thread.start();
			 
			
			

			String path = chemin + "\\model_" + MenuControler.CONFIG.hiddenLayerSize + "_"
					+ MenuControler.CONFIG.learningRate + "_" + MenuControler.CONFIG.numberOfhiddenLayers + ".srl";
			HumanVsIaController.NET.save(path);

		} catch (Exception e) {
			System.out.println("Test.learn()");
			e.printStackTrace();
			System.exit(-1);
		}

	}
	/*
	 * @FXML public void clickButtonProgress(ActionEvent event) throws
	 * InterruptedException {
	 * 
	 * if (progressIndic != null) {
	 * 
	 * double epochs = 10000; HashMap<Integer, Coup> mapTrain = Test
	 * .loadCoupsFromFile(
	 * "C:\\Users\\33780\\Desktop\\JAVA\\PremProjet\\resources\\train\\train.txt");
	 * MultiLayerPerceptron net = learn(9, mapTrain,
	 * MenuControler.CONFIG.hiddenLayerSize, MenuControler.CONFIG.learningRate,
	 * MenuControler.CONFIG.numberOfhiddenLayers, true, epochs); String path =
	 * chemin + "\\model_" + MenuControler.CONFIG.hiddenLayerSize + "_" +
	 * MenuControler.CONFIG.learningRate + "_" +
	 * MenuControler.CONFIG.numberOfhiddenLayers + ".srl"; net.save(path);
	 * 
	 * 
	 * 
	 * } else { System.out.
	 * println("progressIndic est nul. Faut s'assurer qu'il est initialisé correctement."
	 * ); }
	 * 
	 * 
	 * Parent root; try { root =
	 * FXMLLoader.load(getClass().getResource("humanVsIa.fxml")); Scene scene = new
	 * Scene(root); Stage parentStage = (Stage) ((Node)
	 * event.getSource()).getScene().getWindow(); parentStage.setScene(scene);
	 * parentStage.show(); } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
}
