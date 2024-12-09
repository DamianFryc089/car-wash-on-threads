package pl.edu.pwr.student.damian_fryc.lab5.view;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import pl.edu.pwr.student.damian_fryc.lab5.logic.Car;
import pl.edu.pwr.student.damian_fryc.lab5.logic.CarQueue;

import java.util.ArrayList;
import java.util.List;

public class CarQueueUI {
    public double x = 0;
    public double y = 0;
    public static final double QUEUE_HEIGHT = 30;
    public static final double LINE_LENGTH_PER_CAR = 25;
    private final double lineLength;

    public CarQueueUI(int i) {
        x = 50;
        y = 100 + i * QUEUE_HEIGHT;
        lineLength = CarQueue.CAPACITY * LINE_LENGTH_PER_CAR;
    }

    public List<? extends Node> getShape() {
        ArrayList<Line> lines = new ArrayList<>();
        double startX = x;
        double endX = x + lineLength;

        double y1 = y - QUEUE_HEIGHT / 2;
        double y2 = y + QUEUE_HEIGHT / 2;
        lines.add(new Line(startX,  y1,  endX,  y1));
        lines.add(new Line(startX,  y2,  endX,  y2));
        return lines;
    }

    public void moveCarsInQueue(Car[] queuedCars) throws InterruptedException {
        for (int i = 0; i < queuedCars.length; i++) {
            if(queuedCars[i] != null)
                queuedCars[i].carUI.moveCarInQueue((queuedCars.length - i), x);
        }
    }
}
