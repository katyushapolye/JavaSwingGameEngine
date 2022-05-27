package jsge.core;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jsge.components.AnimationController;
import jsge.util.*;
import jsge.util.Utils.Layer;

import java.util.ArrayList;

public class GameObject {
	protected String name;
	
	//To create sprite class
	protected BufferedImage sprite = null;
	
	//To create transform class
	protected int X = 0;
	protected int Y = 0;
	protected int degrees = 0;
	protected double[] scale =  new double[2];
	
	//Planned components
	
	protected AnimationController animationController = null;
	
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
	public GameObject(String name,String pathToSpriteImage,int initX,int initY,int rotation,Layer initLayer,int colliderRadius){
		this.name = name;
		this.layer = initLayer;
		this.X = initX;
		this.degrees = rotation;
		this.Y = initY;
		this.colliderRadius = colliderRadius;
		scale[0] = 1;
		scale[1] = 1;
		try {
		sprite = ImageIO.read(new File(pathToSpriteImage));
		}
		catch(IOException e){
			e.printStackTrace();
		}	
		
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		
	}
	
	//renderiza o objeto no graphics target desejado
	public void draw(Graphics2D g) {
		if(sprite == null || sprite.getWidth() == -1) {
			//System.out.println("Warning - Spriteless GameObject, the sprite may have failed to load");
			return;
		}
		g.drawImage(sprite, applyTransform(), null);
		g.setColor(Color.GREEN);
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
	
	
	public void setAnimationController(AnimationController animationController) {
		this.animationController = animationController;
	}
	
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
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
	
	protected AffineTransform applyTransform() {
		AffineTransform af = new AffineTransform();
		af.translate(this.X, this.Y);
		af.scale(this.scale[0],this.scale[1]);

		af.rotate(Math.toRadians(degrees));
		af.translate(-sprite.getWidth()/2,-sprite.getHeight()/2);	
		return af;
	}

}
