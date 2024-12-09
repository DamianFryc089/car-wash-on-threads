package pl.edu.pwr.student.damian_fryc.lab5.logic;

import pl.edu.pwr.student.damian_fryc.lab5.view.WasherUI;

import java.util.concurrent.Semaphore;
import java.util.random.RandomGenerator;

public class Washer {
    public final Semaphore semaphore = new Semaphore(1, true);
    public static double WAITING_TIME_SCALE = 1;
    public final WasherUI washerUI;

    enum WasherType{
        WATER,
        SOAP
    }
    public Washer(WasherUI washerUI) {
        this.washerUI = washerUI;
    }

    public void use(int direction) throws InterruptedException {

        if(direction == 0)  washerUI.showLeft();
        else                washerUI.showRight();

        Thread.sleep((long) (WAITING_TIME_SCALE * RandomGenerator.getDefault().nextInt(1000,5000)));
        washerUI.hide();
        semaphore.release();
    }
}
