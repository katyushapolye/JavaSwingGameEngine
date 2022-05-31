package jsge.demo;

import jsge.data.Scene;
import jsge.prefabs.Text;
import jsge.utils.Callback;
import jsge.utils.Timer;

public class MenuScene extends Scene{
	
	
	
	Text[] menuOptions;
	
	Callback<Integer> myCallback = (args)-> callbackFunction(args);
	Callback<Void> myCallback2 = (Void) -> callbackFunction2();
	
	Integer[] args = {1,2,3};
	
	Timer<Integer> timer = new Timer<Integer>(myCallback,args,3,false);
	Timer<Void> timer2 = new Timer<Void>(myCallback2,null,6,true);
	
	public MenuScene(String sceneName) {
		super(sceneName);
		
	
	}
	

	@Override
	public void sceneBootStrap() {		
		
	}	
	
	@Override
	public void sceneUpdate(){
		 	args[2] = 10;
	}
	
	
	private Void callbackFunction(Integer[] args) {
		for (Integer integer : args) {
			System.out.println(integer);
		}
		return null;

	}
	private Void callbackFunction2() {
		System.out.println("Lambda NULL");
		return null;

	}
	

}
