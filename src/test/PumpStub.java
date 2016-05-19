package test;

import boundaryclasses.IPump;

public class PumpStub implements IPump {

	private boolean pumpActivated;
	private static int pumpNumber = 0;
	private HumiditySensorStub sensor;
	// var for testing
	private static int cnt;
	//
	private static int errorVar = 0;

	public PumpStub(HumiditySensorStub sensor) {
		this.sensor = sensor;
	}

	@Override
	public void sendActivate() {
		pumpNumber++;
		System.out.println("activate Pump " + pumpNumber);

//		if (errorVar == 1) {
//			pumpActivated = false;
//		}
		pumpActivated = false;
		if (pumpActivated) {
			dry(sensor);
		}

		errorVar++;
		cnt++;
	}

	@Override
	public void sendDeactivate() {
		System.out.println("deactivate Pump " + pumpNumber);
		pumpNumber--;
		pumpActivated = false;

		cnt++;
	}

	@Override
	public boolean receivedActivated() {
		return pumpActivated;
	}

	public static void startPumps(PumpStub pump1, PumpStub pump2,
			TimerStub timer) {
		timer.startTime(5);
		pump1.sendActivate();
		pump2.sendActivate();
	}

	//
	public static boolean pumpsActivated(PumpStub pump1, PumpStub pump2,
			TimerStub timer) {
		while (!timer.isTimerExpired()) {
			if (pump1.receivedActivated() && pump2.receivedActivated()) {
				return true;
			}
		}
		return false;
	}

	//
	public static void dry(HumiditySensorStub sensor) {
		try {
			Thread.sleep(500);
			sensor.reduceHum(20);
		} catch (InterruptedException e) {
			System.out.println(">>Humidify Error");
		}
	}

	// for testing
	public static int getPumpActivity() {
		return cnt;
	}
}