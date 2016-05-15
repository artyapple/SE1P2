package test;

/**
 * Test Framework for testing the FSM from Practice 3
 * @author Thomas Lehmann
 * @version 2015-11-18
 */
import static org.junit.Assert.*;
import fsm.IFSM;
import implementation.FSMImplementation;
import implementation.FSMState;

import org.junit.Before;
import org.junit.Test;

import boundaryclasses.IGate;
import boundaryclasses.IHumidifier;
import boundaryclasses.IHumiditySensor;
import boundaryclasses.IManualControl;
import boundaryclasses.IOpticalSignals;
import boundaryclasses.IPump;

public class FSMImplementationTest {
	private PumpStub pumpA;
	private PumpStub pumpB;
	private GateStub gate;
	private OpticalSignalsStub signals;
	private HumiditySensorStub sensor;
	private HumidifierStub humidifier;
	private ManualControlStub operatorPanel;
	private IFSM uut;
	private TimerStub timer; 

	@Before
	public void testSetup() {
		gate = new GateStub();
		signals = new OpticalSignalsStub();
		sensor = new HumiditySensorStub(100);
		pumpA = new PumpStub(sensor);
		pumpB = new PumpStub(sensor);
		humidifier = new HumidifierStub(sensor);
		operatorPanel = new ManualControlStub();
		timer = new TimerStub();
		uut = new FSMImplementation(pumpA, pumpB, gate, signals, humidifier, sensor, operatorPanel, timer);
	}

	@Test
	public void testPath() {
		uut.evaluate();
		
		assertEquals(2, GateStub.getGateActivity());
		assertEquals(4, PumpStub.getPumpActivity());
		assertEquals(4, OpticalSignalsStub.getLampActivCount());
	}

}
