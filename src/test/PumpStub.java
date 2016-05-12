package test;

import org.junit.experimental.theories.Theories;

import boundaryclasses.IPump;

public class PumpStub implements IPump {
	
	private boolean pumpActivated;
	private static int pumpNumber = 1; 

	@Override
	public void sendActivate() {
		System.out.println("activate Pump " +pumpNumber);
		pumpNumber++;
		pumpActivated = true;
	}

	@Override
	public void sendDeactivate() {
		System.out.println("activate Pump " +pumpNumber);
		pumpNumber--;
		pumpActivated = false;
	}

	@Override
	public boolean receivedActivated() {
		return pumpActivated;
	}

}
