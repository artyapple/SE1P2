package test;

import boundaryclasses.IManualControl;

public class ManualControlStub implements IManualControl {
	
	private static int errorCnt;

	@Override
	public boolean receivedAcknowledgement() {
		errorCnt++;
		return true;
	}
	
	public static int getErrorCnt() {
		return errorCnt;
	}

}
