package pl.edu.pwr.student.damian_fryc.lab5;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import pl.edu.pwr.student.damian_fryc.lab5.logic.*;
import pl.edu.pwr.student.damian_fryc.lab5.view.CarQueueUI;
import pl.edu.pwr.student.damian_fryc.lab5.view.CarUI;
import pl.edu.pwr.student.damian_fryc.lab5.view.ControllerUI;
import pl.edu.pwr.student.damian_fryc.lab5.view.WashBayUI;

import java.util.ArrayList;

public class SimulationController {
    private final Pane root;
    private ArrayList<CarQueue> carQueues = new ArrayList<>();
    private ArrayList<WashBay> washBays = new ArrayList<>();
    private ArrayList<Car> cars = new ArrayList<>();
    public SimulationController(Pane root) {
        this.root = root;
        int queueCapacity = 10;

        createQueues(7);
        createWashBays(15);
        createCars(40);

        Platform.runLater(() -> {
            for (Car car : cars)
                car.start();
        });
        createController(queueCapacity);

    }

    private void createController(int queueCapacity) {
        PathTransition pathTransition = new PathTransition();
        Path path = new Path();
        ControllerUI controllerUI = new ControllerUI(path, pathTransition, queueCapacity, 1000);
        root.getChildren().addAll(controllerUI.getShape(), path);

        Controller controller  = new Controller(washBays, carQueues, 1000, controllerUI);
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
        ArrayList<WaterWasher> waterWashers = new ArrayList<>();
        ArrayList<SoapWasher> soapWashers = new ArrayList<>();

        for (int i = 0; i < amount - 1; i++) {
            waterWashers.add(new WaterWasher());
            soapWashers.add(new SoapWasher());
        }

        WaterWasher[] waterWashersC;
        SoapWasher[] soapWashersC;
        for (int i = 0; i < amount; i++) {
            if (i == 0) {
                waterWashersC = new WaterWasher[]{waterWashers.get(i), null};
                soapWashersC = new SoapWasher[]{soapWashers.get(i), null};
            }
            else if(i == amount - 1){
                waterWashersC = new WaterWasher[]{waterWashers.get(i-1), null};
                soapWashersC = new SoapWasher[]{soapWashers.get(i-1), null};
            }
            else {
                waterWashersC = new WaterWasher[]{waterWashers.get(i-1), waterWashers.get(i)};
                soapWashersC = new SoapWasher[]{soapWashers.get(i-1), soapWashers.get(i)};
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
