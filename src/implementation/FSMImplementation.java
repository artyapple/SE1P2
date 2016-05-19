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

	public FSMImplementation(IPump pumpA, IPump pumpB, IGate gate,
			IOpticalSignals signals, IHumidifier humidifier,
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
	}

	@Override
	public void evaluate() {
		
		switch (state) {
		case HumidityOkay:
			//externalAction(); 
			updateState();
			sensor.getHumidity();
			break;
		// Trockung
		case Drying:
			signals.switchLampBOn();
			gate.sendCloseGate();
			while (gate.receivedGateOpen()) {
				System.out.println("Gate close: in progress...");
			}
			signals.switchLampBOff();
			startPumps(pumpA, pumpB, timer); // activate Pump A and B
			if (pumpsActivated(pumpA, pumpB, timer)) {
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
			state = FSMState.HumidityOkay;
			break;
		// Fehlerzustand
		case Error:
			System.out.println("Waiting for manual control...");
			if (operatorPanel.receivedAcknowledgement()) {
				System.out.println("Manual controll in progress...");
				state = FSMState.HumidityOkay;
			}			
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

	/**
	 * Activate timer & 2 Pumps
	 */
	private static void startPumps(IPump pump1, IPump pump2, ITimer timer) {
		timer.startTime(5);
		pump1.sendActivate();
		pump2.sendActivate();
	}

	
	/**
	 * Check 2 Pumps
	 * 
	 * @return true - Pumps are activated, otherwise false 
	 */
	private static boolean pumpsActivated(IPump pump1, IPump pump2, ITimer timer) {
		while (!timer.isTimerExpired()) {
			if (pump1.receivedActivated() && pump2.receivedActivated()) {
				return true;
			}
		}
		return false;
	}
	
	public implementation.FSMState getCurrentState()
	{
		return state;
	}

}
