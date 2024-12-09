package pl.edu.pwr.student.damian_fryc.lab5.view;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import pl.edu.pwr.student.damian_fryc.lab5.logic.CarQueue;

public class WasherUI {
    public double x = 0;
    public double y = 0;
    private final StackPane washerShape = new StackPane();
    public WasherUI(int posFromTop, int washBayId){
        x = 125 + CarQueueUI.LINE_LENGTH_PER_CAR * CarQueue.CAPACITY + WashBayUI.WIDTH_OF_WASHING_BAY * (washBayId + 1d / 2d);
        y = 25 + posFromTop * WashBayUI.HEIGHT_OF_WASHING_BAY / 2 - WashBayUI.HEIGHT_OF_WASHING_BAY/2;

        Line line = new Line(x, y, x, y + WashBayUI.HEIGHT_OF_WASHING_BAY / 3);

        if(posFromTop == 0) line.setStroke(Paint.valueOf("FF0000"));
        else                line.setStroke(Paint.valueOf("0000FF"));

        washerShape.getChildren().addAll(line);
        washerShape.setTranslateX(x);
        washerShape.setTranslateY(y);
        hide();
    }
    public StackPane getShape() {
        return washerShape;
    }
    public void hide(){
        Platform.runLater(() -> {
            washerShape.setVisible(false);
            washerShape.setTranslateX(x);
        });
    }
    public void showLeft(){
        Platform.runLater(() -> {
            washerShape.setTranslateX(x - 3);
            washerShape.setVisible(true);
        });
    }
    public void showRight(){
        Platform.runLater(() -> {
            washerShape.setTranslateX(x + 3);
            washerShape.setVisible(true);
        });
    }
}
