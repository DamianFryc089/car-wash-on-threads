package pl.edu.pwr.student.damian_fryc.lab5.view;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import pl.edu.pwr.student.damian_fryc.lab5.logic.CarQueue;

import java.util.ArrayList;
import java.util.Collection;

public class WashBayUI {

    public double x = 0;
    public double y = 0;
    public static final double WIDTH_OF_WASHING_BAY = 35;
    public static final double HEIGHT_OF_WASHING_BAY = 40;

    public WashBayUI(int i) {
        x = 125 + CarQueueUI.LINE_LENGTH_PER_CAR * CarQueue.CAPACITY + i * WIDTH_OF_WASHING_BAY;
        y = 25;
    }

    public Collection<? extends Node> getShape() {
        ArrayList<Line> lines = new ArrayList<>();
        double x1 = x - WIDTH_OF_WASHING_BAY / 2;
        double x2 = x + WIDTH_OF_WASHING_BAY / 2;

        double y1 = y - HEIGHT_OF_WASHING_BAY / 2;
        double y2 = y + HEIGHT_OF_WASHING_BAY / 2;
        lines.add(new Line(x1,  y1,  x1,  y2));
        lines.add(new Line(x2,  y1,  x2,  y2));
        return lines;
    }
}
