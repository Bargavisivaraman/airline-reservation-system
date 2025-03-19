package hellofx;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;

public class Controller {
    @FXML
    private Arc pacMAN;
    private double x;
    private double y;


    public void up(ActionEvent e) {
        System.out.println("UP");
        pacMAN.setCenterY(y -= 10);
    }

    public void down(ActionEvent e) {
        System.out.println("DOWN"); 
        pacMAN.setCenterY(y +=  10);
    }

    public void left(ActionEvent e) {
        System.out.println("LEFT");
        pacMAN.setCenterX(x -= 10);
    }

    public void right(ActionEvent e) {
        System.out.println("RIGHT");
        pacMAN.setCenterX(x += 10); 
    }
}
