package pl.edu.pwr.student.damian_fryc.lab5.view;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pl.edu.pwr.student.damian_fryc.lab5.logic.CarQueue;

import java.util.random.RandomGenerator;

public class ControllerUI {
    private final StackPane controllerShape = new StackPane();
    private final Path path;
    private final PathTransition pathTransition;
    public double x = 0;
    public double y = 0;

    public ControllerUI(Path path, PathTransition pathTransition, int queueCapacity, int waitTime) {
        this.path = path;
        this.pathTransition = pathTransition;

        Text text = new Text("P");

        int color = RandomGenerator.getDefault().nextInt(0xFFFFFF + 1);
//        Rectangle rectangle = new Rectangle(10, 10 , Paint.valueOf(String.format("#%06X", color)));
        Circle circle = new Circle(10 , Paint.valueOf("999999"));
        controllerShape.getChildren().addAll(circle, text);

        x = 65 + CarQueueUI.LINE_LENGTH_PER_CAR * queueCapacity;
//        y = 60 - 60;

        controllerShape.setTranslateX(x);
        controllerShape.setTranslateY(y);

        pathTransition.setNode(controllerShape);
        pathTransition.setDuration(Duration.millis((double) waitTime /2));
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.setPath(path);

        path.setStrokeWidth(0);
    }

    public Node getShape() {
        return controllerShape;
    }

    public void goToNextQueue(CarQueue carQueue) throws InterruptedException {
        path.getElements().clear();
        path.getElements().add(new MoveTo(x, y)); // Start

        // to Y of queue
        y = carQueue.carQueueUI.y - CarQueueUI.QUEUE_HEIGHT;
        path.getElements().add(new LineTo(x, y));

        pathTransition.play();
        Thread.sleep((long) pathTransition.getDuration().toMillis());
    }
}
