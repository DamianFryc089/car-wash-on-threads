package pl.edu.pwr.student.damian_fryc.lab5.logic;

import pl.edu.pwr.student.damian_fryc.lab5.view.CarUI;
import pl.edu.pwr.student.damian_fryc.lab5.view.WashBayUI;

import java.util.Arrays;

public class WashBay{
	public final WaterWasher[] waterWashers;
	public final SoapWasher[] soapWashers;
	public final int id;
	private Car car;
	public final WashBayUI washBayUI;

	public WashBay(int id, WaterWasher[] waterWashers, SoapWasher[] soapWashers, WashBayUI washBayUI) {
		this.waterWashers = waterWashers;
		this.soapWashers = soapWashers;
        this.id = id;
        this.washBayUI = washBayUI;
    }

	public boolean isEmpty() {
        return car == null;
    }

	public void setCar(Car car) {
		this.car = car;
	}

	public void removeCar(){
		this.car = null;
	}

	@Override
	public String toString() {
		return "WashBay{" +
				"waterWashers=" + Arrays.toString(waterWashers) +
				", soapWashers=" + Arrays.toString(soapWashers) +
				'}';
	}
}
