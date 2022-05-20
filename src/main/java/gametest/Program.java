package gametest;
public class Program {


	public static void main(String[] args){
		RendererPanel r = new RendererPanel();
		
		long FPS = 60l;
		long frameTargetTime = 1000000000/FPS;
		long tickStart =  System.nanoTime();
		long deltaTime = 1;//Tempo desde ultimo frame
		while(true) {
			if(System.nanoTime() - tickStart >= frameTargetTime) {
				 
				
				//Pool Input

				
				//GameLoop
				
				
				//Renderização
				//r.DrawGameObjects(gmBuffer);
				//r.display();
				deltaTime = System.nanoTime() - tickStart;
				tickStart = System.nanoTime();	
				System.out.println("FPS: " + 1000000000/deltaTime);
				}
			//Salva a CPU, não é 100% preciso mas é bom o suficiente
			else {
				
				long sleepTime = (long) (frameTargetTime - tickStart);
				
				try {
					Thread.sleep(sleepTime);
					}
				catch(Exception e){
					
					}
					
					
				
				}
			
			
			
		}

	}

}
