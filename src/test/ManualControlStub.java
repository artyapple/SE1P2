package test;

import boundaryclasses.IManualControl;

public class ManualControlStub implements IManualControl {

	@Override
	public boolean receivedAcknowledgement() {
		return true;
	}

}
