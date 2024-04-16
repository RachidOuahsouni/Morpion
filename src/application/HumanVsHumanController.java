package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
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

public class HumanVsHumanController implements Initializable {

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		labelO.setGraphic(letterO);
		labelX.setGraphic(letterX);

		labelScoreX.setText("0");
		labelScoreO.setText("0");
		letterO.setLit(true);
		// resetBoard();
	}

	@FXML
	public void play(ActionEvent event) {
		int row = -1;
		int col = -1;
		Button clickedButton = (Button) event.getSource();

		if (clickedButton.getGraphic() == null) {// If the cell is empty

			counter++;
			if (clickedButton == button1) {
				row = 0;
				col = 0;
			} else if (clickedButton == button2) {
				row = 0;
				col = 1;
			} else if (clickedButton == button3) {
				row = 0;
				col = 2;
			} else if (clickedButton == button4) {
				row = 1;
				col = 0;
			} else if (clickedButton == button7) {
				row = 2;
				col = 0;
			} else {
				row = GridPane.getRowIndex(clickedButton);
				col = GridPane.getColumnIndex(clickedButton);
			}

			if (currentPlayer == 'O') {
				TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), rectangle);
				transition.setToX(325);

				transition.play();
				rectangle.setFill(Color.RED);
				letterX.setLit(true);
				letterO.setLit(false);
				Letter letter = new Letter('O', 50);
				letter.setLit(true);
				clickedButton.setGraphic(letter);
			} else {
				TranslateTransition transition = new TranslateTransition(Duration.seconds(0.1), rectangle);
				transition.setToX(2);

				transition.play();
				rectangle.setFill(Color.BLUE);
				letterO.setLit(true);
				letterX.setLit(false);
				Letter letter = new Letter('X', 50);
				letter.setLit(true);
				clickedButton.setGraphic(letter);

			}
			tab[row][col] = currentPlayer;

			if (checkWin(row, col)) {
				System.out.println("win");

				if (currentPlayer == 'X') {
					int score = Integer.parseInt(labelScoreX.getText());
					score++;
					labelScoreX.setText(String.valueOf(score));
				} else if (currentPlayer == 'O') {
					int score = Integer.parseInt(labelScoreO.getText());
					score++;
					labelScoreO.setText(String.valueOf(score));
				}

				resetBoard();
			} else if (checkDraw()) {
				System.out.println("draw");

				resetBoard();
			} else {
				currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Switch players
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
	}

}
