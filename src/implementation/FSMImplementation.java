package implementation;

import boundaryclasses.IGate;
import boundaryclasses.IHumidifier;
import boundaryclasses.IHumiditySensor;
import boundaryclasses.IManualControl;
import boundaryclasses.IOpticalSignals;
import boundaryclasses.IPump;
import boundaryclasses.ITimer;
import fsm.IFSM;

public class FSMImplementation implements IFSM {
	private FSMState state;
	private IPump pumpA;
	private IPump pumpB;
	private IGate gate;
	private IOpticalSignals signals;
	private IHumiditySensor sensor;
	private IHumidifier humidifier;
	private IManualControl operatorPanel;
	private ITimer timer;
	private final double upperBound;
	private final double lowerBound;

	public FSMImplementation(IPump pumpA, IPump pumpB, IGate gate, IOpticalSignals signals, IHumidifier humidifier,
			IHumiditySensor sensor, IManualControl operatorPanel, ITimer timer) {
		this.state = FSMState.HumidityOkay;
		this.pumpA = pumpA;
		this.pumpB = pumpB;
		this.gate = gate;
		this.signals = signals;
		this.sensor = sensor;
		this.humidifier = humidifier;
		this.operatorPanel = operatorPanel;
		this.timer = timer;
		upperBound = 60;
		lowerBound = 20;
		;
	}

	@Override
	public void evaluate() {
		updateState();
		switch (state) {
		case HumidityOkay:
			externalAction();
			break;
		// Trockung
		case Drying:
			signals.switchLampBOn();
			gate.sendCloseGate();
			while (gate.receivedGateOpen()) {
				System.out.println("Gate close: in progress...");
			}
			signals.switchLampBOff();
			IPump.startPumps(pumpA, pumpB, timer); // activate Pump A and B
			if (IPump.pumpsActivated(pumpA, pumpB, timer)) {
				while (sensor.getHumidity() > upperBound) {
					System.out.println("Drying in progress...");
				} // wait
				terminateDrying();
				state = FSMState.HumidityOkay;
			} else {
				terminateDrying();
				state = FSMState.Error;
			}
			break;
		// Befeuchtung
		case Humidification:
			signals.switchLampAOn();
			humidifier.sendSprayOn();
			humidifier.sendSprayOff();
			signals.switchLampAOff();
			break;
		// Fehlerzustand
		case Error:
			while(!operatorPanel.receivedAcknowledgement())
			{
				System.out.println("Waiting for manual control...");
			}
			state = FSMState.Drying;
			break;
		default:
			throw new NullPointerException();
		}

	}

	private void updateState() {
		if ((state != FSMState.Error)) {
			double currentHum = sensor.getHumidity();
			if (currentHum > upperBound) {
				state = FSMState.Drying;
			} else if (currentHum < lowerBound) {
				state = FSMState.Humidification;
			} else if (currentHum >= lowerBound && currentHum <= upperBound) {
				state = FSMState.HumidityOkay;
			}
		}
	}

	/**
	 * simulates the random change of humidity.
	 */
	private void externalAction() {
//		int rand = (Math.random() < 0.5) ? 0 : 1;
//
//		if (rand == 0) {
//			sensor.humidify(Math.random()*10);
//		} else {
//			sensor.encrease(Math.random()*10);
//		}
	}

	/**
	 * Deactivate 2 Pumps and open the Gate
	 */
	private void terminateDrying() {
		pumpA.sendDeactivate();
		pumpB.sendDeactivate();
		signals.switchLampBOn();
		gate.sendOpenGate();
		while (gate.receivedGateClosed()) {
			System.out.println("Gate open: in progress...");
		}
		signals.switchLampBOff();
	}

}
