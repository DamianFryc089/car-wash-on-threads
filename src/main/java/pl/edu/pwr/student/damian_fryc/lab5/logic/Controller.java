package pl.edu.pwr.student.damian_fryc.lab5.logic;

import pl.edu.pwr.student.damian_fryc.lab5.view.ControllerUI;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Thread{
	private ArrayList<WashBay> washBays;
	private ArrayList<CarQueue> carQueues;
	private int lastPicked = 0;
	private int waitTime;
	private final ControllerUI controllerUI;

	public Controller(List<WashBay> washBays, List<CarQueue> carQueues, int waitTime, ControllerUI controllerUI) {
		this.washBays = (ArrayList<WashBay>) washBays;
		this.carQueues = (ArrayList<CarQueue>) carQueues;
		this.waitTime = waitTime;
        this.controllerUI = controllerUI;
    }

	@Override
	public void run(){
		while (true)
		{
			try {
				sleep(waitTime/2);

				WashBay emptyWashBay = null;
				for (WashBay washBay : washBays){
					if(washBay.isEmpty()) {
						emptyWashBay = washBay;
						break;
					}
				}
				if(emptyWashBay == null) continue;

				controllerUI.goToNextQueue(carQueues.get(lastPicked));

				Car car = carQueues.get(lastPicked).getFirst();
				lastPicked = (lastPicked + 1) % carQueues.size();
				if (car == null) continue;

				car.washBay = emptyWashBay;
				synchronized (car.lock) {
					car.lock.notify();
				}
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
    }

}
