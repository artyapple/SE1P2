package test;

import java.util.HashMap;
import java.util.Map;

import boundaryclasses.IOpticalSignals;

public class OpticalSignalsStub implements IOpticalSignals {

	private static int cnt;

	@Override
	public void switchLampAOn() {
		System.out.println(">Lampe A on");
		cnt++;
	}

	@Override
	public void switchLampAOff() {
		System.out.println(">Lampe A off");
		cnt++;
	}

	@Override
	public void switchLampBOn() {
		System.out.println(">Lampe B on");
		cnt++;
	}

	@Override
	public void switchLampBOff() {
		System.out.println(">Lampe B off");
		cnt++;
	}
	
	public static int getLampActivCount()
	{
		return cnt;
	}

}
