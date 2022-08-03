package jsge.demo.stage_1;

import java.awt.event.KeyEvent;
import java.security.Key;

import jsge.components.Transform;
import jsge.core.GameKeyEvent;
import jsge.core.GameKeyEvent.EventType;
import jsge.core.GameObject;
import jsge.data.AudioClip;
import jsge.prefabs.Text;
import jsge.utils.Layers.Layer;
import jsge.utils.Point;
import jsge.utils.Timer;


//Isso tudo será feito a partir da gambiarra.
public class CutsceneHandler extends GameObject {
	
	

	private boolean animationInProgress = false;
	
	private boolean startUpInProgress = true; //needed for animation on entry
	
	public boolean hasEnded = false;
	
	private Text textBoxUI;
	
	private Text speakerNameUI;
	
	private AudioClip  bgmDialogue;
	
	//três paineis, um pra textbox, um pro ator (player) e outro pro boss
	
	private GameObject bossSprite;
	private GameObject textBoxSprite;
	private GameObject playerSprite;
	
	private int letterCount = 0;
	
	private String buffer = ""; //usada para a animação
	
	Timer letterAnimationTimer = null;
	
	
	//painel de texto, vou usar um truque para salvar tempo
	
	private int dialogueStep = -1;
	private String[] dialogueText = {
			"They are definitely multiplying...",
			"Helloooooooooooooo",
			"????",
			"Who the hell are you?",
			"I'm a ghost, can't you see?",
			"I didn't ask that..."
	};
	
	private String[] dialogueSpeaker= {
			"Marisa",
			"Umbra",
			"Marisa",
			"Marisa",
			"Umbra",
			"Marisa"
	};
	
	public CutsceneHandler() {
		super("SceneHandler", Layer.UI);
		
		//Final coords for
		//Boss Sprite = 120,310
		//player = 430,310
		//textBox 255,410
		//TextUI 40,380
		//TextSpeaker 40,345
		bossSprite = new GameObject("boss","src/main/resources/Assets/Scratchs/Umbra_Portrait.png",new Transform(120,310),Layer.UI);
		playerSprite  = new GameObject("player","src/main/resources/Assets/Scratchs/marisa_dialogue.png",new Transform(430,310),Layer.UI);
		
		textBoxSprite =  new GameObject("dialogueBox","src/main/resources/Assets/Scratchs/dialogueBox.png",new Transform(255,410),Layer.UI);
		
		bgmDialogue =  new AudioClip("src/main/resources/Sounds/dialogue00.wav");
		bgmDialogue.setLoop(true);
		bgmDialogue.play();
		
		playerSprite.getTransform().setScale(0.4, 0.4);
		bossSprite.getTransform().setScale(0.75, 0.75);
		
		textBoxUI = new Text("text","",new Transform(40,380), Layer.UI,null);
		textBoxUI.setSize(10);
		speakerNameUI = new Text("text","",new Transform(40,345), Layer.UI,null);
		startGameObjectReceivingInput(this);
		System.out.println("CutsceneHandler: CutsceneHandler has started, taking control from SceneHandler");
		
		nextLine();
				
	}
	
	
	private void updateUI() {
		speakerNameUI.setText(dialogueSpeaker[dialogueStep]);
		textBoxUI.setText(buffer);
		
		
	}
	
	
	private void letterAnimation() {
		buffer += dialogueText[dialogueStep].charAt(letterCount);
		letterCount++;
		updateUI();
		if(letterCount == dialogueText[dialogueStep].length()) {
			letterAnimationTimer.destroyTimer();
			letterAnimationTimer = null;
			animationInProgress = false;
		}
		
		
		
		//buffer for letter, timer for index, update and add to string of text on the ui letter by letter
	}
	
	private void nextLine() {
		if(this.dialogueStep == dialogueText.length -1) {
			hasEnded = true;
			System.out.println("CutsceneHandler: End of Cutscene, giving back control to SceneHandler");
			return;
		}
		else {
			this.dialogueStep++;
			spriteAnimation();
			buffer = "";
			animationInProgress = true;
			letterAnimationTimer = new Timer(()->letterAnimation(),0.05,true);
			letterCount = 0;
		}
		
	}
	
	private void spriteAnimation() {
		
		if(this.dialogueSpeaker[dialogueStep].equalsIgnoreCase("marisa")) {
			this.bossSprite.getTransform().setScale(0.70,0.70);
			this.bossSprite.getTransform().setPosition(new Point(110,300));
			
			this.playerSprite.getTransform().setScale(0.45,0.45);
			this.playerSprite.getTransform().setPosition(new Point(400,300));
			
		}
		else {
			this.bossSprite.getTransform().setScale(0.8,0.8);
			this.bossSprite.getTransform().setPosition(new Point(120,290));
			
			this.playerSprite.getTransform().setScale(0.40,0.40);
			this.playerSprite.getTransform().setPosition(new Point(430,310));
			
		}
		//slight offcenter and descolation for the left/right, reset other actor
		
		//check for who is the speaker
		
	}
	
	@Override
	public void receiveInput(GameKeyEvent event) {
		if(event.getEventType() == EventType.Pressed) {
			if(event.getKeyCode() == KeyEvent.VK_Z) {
				if(animationInProgress == false) {
					nextLine();
					return;
				}
			}
		}
		else {
			return;
		}
		
	}
	
	public void destroyDialogue() {
		
		bgmDialogue.stop();
		GameObject.destroyGameObject(bossSprite);
		GameObject.destroyGameObject(speakerNameUI);
		GameObject.destroyGameObject(textBoxSprite);
		GameObject.destroyGameObject(playerSprite);
		GameObject.destroyGameObject(textBoxUI);
		GameObject.stopGameObjectReceivingInput(this);
		GameObject.destroyGameObject(this);
		
	}
	
	
	
	

}
