package pl.edu.pwr.student.damian_fryc.lab5.logic;

import pl.edu.pwr.student.damian_fryc.lab5.view.ControllerUI;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Thread{
	private final ArrayList<WashBay> washBays;
	private final ArrayList<CarQueue> carQueues;
	private int lastPicked = 0;
	private final ControllerUI controllerUI;
	public static double WAITING_TIME_SCALE = 1;

	public Controller(List<WashBay> washBays, List<CarQueue> carQueues, ControllerUI controllerUI) {
		this.washBays = (ArrayList<WashBay>) washBays;
		this.carQueues = (ArrayList<CarQueue>) carQueues;
        this.controllerUI = controllerUI;
    }

	@Override
	public void run(){
		while (true)
		{
			try {
				sleep((long) (WAITING_TIME_SCALE * 500));

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
