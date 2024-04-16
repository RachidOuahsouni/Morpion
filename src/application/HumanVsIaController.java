package application;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import ai.MultiLayerPerceptron;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HumanVsIaController implements Initializable {

	static public MultiLayerPerceptron NET;

	@FXML
	Rectangle rectangle;

	@FXML
	Label labelO;

	@FXML
	Label labelX;

	@FXML
	Label labelScoreX;

	@FXML
	Label labelScoreO;

	@FXML
	Button button1;

	@FXML
	Button button2;

	@FXML
	Button button3;

	@FXML
	Button button4;

	@FXML
	Button button5;

	@FXML
	Button button6;

	@FXML
	Button button7;

	@FXML
	Button button8;

	@FXML
	Button button9;

	@FXML
	GridPane gridPane;

	@FXML
	Button settingsButton;

	@FXML
	Button quiteButton;
	private char currentPlayer = 'O'; // X starts the game
	private char[][] tab = new char[3][3];
	private int counter = 0;
	Letter letterO = new Letter('O', 50);
	Letter letterX = new Letter('X', 50);
	public static double[] inputs = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	public static double outputs[];

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		labelO.setGraphic(letterO);
		labelX.setGraphic(letterX);

		labelScoreX.setText("0");
		labelScoreO.setText("0");
		letterO.setLit(true);
		inputs = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		outputs = NET.forwardPropagation(inputs);
		System.out.println(Arrays.toString(outputs));
		double tab[] = getOutputs(outputs);
		System.out.println(Arrays.toString(tab));
		// resetBoard();
	}

	@FXML
	public void play(ActionEvent event) {
		boolean find = false;
		boolean win = false;
		int row = -1;
		int col = -1;
		Button clickedButton = (Button) event.getSource();

		if (clickedButton.getGraphic() == null && currentPlayer == 'O') {// If the cell is empty

			counter++;
			if (clickedButton == button1) {
				row = 0;
				col = 0;
				inputs[0] = 1;
			} else if (clickedButton == button2) {
				row = 0;
				col = 1;
				inputs[1] = 1;
			} else if (clickedButton == button3) {
				row = 0;
				col = 2;
				inputs[2] = 1;
			} else if (clickedButton == button4) {
				row = 1;
				col = 0;
				inputs[3] = 1;
			} else if (clickedButton == button5) {
				row = 1;
				col = 1;
				inputs[4] = 1;
			} else if (clickedButton == button6) {
				row = 1;
				col = 2;
				inputs[5] = 1;
			} else if (clickedButton == button7) {
				row = 2;
				col = 0;
				inputs[6] = 1;
			} else if (clickedButton == button8) {
				row = 2;
				col = 1;
				inputs[7] = 1;
			} else if (clickedButton == button9) {
				row = 2;
				col = 2;
				inputs[8] = 1;
			}

			if (currentPlayer == 'O') {
				Letter letter = new Letter('O', 50);
				letter.setLit(true);
				clickedButton.setGraphic(letter);
				

				tab[row][col] = currentPlayer;
				if (checkWin(row, col)) {
					win = true;
					System.out.println("win");

					int score = Integer.parseInt(labelScoreO.getText());
					score++;
					labelScoreO.setText(String.valueOf(score));
					resetBoard();
				} else if (checkDraw()) {
					System.out.println("draw");
					win = true;
					resetBoard();
				} else {
					
					currentPlayer = 'X';
					TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), rectangle);
					transition.setToX(325);

					transition.play();
					
					rectangle.setFill(Color.RED);
					letterX.setLit(true);
					letterO.setLit(false);
					
				}
				
			}

			if (!win) {
				
				
			
			
			Task<Void> playerTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                	 try {
                         Thread.sleep(1000); // un retard d'une seconde pour que le joueur voir que IA joue son tour
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                    Platform.runLater(() -> {
                    	int row = -1;
                    	int col = -1;
                    	boolean find = false;
                    	Letter letter2 = new Letter('X', 50);
        				letter2.setLit(true);

        				outputs = NET.forwardPropagation(inputs);

        				System.out.println(Arrays.toString(outputs));
        				// recupreation du tableau des probaabilité trié en ordre croissant

        				double tabIa[] = getOutputs(outputs);
        				System.out.println(Arrays.toString(tabIa));

        				int i = 0;

        				while (!find && i < tabIa.length) {
        					if (tabIa[i] == 0 && button1.getGraphic() == null) {
        						button1.setGraphic(letter2);
        						inputs[0] = -1;
        						row = 0;
        						col = 0;
        						find = true;
        					} else if (tabIa[i] == 1 && button2.getGraphic() == null) {
        						button2.setGraphic(letter2);
        						inputs[1] = -1;
        						row = 0;
        						col = 1;
        						find = true;
        					} else if (tabIa[i] == 2 && button3.getGraphic() == null) {
        						button3.setGraphic(letter2);
        						inputs[2] = -1;
        						row = 0;
        						col = 2;
        						find = true;
        					} else if (tabIa[i] == 3 && button4.getGraphic() == null) {
        						button4.setGraphic(letter2);
        						inputs[3] = -1;
        						row = 1;
        						col = 0;
        						find = true;
        					} else if (tabIa[i] == 4 && button5.getGraphic() == null) {
        						button5.setGraphic(letter2);
        						inputs[4] = -1;
        						row = 1;
        						col = 1;
        						find = true;
        					} else if (tabIa[i] == 5 && button6.getGraphic() == null) {
        						button6.setGraphic(letter2);
        						inputs[5] = -1;
        						row = 1;
        						col = 2;
        						find = true;
        					} else if (tabIa[i] == 6 && button7.getGraphic() == null) {
        						button7.setGraphic(letter2);
        						inputs[6] = -1;
        						row = 2;
        						col = 0;
        						find = true;
        					} else if (tabIa[i] == 7 && button8.getGraphic() == null) {
        						button8.setGraphic(letter2);
        						inputs[7] = -1;
        						row = 2;
        						col = 1;
        						find = true;
        					} else if (tabIa[i] == 8 && button9.getGraphic() == null) {
        						button9.setGraphic(letter2);
        						inputs[8] = -1;
        						row = 2;
        						col = 2;
        						find = true;
        					}
        					i++;

        				}

        				counter++;

        				

        				tab[row][col] = currentPlayer;

        				if (checkWin(row, col)) {
        					System.out.println("win");

        					int score = Integer.parseInt(labelScoreX.getText());
        					score++;
        					labelScoreX.setText(String.valueOf(score));
        					resetBoard();
        				} else if (checkDraw()) {
        					System.out.println("draw");

        					resetBoard();
        				} else {
        			
        					currentPlayer = 'O';
        					TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.1), rectangle);
        					transition2.setToX(2);

        					transition2.play();

        					rectangle.setFill(Color.BLUE);
        					letterO.setLit(true);
        					letterX.setLit(false);
        					
        				}                    });
                    return null;
                }
            };   
            Thread thread = new Thread(playerTask);
            thread.start();
            
			}
		}
		
	}

	private boolean checkWin(int row, int col) {

		if (row == col && (tab[0][0] == tab[1][1] && tab[0][0] == tab[2][2])) {
			return true;
		}
		if (((row == 2 && col == 0) || (row == 1 && col == 1) || (row == 0 && col == 2))
				&& (tab[2][0] == tab[1][1] && tab[2][0] == tab[0][2])) {
			return true;
		}
		if ((tab[row][0] == tab[row][1] && tab[row][0] == tab[row][2])
				|| (tab[0][col] == tab[1][col] && tab[0][col] == tab[2][col])) {
			return true;
		}

		return false;
	}

	private boolean checkDraw() {

		if (counter == 9)
			return true;
		return false;
	}

	private void resetBoard() {

		currentPlayer = 'O';
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tab[i][j] = 'm';
			}
		}
		char[][] tab = new char[3][3];
		counter = 0;
		// Clear all button texts
		button1.setGraphic(null);
		button2.setGraphic(null);
		button3.setGraphic(null);
		button4.setGraphic(null);
		button5.setGraphic(null);
		button6.setGraphic(null);
		button7.setGraphic(null);
		button8.setGraphic(null);
		button9.setGraphic(null);
		TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), rectangle);
		transition.setToX(2);

		transition.play();
		rectangle.setFill(Color.BLUE);
		letterO.setLit(true);
		letterX.setLit(false);

		inputs = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		outputs = NET.forwardPropagation(inputs);

	}

	// je recupere lindice du plus grand nombre de mon tableau
	int getindiceMaxvalueofBord(double[] outputs) {
		double max = outputs[0];
		int indice = 0;
		for (int i = 0; i < outputs.length; i++) {
			if (max < outputs[i]) {
				max = outputs[i];
				indice = i;
			}

		}
		return indice;
	}

	// ici je recupere un tableau trie par odre croissant des intice de outpout;
	public double[] getOutputs(double[] outputs) {
		double[] Tab = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

		for (int i = 0; i < outputs.length; i++) {
			int j = getindiceMaxvalueofBord(outputs);
			Tab[i] = j;
			outputs[j] = 0;
		}
		return Tab;

	}

	private void printTab() {
		for (int i = 0; i < tab.length; i++) {
			for (int j = 0; j < tab[i].length; j++) {
				System.out.print(tab[i][j] + " ");
			}
			System.out.println(); // Ajoutez une nouvelle ligne à la fin de chaque ligne du tableau
		}
	}
}
