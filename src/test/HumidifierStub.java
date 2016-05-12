package test;

import boundaryclasses.IHumidifier;

public class HumidifierStub implements IHumidifier {

	private boolean humidifierActivated;
	
	@Override
	public void sendSprayOn() {
		System.out.println(">Spray ON");
		humidifierActivated = true;
		
	}

	@Override
	public void sendSprayOff() {
		System.out.println(">Spray OFF");
		humidifierActivated = false;
	}

}
