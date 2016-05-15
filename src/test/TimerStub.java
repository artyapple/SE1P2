package test;

import java.util.Timer;
import java.util.TimerTask;

import boundaryclasses.ITimer;

public class TimerStub implements ITimer {

	private boolean timerExpired;

	@Override
	public void startTime(double seconds) {
		timerExpired = false;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerExpired = true;
			}
		}, (long) (seconds * 1000));
	}

	@Override
	public boolean isTimerExpired() {
		return timerExpired;
	}

}
