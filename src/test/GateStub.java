package test;

import boundaryclasses.IGate;

public class GateStub implements IGate {

	private boolean gateClosed;
	//var for testing
	public static int cnt;

	@Override
	public void sendCloseGate() {
		System.out.println(">Gate close");
		try {
			Thread.sleep(500);
			gateClosed = true;
			cnt++;
		} catch (InterruptedException e) {
			System.out.print("An error occurred while closing the gate!");
		}
	}

	@Override
	public void sendOpenGate() {
		System.out.println(">Gate open");
		try {
			Thread.sleep(500);
			gateClosed = false;
			cnt++;
		} catch (InterruptedException e) {
			System.out.print("An error occurred while closing the gate!");
		}
	}

	@Override
	public boolean receivedGateClosed() {
		return (gateClosed == true);
	}

	@Override
	public boolean receivedGateOpen() {
		return (gateClosed == false);
	}
	
	
	//for testing
	public static int getGateActivity()
	{
		return cnt;
	}
}
