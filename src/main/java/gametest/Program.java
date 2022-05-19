package gametest;
import java.time.Clock;
public class Program {

	
	//FAZR BENCHMARK NESSA BAGAÇA, PERFORMANCE CAI QUANDO MOUSE FORA DA JANELA
	public static void main(String[] args){
		RendererPanel r = new RendererPanel();
		Clock clock = Clock.systemDefaultZone();
		GameObject debugObject =  new GameObject("src/main/resources/Sprite_Sheet.png",-800,-800);
		GameObject debugObject2 =  new GameObject("src/main/resources/Sprite_Sheet.png",0,-400);
		GameObject[] gmBuffer =  new GameObject[2];
		gmBuffer[0] = debugObject;
		gmBuffer[1] = debugObject2;
		
		long tickStart = clock.millis();
		
		
		while(true) {
			if(clock.millis() - tickStart >= 33.3) {
				tickStart = clock.millis();
				gmBuffer[0].updatePosition(1, 0);
				gmBuffer[1].updatePosition(-1,0);
				
				r.DrawGameObjects(gmBuffer);
				r.display();
				System.out.println("TickUpdate");
			}
			else {
				continue;
			}
			
			
			
		}

	}

}
