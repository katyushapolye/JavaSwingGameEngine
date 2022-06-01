package jsge.utils;

import java.util.ArrayList;
import java.util.List;

public class Timer <T> {
	private double interval;
	private boolean recursive;
	
	private long epoch;
	
	private Callback<T> timerCallback;
	private T[] args = null;
	
	private static List<Timer<Object>> allActiveTimers = new ArrayList<Timer<Object>>();
	
	
	public static ArrayList<Timer<Object>> getAllActiveTimers() {
		return (ArrayList<Timer<Object>>) allActiveTimers; //Polimorfismo brabo
	};
	
	
	@SuppressWarnings("unchecked")
	public Timer(Callback<T> initcallback,T[] args,double intervalInSeconds,boolean recursive){
		this.timerCallback =  initcallback;
		this.args = args;
		this.interval = intervalInSeconds;
		this.recursive = recursive;
		epoch = System.currentTimeMillis();
		allActiveTimers.add((Timer<Object>)this);
	}
	
	
	Void executeCallback() {
		return timerCallback.callback(args);
	}
	
	
	//Pode crasha se tipo T for imcompativel com o T n?
	void setCallback(Callback<T> callback) {
		this.timerCallback = callback;
	}
	
	public void checkTimer() {
		if(epoch+(interval*1000) <= System.currentTimeMillis()) {
			timerCallback.callback(args);
			System.out.println("Timer: Sending Callback, funcion: " + timerCallback.toString());
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
