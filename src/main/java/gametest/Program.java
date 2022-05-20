package gametest;
public class Program {


	public static void main(String[] args){
		RendererPanel r = new RendererPanel();
		final long SECOND_IN_NANO = 1000000000l;
		long FPS = 30l;
		long frameTargetTime = SECOND_IN_NANO/FPS;
		long tickStart =  System.nanoTime();
		long deltaTime = 0l;//Tempo desde ultimo frame
		double deltaTimeInSeconds = 0;
		
		
		while(true) {
			if(System.nanoTime() - tickStart >= frameTargetTime) {
				deltaTime = System.nanoTime() - tickStart;
				deltaTimeInSeconds = (double)deltaTime/1000000000;
				tickStart = System.nanoTime();	
				
				//Pool Input

				
				//GameLoop
				
				
				//Renderização
				//r.DrawGameObjects(gmBuffer);
				r.display();
				
				
				//Reset dos timers;
				System.out.printf("DeltaTime:%d nanoSeconds%n",deltaTime);
				System.out.printf("DeltaTime:%f Seconds%n",deltaTimeInSeconds);
				System.out.printf("FPS:%d%n",SECOND_IN_NANO/deltaTime);
				}
			//THread.sleep não é preciso e erra o tempo de sleep, perdendo fps, deixe assim por enquanto até uma ideia melhor
			else {
				/*
				int sleepTime = (int) (frameTargetTime - (tickStart-System.nanoTime()));
				
				try {
					
					Thread.sleep(sleepTime/1000000);
					}
				catch(Exception e){
					System.out.println("SleepAwake");
					}
					
					
				
				}
				*/
			
			
			
		}

	}

}}
