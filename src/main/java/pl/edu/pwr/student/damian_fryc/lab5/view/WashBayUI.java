package pl.edu.pwr.student.damian_fryc.lab5.view;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collection;

public class WashBayUI {

    public double x = 0;
    public double y = 0;
    public final double widthOfWashingBay = 35;
    public final double heightOfWashingBay = 40;

    public WashBayUI(int i) {
        x = 350 + i * widthOfWashingBay;
        y = 25;
    }

    public Collection<? extends Node> getShape() {
        ArrayList<Line> lines = new ArrayList<>();
        double x1 = x - widthOfWashingBay / 2;
        double x2 = x + widthOfWashingBay / 2;

        double y1 = y - heightOfWashingBay / 2;
        double y2 = y + heightOfWashingBay / 2;
        lines.add(new Line(x1,  y1,  x1,  y2));
        lines.add(new Line(x2,  y1,  x2,  y2));
        return lines;
    }
}
