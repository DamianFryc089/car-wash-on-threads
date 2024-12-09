package pl.edu.pwr.student.damian_fryc.lab5.logic;

import pl.edu.pwr.student.damian_fryc.lab5.view.CarQueueUI;

import java.util.Arrays;

public class CarQueue {
	public static int CAPACITY = 10;
	private final Car[] queuedCars;
	private final int id;
	private int size;
	private int reserved;
	public final CarQueueUI carQueueUI;

	public CarQueue(int id, CarQueueUI carQueueUI) {
		this.queuedCars = new Car[CAPACITY];
		this.id = id;
        this.carQueueUI = carQueueUI;
        this.size = 0;
		this.reserved = 0;
	}


	public synchronized int reserve(){
		if (reserved == CAPACITY)
			return -1;

		reserved++;
		return reserved-1;
	}
	public synchronized void enqueue(Car car) {
		queuedCars[size] = car;
		size++;
		System.out.println(id + ": " + getQueueString() + " +" + car.letter);
	}


	public synchronized Car getFirst() throws InterruptedException {
		if (size == 0) return null;

		Car removedCar = queuedCars[0];

		for (int i = 1; i < size; i++)
			queuedCars[i - 1] = queuedCars[i];


		queuedCars[size - 1] = null;
		size--;
		reserved--;
		carQueueUI.moveCarsInQueue(queuedCars);
		System.out.println(id + ": " + getQueueString() + " -" + removedCar.letter);
		return removedCar;
	}

	public synchronized int getQueueCarCount() {
		return reserved;
	}
	public int getQueueCapacity() {
		return CAPACITY;
	}

	@Override
	public String toString() {
		return "CarQueue{" +
				"CAPACITY=" + CAPACITY +
				", queuedCars=" + Arrays.toString(queuedCars) +
				", size=" + size +
				'}';
	}

	private String getQueueString() {
		StringBuilder text = new StringBuilder();
		for (int i = CAPACITY - 1; i >= 0; i--) {
			if (queuedCars[i] == null) text.append(" - ");
			else text.append(" ").append(queuedCars[i].letter).append(" ");
		}
		return text.toString();
	}
}
