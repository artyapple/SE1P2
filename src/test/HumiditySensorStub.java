package test;

import boundaryclasses.IHumiditySensor;

public class HumiditySensorStub implements IHumiditySensor {

	private double humidity;

	public HumiditySensorStub(double humidity) {
		this.humidity = humidity;
	}

	@Override
	public double getHumidity() {
		System.out.println("Humidity is: " + humidity + " %");
		return humidity;
	}
	
	public void reduceHum(double value){
	    humidity -= value;
	}
	
	public void increaseHum(double value){
		humidity += value;
	} 

}
