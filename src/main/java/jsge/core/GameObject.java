package jsge.core;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import jsge.components.AnimationController;
import jsge.components.Sprite;
import jsge.components.Transform;
import jsge.utils.*;
import jsge.utils.Layers.Layer;

import java.util.ArrayList;

public class GameObject {
	protected String name;
	
	
	//COMPONENTS
	protected Sprite sprite = null;
	protected AnimationController animationController = null;
	protected Transform transform = null;
	
	
	//Flags
	
	protected Layer layer = null;
	private boolean receivesInput = false;
	
	protected int colliderRadius = 0;
	
	
	private static int TOTAL_GAME_OBJECT_COUNT = 0;
	private static ArrayList<GameObject> totalGameObjects =  new ArrayList<GameObject>();
	private static ArrayList<GameObject> inputReceiverGameObjects =  new ArrayList<GameObject>();
	
	public static int getTotalGameObjectCount() {
		return TOTAL_GAME_OBJECT_COUNT;
	}
	public static ArrayList<GameObject> getAllGameObjects() {
		return totalGameObjects;
	}
	
	public static ArrayList<GameObject> getAllInputReceiverGameObjects(){
		return inputReceiverGameObjects;
	}
	
	public static void destroyAllGameObjects() {
		TOTAL_GAME_OBJECT_COUNT= 0;
		//Call all destroy functions
		totalGameObjects.clear();
	}
	
	public static void destroyGameObject(GameObject gameObjectToDestroy) {
		gameObjectToDestroy.onDestroy();
		totalGameObjects.remove(gameObjectToDestroy);
		
	}
	
	
	
	//Adicionar overloads nesse metodo, para opções vazias etc
	public GameObject(String name,String pathToSprite,Transform transform,Layer initLayer,int colliderRadius){
		if(name == null) {
			System.out.println("GameObject: Error - Nameless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0001 - GameObject Must Have A Valid Identification"));
		}
		this.name = name;
		if(initLayer == null) {
			System.out.println("GameObject: Error - Layerless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0002 - GameObject Must Have A Valid Layer"));
		}
		this.layer = initLayer;
		this.receivesInput = false;

		this.transform = transform;
		
		this.sprite = new Sprite(pathToSprite);
		
		
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	
	public GameObject(String name,String pathToSprite,Transform transform,Layer initLayer,int colliderRadius,boolean receivesInput){
		if(name == null) {
			System.out.println("GameObject: Error - Nameless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0001 - GameObject Must Have A Valid Identification"));
		}
		this.name = name;
		if(initLayer == null) {
			System.out.println("GameObject: Error - Layerless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0002 - GameObject Must Have A Valid Layer"));
		}
		this.layer = initLayer;
		this.receivesInput = receivesInput;

		this.transform = transform;
		
		this.sprite = new Sprite(pathToSprite);
		
		
		totalGameObjects.add(this);
		inputReceiverGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	
	public GameObject(String name,Layer initLayer){
		if(name == null || name.contentEquals("")) {
			System.out.println("GameObject: Error - Nameless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0001 - GameObject Must Have A Valid Identification"));
		}
		this.name = name;
		if(initLayer == null) {
			System.out.println("GameObject: Error - Layerless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0002 - GameObject Must Have A Valid Layer"));
		}
		
		this.transform = new Transform();
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
		
	public void draw(Graphics2D g) {
		if(sprite == null || this.transform == null) {
			System.out.println("GameObject: Warning - Spriteless or Transformless GameObject, the sprite may have failed to load or the Transform was manually set as null, Is this intended Behaviour?");;
			return;
		}
		g.drawImage(sprite.getSprite(), applyTransform(), null);
		//debug
	}
	

	//Metodos para override
	
	public void receiveInput(GameKeyEvent event) {
		if(this.receivesInput == true) {
			System.out.println("GameObject: Warning - GameObject " + this.name + " is receiving input, but the method has not been overwritten, is this intended Behaviour?");
			
		}
	}
	public void onCollision(GameObject collision) {
		System.out.println(this.name + " COLLIDED WITH " + collision.name);
	}
	
	public void update(double deltaTime) {
		if(animationController != null) {
			animationController.updateAnimationController();
		}
		//System.out.println("Waring - Object default Update has not been overridden, Please do not instantiate raw GameObjects");
	}
	
	public void onDestroy() {
		return;
	};
	
	//Getters e Setters
	
	public void setSpriteComponent(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSpriteComponent() {
		return this.sprite;
	}
	
	
	public void setAnimationController(AnimationController animationController) {
		this.animationController = animationController;
	}
	
	
	public Layers.Layer getLayer() {
		return this.layer;
	}
	
	
	public int getColliderRadius() {
		return this.colliderRadius;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public Transform getTransform() {
		return this.transform;
	}
	
	public void setTransformComponent(Transform transform) {
		this.transform = transform;
	}
	//Fim getters and setters
	
	
	//Metodos internos para controle e transform
	protected AffineTransform applyTransform() {
		AffineTransform af = new AffineTransform();
		af.translate(this.transform.getX() ,this.transform.getY());
		af.scale(this.transform.getScale()[0],this.transform.getScale()[1]);

		af.rotate(Math.toRadians(this.transform.getRotation()));
		af.translate(-sprite.getWidth()/2,-sprite.getHeight()/2);	
		return af;
	}

}
