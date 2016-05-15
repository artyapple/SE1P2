package test;

import boundaryclasses.IHumidifier;

public class HumidifierStub implements IHumidifier {

	private HumiditySensorStub sensor;
	private boolean humidifierActivated;
	
	public HumidifierStub(HumiditySensorStub sensor) {
		this.sensor = sensor;
	}
	
	@Override
	public void sendSprayOn() {
		System.out.println(">Spray ON");
		humidifierActivated = true;
		humidify();
	}

	@Override
	public void sendSprayOff() {
		System.out.println(">Spray OFF");
		humidifierActivated = false;
	}
	
	private void humidify()
	{
		try {
			Thread.sleep(500);
			sensor.increaseHum(20);
			System.out.println("Humdidify in progress...");
			sensor.getHumidity();
		} catch (InterruptedException e) {
			System.out.println(">>Humidify Error");
		}
	}

}
