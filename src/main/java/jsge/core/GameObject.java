package jsge.core;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import jsge.components.AnimationController;
import jsge.components.Sprite;
import jsge.util.*;
import jsge.util.Utils.Layer;

import java.util.ArrayList;

public class GameObject {
	protected String name;
	
	//To create sprite class
	protected Sprite sprite = null;
	protected AnimationController animationController = null;
	
	//To create transform class
	protected int X = 0;
	protected int Y = 0;
	protected int degrees = 0;
	protected double[] scale =  new double[2];
	
	//Planned components
	
	
	protected Layer layer = null;
	protected int colliderRadius = 0;
	
	
	private static int TOTAL_GAME_OBJECT_COUNT = 0;
	private static ArrayList<GameObject> totalGameObjects =  new ArrayList<GameObject>();
	
	public static int getTotalGameObjectCount() {
		return TOTAL_GAME_OBJECT_COUNT;
	}
	public static ArrayList<GameObject> getAllGameObjects() {
		return totalGameObjects;
	}
	
	public static void destroyAllGameObjects() {
		TOTAL_GAME_OBJECT_COUNT= 0;
		totalGameObjects.clear();
	}
	
	public static void destroyGameObject(GameObject gameObjectToDestroy) {
		gameObjectToDestroy.onDestroy();
		totalGameObjects.remove(gameObjectToDestroy);
		
	}
	
	
	
	//Adicionar overloads nesse metodo, para opções vazias etc
	public GameObject(String name,String pathToSprite,int initX,int initY,int rotation,Layer initLayer,int colliderRadius){
		if(name == null) {
			System.out.println("Error - Nameless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0001 - GameObject Must Have A Valid Identification"));
		}
		this.name = name;
		if(initLayer == null) {
			System.out.println("Error - Layerless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0002 - GameObject Must Have A Valid Layer"));
		}
		this.layer = initLayer;
		
		//Vai ser um transform
		this.X = initX;
		this.degrees = rotation;
		this.Y = initY;
		this.colliderRadius = colliderRadius;
		scale[0] = 1;
		scale[1] = 1;
		
		
		this.sprite = new Sprite(pathToSprite);
		
		
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	
	public GameObject(String name,Layer initLayer){
		if(name == null) {
			System.out.println("Error - Nameless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0001 - GameObject Must Have A Valid Identification"));
		}
		this.name = name;
		if(initLayer == null) {
			System.out.println("Error - Layerless GameObject initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0002 - GameObject Must Have A Valid Layer"));
		}
		this.layer = initLayer;
		this.X = 0;
		this.degrees = 0;
		this.Y = 0;
		this.colliderRadius = 0;
		scale[0] = 1;
		scale[1] = 1;
		
		
		
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	
	
	
	
	
	
	
	
	//renderiza o objeto no graphics target desejado
	public void draw(Graphics2D g) {
		if(sprite == null) {
			//System.out.println("Warning - Spriteless GameObject, the sprite may have failed to load");
			return;
		}
		g.drawImage(sprite.getSprite(), applyTransform(), null);
		//debug
	}
	
	
	
	//Metodos para override
	
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
	
	
	public Utils.Layer getLayer() {
		return this.layer;
	}
	
	public void setPosition(int newX,int newY) {
		this.X = newX;
		this.Y = newY;
	}
	
	public void setRotation(int degrees) {
		this.degrees = degrees;
	}
	
	public void offsetPosition(int newX,int newY) {
		this.X  +=  newX;
		this.Y +=  newY;
	}
	
	public void offsetRotation(int degrees) {
		this.degrees = this.degrees+degrees;
	}
	
	public int getColliderRadius() {
		return this.colliderRadius;
	}
	
	public Point getPosition() {
		return new Point(this.X,this.Y);
	}
	
	public int getRotation() {
		return this.degrees;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setScale(double scaleX,double scaleY) {
		this.scale[0] = scaleX;
		this.scale[1] = scaleY;
	}
	//Fim getters and setters
	
	
	//Metodos internos para controle e transform
	protected AffineTransform applyTransform() {
		AffineTransform af = new AffineTransform();
		af.translate(this.X, this.Y);
		af.scale(this.scale[0],this.scale[1]);

		af.rotate(Math.toRadians(degrees));
		af.translate(-sprite.getWidth()/2,-sprite.getHeight()/2);	
		return af;
	}

}
