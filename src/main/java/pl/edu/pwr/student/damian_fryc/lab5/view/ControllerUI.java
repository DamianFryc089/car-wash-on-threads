package pl.edu.pwr.student.damian_fryc.lab5.view;

import javafx.animation.PathTransition;
import javafx.application.Platform;
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
import pl.edu.pwr.student.damian_fryc.lab5.logic.Controller;

import java.util.random.RandomGenerator;

public class ControllerUI {
    private final StackPane controllerShape = new StackPane();
    private final Path path;
    private final PathTransition pathTransition;
    public double x = 0;
    public double y = 0;
    private final Object animationStop = new Object();

    public ControllerUI(Path path, PathTransition pathTransition) {

        Text text = new Text("P");

        int color = RandomGenerator.getDefault().nextInt(0xFFFFFF + 1);
//        Rectangle rectangle = new Rectangle(10, 10 , Paint.valueOf(String.format("#%06X", color)));
        Circle circle = new Circle(10 , Paint.valueOf("999999"));
        controllerShape.getChildren().addAll(circle, text);

        x = 65 + CarQueueUI.LINE_LENGTH_PER_CAR * CarQueue.CAPACITY;
        y = -10;

        controllerShape.setTranslateX(x);
        controllerShape.setTranslateY(y);

        pathTransition.setNode(controllerShape);
        pathTransition.setDuration( Duration.millis(Controller.WAITING_TIME_SCALE * 500));
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.setPath(path);

        this.path = path;
        this.pathTransition = pathTransition;

        path.setStrokeWidth(0);

        // after the end of the animation send signal
        pathTransition.setOnFinished(event -> {
            synchronized (animationStop) {
                animationStop.notify();
            }
        });
    }

    public Node getShape() {
        return controllerShape;
    }

    public void goToNextQueue(CarQueue carQueue) throws InterruptedException {
//        if(isPlaying){
//            synchronized (monitor) {
//                monitor.wait();
//            }
//        }
        // command Javafx to run animation
        Platform.runLater(() -> {
            path.getElements().clear();
            path.getElements().add(new MoveTo(x, y));

            // to Y of queue
            y = carQueue.carQueueUI.y - CarQueueUI.QUEUE_HEIGHT;
            path.getElements().add(new LineTo(x, y));

            pathTransition.play();
        });

        // wait till the end of the animation
        synchronized (animationStop) {
            animationStop.wait();
        }
//        path.getElements().clear();
//        path.getElements().add(new MoveTo(x, y)); // Start
//
//        // to Y of queue
//        y = carQueue.carQueueUI.y - CarQueueUI.QUEUE_HEIGHT;
//        path.getElements().add(new LineTo(x, y));
//
//        isPlaying = true;
//        pathTransition.play();

//        Thread.sleep((long) (pathTransition.getDuration().toMillis()  * 1.2));
    }
}
