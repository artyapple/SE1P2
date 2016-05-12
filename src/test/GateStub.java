package test;

import boundaryclasses.IGate;

public class GateStub implements IGate {
	
	private boolean gateClosed;

	@Override
	public void sendCloseGate() {
		gateClosed = true;
		System.out.println("gate close");
	}

	@Override
	public void sendOpenGate() {
		gateClosed = false;
		System.out.println("gate open");
	}

	@Override
	public boolean receivedGateClosed() {
		return gateClosed == true;
	}

	@Override
	public boolean receivedGateOpen() {
		return gateClosed == false;
	}

}
