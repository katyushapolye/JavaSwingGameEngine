//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END

package jsge.utils;

import java.util.ArrayList;
import java.util.List;

public class Timer{
	private double interval;
	private boolean recursive;
	
	private long epoch;
	
	private Callback timerCallback;
	
	
	private static List<Timer> allActiveTimers = new ArrayList<Timer>();
	
	
	public static ArrayList<Timer> getAllActiveTimers() {
		return (ArrayList<Timer>) allActiveTimers;
	};
	
	
	public Timer(Callback initcallback,double intervalInSeconds,boolean recursive){
		this.timerCallback =  initcallback;
		this.interval = intervalInSeconds;
		this.recursive = recursive;
		epoch = System.currentTimeMillis();
		allActiveTimers.add((Timer)this);
	}
	
	public void destroyTimer() {
		allActiveTimers.remove(this);
	}
	
	
	public static void destroyAllTimers() {
		allActiveTimers.clear();
	}
	
	void executeCallback() {
		timerCallback.callback();
	}
	
	
	//Pode crasha se tipo T for imcompativel com o T n?
	void setCallback(Callback callback) {
		this.timerCallback = callback;
	}
	
	public void checkTimer() {
		if(epoch+(interval*1000) <= System.currentTimeMillis()) {
			timerCallback.callback();
			//System.out.println("Timer: Sending Callback, funcion: " + timerCallback.toString());
			if(this.recursive) {
				epoch =  System.currentTimeMillis();
				return;
				}
			else {
				allActiveTimers.remove(this);
			}
		}
	}
	
	
	

}
