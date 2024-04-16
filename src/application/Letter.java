package application;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Letter extends Group {
    private Circle circle;
    private Line line1;
    private Line line2;

    public Letter(char letter, double size) {
        // Créer un cercle
        circle = new Circle(size / 2);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(3);
        
        
        // Créer des lignes
        line1 = new Line(0, 0, size, size);
        line2 = new Line(0, size, size, 0);
        line1.setStroke(Color.BLACK);
        line2.setStroke(Color.BLACK);
        line1.setStrokeWidth(3);
        line2.setStrokeWidth(3);

        // Ajouter les éléments à la lettre selon la lettre spécifiée
        if (letter == 'O') {
            getChildren().add(circle);
        } else if (letter == 'X') {
            getChildren().addAll(line1, line2);
        }
    }

    // Méthode pour allumer ou éteindre la lettre
    public void setLit(boolean lit) {
        Color color1 = lit ? Color.BLUE  : Color.BLACK;
        Color color2 = lit ? Color.RED  : Color.BLACK;
        if (circle != null) {
        	circle.setStroke(color1);
        }
        if (line1 != null && line2 != null) {
            line1.setStroke(color2);
            line2.setStroke(color2);
        }
    }
}
