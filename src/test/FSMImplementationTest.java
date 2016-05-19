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
	private HumiditySensorStub sensor1;
	private HumiditySensorStub sensor2;
	private HumidifierStub humidifier;
	private ManualControlStub operatorPanel;
	private IFSM uut;
	private IFSM hut;
	private TimerStub timer;
	private PumpStub pumpC;
	private PumpStub pumpD; 

	@Before
	public void testSetup() {
		gate = new GateStub();
		signals = new OpticalSignalsStub();
		sensor1 = new HumiditySensorStub(10);
		sensor2 = new HumiditySensorStub(100);
		pumpA = new PumpStub(sensor1);
		pumpB = new PumpStub(sensor1);
		pumpC = new PumpStub(sensor2);
		pumpD = new PumpStub(sensor2);
		HumidifierStub humidifierA = new HumidifierStub(sensor1);
		HumidifierStub humidifierB = new HumidifierStub(sensor2);
		operatorPanel = new ManualControlStub();
		timer = new TimerStub();
		uut = new FSMImplementation(pumpA, pumpB, gate, signals, humidifierA, sensor1, operatorPanel, timer);
		hut = new FSMImplementation(pumpC, pumpD, gate, signals, humidifierB, sensor2, operatorPanel, timer);
		
	}

	@Test
	public void testPath() {
		uut.evaluate(); //Hunidity ok
		uut.evaluate(); //Humidity low
		assertEquals(0, GateStub.getGateActivity());
		assertEquals(0, PumpStub.getPumpActivity());
		assertEquals(2, OpticalSignalsStub.getLampActivCount());
		
		hut.evaluate(); //Humidity ok
		hut.evaluate(); //Humidity high
		
		assertEquals(2, GateStub.getGateActivity());
		assertEquals(4, PumpStub.getPumpActivity());
		assertEquals(6, OpticalSignalsStub.getLampActivCount());
		
		hut.evaluate(); //Error state 
		assertEquals(1, ManualControlStub.getErrorCnt());
		assertEquals(FSMState.HumidityOkay, hut.getCurrentState()); //Humidity ok
		
		
		
	}

}
