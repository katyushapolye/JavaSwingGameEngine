package jsge.util;

public class Clock {
	long epoch = 0;
	public Clock() {
		epoch = System.nanoTime();
	}
	
	public long getElapsedTimeInNanoSeconds() {
		return System.nanoTime() - epoch;
	}
	
	public double getElapsedTimeInSeconds() {
		return (System.nanoTime()- epoch)/((double)1000000000);
	}
	
	
	public double resetClock() {
		long temp = epoch;
		epoch = System.nanoTime();
		return (epoch - temp)/(double)1000000000;
	}

}
