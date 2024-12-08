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

	public Car(int id, ArrayList<CarQueue> carQueues, CarUI carUI){
		letter = (char) (id + 'a');
		this.carQueues = carQueues;
        this.carUI = carUI;
    }

	@Override
	public void run(){
		while (true){
			try {
				sleep(RandomGenerator.getDefault().nextInt(500, 10000));
				int slotInQueue;
				do {
					sleep(250);
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
				waterPhase();
				soapPhase();
				waterPhase();

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
	private void waterPhase() throws InterruptedException {
		while (true) {
			if (washBay.waterWashers[0].semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
				washBay.waterWashers[0].use();
				System.out.println(letter + " washed in 1");
				return;
			} else {
				System.out.println(letter + " not washed in 1");
			}

			if (washBay.waterWashers[1] != null && washBay.waterWashers[1].semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
				washBay.waterWashers[1].use();
				System.out.println(letter + " washed in 2");
				return;
			} else {
				System.out.println(letter + " not washed in 2");
			}
		}
	}
	private void soapPhase() throws InterruptedException {
		while (true) {
			if (washBay.soapWashers[0].semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
				washBay.soapWashers[0].use();
				System.out.println(letter + " soaped in 1");
				return;
			} else {
				System.out.println(letter + " not soaped in 1");
			}

			if (washBay.soapWashers[1] != null && washBay.soapWashers[1].semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
				washBay.soapWashers[1].use();
				System.out.println(letter + " soaped in 2");
				return;
			} else {
				System.out.println(letter + " not soaped in 2");
			}
		}
	}



}
