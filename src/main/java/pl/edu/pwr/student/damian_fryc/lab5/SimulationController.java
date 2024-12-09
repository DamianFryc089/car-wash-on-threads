package pl.edu.pwr.student.damian_fryc.lab5;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import pl.edu.pwr.student.damian_fryc.lab5.logic.*;
import pl.edu.pwr.student.damian_fryc.lab5.view.*;

import java.util.ArrayList;

public class SimulationController {
    private final Pane root;
    private ArrayList<CarQueue> carQueues = new ArrayList<>();
    private ArrayList<WashBay> washBays = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();
    public SimulationController(Pane root) {
        Car.WAITING_TIME_SCALE = 1;
        Controller.WAITING_TIME_SCALE = 1;
        Washer.WAITING_TIME_SCALE = 1;

        CarQueue.CAPACITY = 10;


        this.root = root;

        createQueues(2);
        createWashBays(4);
        createCars(10);

        Platform.runLater(() -> {
            for (Car car : cars)
                car.start();
        });
        createController();

    }

    private void createController() {
        PathTransition pathTransition = new PathTransition();
        Path path = new Path();
        ControllerUI controllerUI = new ControllerUI(path, pathTransition);
        root.getChildren().addAll(controllerUI.getShape(), path);

        Controller controller  = new Controller(washBays, carQueues, controllerUI);
        controller.start();
    }

    private void createQueues(int amount){
        carQueues.clear();
        for (int i = 0; i < amount; i++) {
            CarQueueUI carQueueUI = new CarQueueUI(i);
            carQueues.add(new CarQueue(i, carQueueUI));
            root.getChildren().addAll(carQueueUI.getShape());
        }
    }

    private void createWashBays(int amount){
        washBays.clear();
        ArrayList<Washer> waterWashers = new ArrayList<>();
        ArrayList<Washer> soapWashers = new ArrayList<>();

        for (int i = 0; i < amount - 1; i++) {
            WasherUI soapWasherUI = new WasherUI(0, i);
            soapWashers.add(new Washer(soapWasherUI));

            WasherUI waterWasherUI = new WasherUI(1, i);
            waterWashers.add(new Washer(waterWasherUI));

            root.getChildren().addAll(soapWasherUI.getShape(), waterWasherUI.getShape());
        }

        Washer[] waterWashersC;
        Washer[] soapWashersC;
        for (int i = 0; i < amount; i++) {
            if (i == 0) {
                waterWashersC = new Washer[]{null, waterWashers.get(i)};
                soapWashersC = new Washer[]{null, soapWashers.get(i)};
            }
            else if(i == amount - 1){
                waterWashersC = new Washer[]{waterWashers.get(i-1), null};
                soapWashersC = new Washer[]{soapWashers.get(i-1), null};
            }
            else {
                waterWashersC = new Washer[]{waterWashers.get(i-1), waterWashers.get(i)};
                soapWashersC = new Washer[]{soapWashers.get(i-1), soapWashers.get(i)};
            }
            WashBayUI washBayUI = new WashBayUI(i);
            root.getChildren().addAll(washBayUI.getShape());

            washBays.add(new WashBay(i, waterWashersC, soapWashersC, washBayUI));
        }
    }

    private void createCars(int amount){
        for (int i = 0; i < amount; i++) {
            PathTransition pathTransition = new PathTransition();
            Path path = new Path();
            CarUI carUI = new CarUI(path, pathTransition, (char) (i+'a'));
            Car car = new Car(i, carQueues, carUI);

            root.getChildren().addAll(carUI.getShape(), path);
            cars.add(car);
        }
    }
}
