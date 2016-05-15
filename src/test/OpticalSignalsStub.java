package test;

import java.util.HashMap;
import java.util.Map;

import boundaryclasses.IOpticalSignals;

public class OpticalSignalsStub implements IOpticalSignals {

	Map lampeLog = new HashMap<String, Integer>();

	@Override
	public void switchLampAOn() {
		System.out.println(">Lampe A on");
	}

	@Override
	public void switchLampAOff() {
		System.out.println(">Lampe A off");
	}

	@Override
	public void switchLampBOn() {
		System.out.println(">Lampe B on");
	}

	@Override
	public void switchLampBOff() {
		System.out.println(">Lampe B off");
	}

}
