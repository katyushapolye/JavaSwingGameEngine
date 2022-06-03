package jsge.core;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


import jsge.components.AnimationController;
import jsge.components.Collider;
import jsge.components.Sprite;
import jsge.components.Transform;
import jsge.utils.*;
import jsge.utils.Layers.Layer;

import java.util.ArrayList;

public class GameObject {
	protected String name;
	
	
	//COMPONENTS Talvez trocar por uma lista de uma superclasse de componentes
	protected Sprite sprite = null;
	protected AnimationController animationController = null;
	protected Transform transform = null;
	protected Collider collider = null;
	
	
	//Flags
	
	protected Layer layer = null;
	private boolean receivesInput = false;
	private boolean wasSetADefaultSprite = true;
	
	
	
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
		for(int i = 0;i<totalGameObjects.size();i++) {
			totalGameObjects.get(i).onDestroy();
			
			
		}
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
		this.collider = new Collider(colliderRadius,transform);
		this.sprite = new Sprite(pathToSprite);
		if(this.sprite.getSprite() ==  null) {
			this.wasSetADefaultSprite = false;
		}
		
		
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	
	
	public GameObject(String name,Transform transform,Layer initLayer){
		if(name == null) {
			System.out.println("GameObject: Error - Nameless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0001 - GameObject Must Have A Valid Identification"));
		}
		this.name = name;
		if(initLayer == null) {
			System.out.println("GameObject: Error - Layerless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0002 - GameObject Must Have A Valid Layer"));
		}
		this.sprite = new Sprite();
		this.layer = initLayer;
		this.receivesInput = false;
		this.transform = transform;
		this.wasSetADefaultSprite = false;
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	public GameObject(String name,Transform transform,Layer initLayer,boolean receivesInput){
		if(name == null) {
			System.out.println("GameObject: Error - Nameless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0001 - GameObject Must Have A Valid Identification"));
		}
		this.name = name;
		if(initLayer == null) {
			System.out.println("GameObject: Error - Layerless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0002 - GameObject Must Have A Valid Layer"));
		}
		this.sprite = new Sprite();
		this.layer = initLayer;
		this.transform = transform;
		this.wasSetADefaultSprite = false;
		this.receivesInput = receivesInput;
		
		if(receivesInput) {
			inputReceiverGameObjects.add(this);
		}
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	public GameObject(String name,String pathToSprite,Transform transform,Layer initLayer,int colliderWidth,int colliderHeight){
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
		this.collider = new Collider(colliderWidth,colliderHeight,this.transform);
		this.sprite = new Sprite(pathToSprite);
		
		if(this.sprite.getSprite() ==  null) {
			this.wasSetADefaultSprite = false;
		}
		
		totalGameObjects.add(this);
		inputReceiverGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	public GameObject(String name,String pathToSprite,Transform transform,Layer initLayer){
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
		this.collider = null;
		this.sprite = new Sprite(pathToSprite);
		
		if(this.sprite.getSprite() ==  null) {
			this.wasSetADefaultSprite = false;
		}
		
		
		totalGameObjects.add(this);
		inputReceiverGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	public GameObject(String name,String pathToSprite,Transform transform,Layer initLayer,int colliderWidth,int colliderHeight,boolean receivesInput){
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
		this.collider = new Collider(colliderWidth,colliderHeight,this.transform);
		this.sprite = new Sprite(pathToSprite);
		
		if(this.sprite.getSprite() ==  null) {
			this.wasSetADefaultSprite = false;
		}
		
		
		totalGameObjects.add(this);
		if(receivesInput) {
			inputReceiverGameObjects.add(this);
		}
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
		this.collider = new Collider(colliderRadius,this.transform);
		this.sprite = new Sprite(pathToSprite);
		
		if(this.sprite.getSprite() ==  null) {
			this.wasSetADefaultSprite = false;
		}
		
		totalGameObjects.add(this);
		if(receivesInput) {
			inputReceiverGameObjects.add(this);
		}
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
		
		this.sprite =  new Sprite();
		this.transform =  new Transform();
		this.wasSetADefaultSprite = false;
		this.transform = new Transform();
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
		
	public void draw(Graphics2D g) {
		if((sprite == null || this.transform == null) && this.wasSetADefaultSprite == true) {
			System.out.println("GameObject: Warning - Transformless or Spriteless GameObject, the sprite may have failed to load or the Transform was manually set as null, Is this intended Behaviour?");;
			return;
		}
		
		;

		g.drawImage(sprite.getSprite(), applyTransform(), null);
		g.setColor(new Color(0,0,255));
		
		//Debug
		try {
		g.drawOval(this.collider.getX()-collider.getRadius(),this.collider.getY() -  collider.getRadius(), collider.getRadius()*2,collider.getRadius()*2); 
		//g.drawRect(this.collider.getX()-collider.getWidth(),this.collider.getY() -  collider.getHeight(), collider.getWidth()*2,collider.getHeight()*2); 
		//g.setPaintMode()
		}
		catch(Exception e) {
			
		}
		//Debug
		g.setColor(new Color(0,0,0));
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
		internalUpdate();
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
	
	public Collider getCollider() {
		return this.collider;
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
		if(sprite == null && this.wasSetADefaultSprite == true) {
			System.out.println("GameObject: Warning - No sprite set as default, please check your GameObject Constructor");
			return null;
		}
		AffineTransform af = new AffineTransform();
		af.translate(this.transform.getX() ,this.transform.getY());
		af.scale(this.transform.getScale()[0],this.transform.getScale()[1]);
		
		af.rotate(Math.toRadians(this.transform.getRotation()));
		af.translate(-sprite.getWidth()/2,-sprite.getHeight()/2);	
		return af;
	}
	
	
	//Uma lista de componentes seria mais interessante
	void internalUpdate() {
		if(collider != null)collider.internalUpdate();
		if(animationController!= null)animationController.internalUpdate();
		
	}

}
