package boundaryclasses;

import test.PumpStub;
import test.TimerStub;

/**
 * Interface for communication to a Pump Controller.
 * 
 * @author Thomas Lehmann
 * @version 2015-11-18
 */

public interface IPump {
	/**
	 * Send the message to start pumping.
	 */
	public void sendActivate();

	/**
	 * Send the message to stop pumping.
	 */
	public void sendDeactivate();

	/**
	 * Check, whether the message for activation confirmation has been received.
	 * In case it has been received, the method returns true and the following
	 * up call of the method returns only true, iff a further message of that
	 * type has been received in the meantime.
	 * 
	 * @return whether the message has been received or not.
	 */
	public boolean receivedActivated();

	/**
	 * Activate timer & 2 Pumps
	 */
	public static void startPumps(IPump pump1, IPump pump2, ITimer timer) {
		timer.startTime(5);
		pump1.sendActivate();
		pump2.sendActivate();
	}
	
	/**
	 * Check 2 Pumps
	 * 
	 * @return true - Pumps are activated, otherwise false 
	 */
	public static boolean pumpsActivated(IPump pump1, IPump pump2, ITimer timer) {
		while (!timer.isTimerExpired()) {
			if (pump1.receivedActivated() && pump2.receivedActivated()) {
				return true;
			}
		}
		return false;
	}
}
