package pl.edu.pwr.student.damian_fryc.lab5.logic;

import pl.edu.pwr.student.damian_fryc.lab5.view.CarUI;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

public class Car extends Thread {
	public final Object lock = new Object();
	public final char letter;
	public WashBay washBay = null;
	public final CarUI carUI;
	private final ArrayList<CarQueue> carQueues;
	private CarQueue carQueue = null;
	public static double WAITING_TIME_SCALE = 1;
	public Car(int id, ArrayList<CarQueue> carQueues, CarUI carUI){
		letter = (char) (id + 'a');
		this.carQueues = carQueues;
        this.carUI = carUI;
    }

	@Override
	public void run(){
		while (true){
			try {
				sleep((long) (WAITING_TIME_SCALE * RandomGenerator.getDefault().nextInt(500, 10000)));
				int slotInQueue;
				do {
					sleep((long) (WAITING_TIME_SCALE * 1000));
					slotInQueue = reserveSlotInQueue();
				} while(slotInQueue == -1);

				carUI.moveToCarQueue(carQueue, slotInQueue);

				System.out.println(letter + " arrived to queue");
				carQueue.enqueue(this);
				synchronized (lock){
					lock.wait();
				}

				washBay.setCar(this);

				System.out.println(letter + " arrived to " + washBay.id);

				carUI.moveToWashBay(washBay);

				washPhase(Washer.WasherType.WATER);
				washPhase(Washer.WasherType.SOAP);
				washPhase(Washer.WasherType.WATER);

				washBay.removeCar();
				washBay = null;

				carUI.moveToEdgeOfScreen();
				System.out.println(letter + " DONE!!");

			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
    }
	private int reserveSlotInQueue(){
		CarQueue minQueue = carQueues.getFirst();
		for (int i = 1; i < carQueues.size(); i++) {
			if (carQueues.get(i).getQueueCarCount() < minQueue.getQueueCarCount())
				minQueue = carQueues.get(i);
		}

		int reserved = minQueue.reserve();
		if(reserved != -1)
			carQueue = minQueue;

		return reserved;
	}
	private void washPhase(Washer.WasherType washerType) throws InterruptedException {
		while (true) {
			Washer washer = washBay.tryUseLeft(washerType, (long) (WAITING_TIME_SCALE * 1000));
			if(washer != null) {
				washer.use(1);
				return;
			}

			washer = washBay.tryUseRight(washerType, (long) (WAITING_TIME_SCALE * 1000));
			if(washer != null) {
				washer.use(0);
				return;
			}
		}
	}
}
