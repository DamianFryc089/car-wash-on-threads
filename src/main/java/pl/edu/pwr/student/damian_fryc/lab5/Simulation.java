package pl.edu.pwr.student.damian_fryc.lab5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Simulation extends Application {
	@Override
	public void start(Stage stage) {
		Pane root = new Pane();
		SimulationController controller = new SimulationController(root);

		stage.setScene(new Scene(root, 600, 400));
		stage.setTitle("Car Wash Simulation");
		stage.show();
	}




	public static void main(String[] args) {
		launch(args);
	}
}
