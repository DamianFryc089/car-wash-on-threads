package pl.edu.pwr.student.damian_fryc.lab5.logic;

import java.util.concurrent.Semaphore;
import java.util.random.RandomGenerator;

public abstract class Washer {
    boolean available = true;
    public final Semaphore semaphore = new Semaphore(1, true);
    public void use() throws InterruptedException {
        Thread.sleep( RandomGenerator.getDefault().nextInt(1000,5000));
        semaphore.release();
    }
}
