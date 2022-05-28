package jsge.components;

import jsge.utils.Point;

public class Collider{
	
	public static enum ColliderType{
		Rect,
		Circle,
		
	}
	
	private ColliderType colliderType;
	
	private Transform parentTransform = null;
	private int X;
	private int Y;
	private int width;
	private int height;
	
	private int radius = 0;
	
	public Collider(int radius, Transform parentTransform) {
		this.radius = radius;
		this.colliderType = ColliderType.Circle;
		if(parentTransform == null) {
			System.out.println("Collider: Warning - Parentless Collider, It's position will not be updated, Is this intended Behaviour?");
			return;
		}
		this.parentTransform =  parentTransform;
		this.X = parentTransform.X;
		this.Y = parentTransform.Y;
	}
	
	public Collider(int width,int height,Transform parentTransform) {
		this.width = width;
		this.height = height;
		this.colliderType = ColliderType.Rect;
		if(parentTransform == null) {
			System.out.println("Collider: Warning - Parentless Collider, It's position will not be updated, Is this intended Behaviour?");
			return;
		}
		this.parentTransform = parentTransform;
		this.X = parentTransform.X;
		this.Y = parentTransform.Y;
	}
	
	public void internalUpdate() {
		if(parentTransform == null) {
			System.out.println("Collider: Warning - Parentless Collider, It's position will not be updated, Is this intended Behaviour?");
			return;
		}
		this.Y =  parentTransform.Y;
		this.X = parentTransform.X;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	
	public int getRadius() {
		return this.radius;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public boolean isColliding(Collider colision) {
		if(colision.colliderType == ColliderType.Rect) {
			if(this.colliderType ==  ColliderType.Rect) {
				//Pouco otimizado, pensar em quadrantes dps
				if(this.Y < colision.X + colision.width &&
					this.X + this.width > colision.X &&
					this.Y < colision.Y + colision.height &&
					this.Y + this.height > colision.Y)
				{
					return true;
				}
				else {
					//this é circular e e colision é rect
					
					float dx = Math.abs(this.X - colision.X);
					float dy = Math.abs(this.Y - colision.Y);
					if(dx > colision.width/2 + this.radius) {return false;}
					if(dy > colision.height/2 + this.radius) {return false;}
					
					double cornerD = Math.pow((dx - (colision.width/2)),2) + Math.pow((dy - (colision.height/2)),2);
					if(cornerD <= Math.pow(this.radius,2)) {
						return true;
					}
					
					return false;
				}
			}
			
			else {
				return false;
			}
			
		}
		else if(colision.colliderType == ColliderType.Circle) {
			if(this.colliderType ==  ColliderType.Circle) {
				if(Point.distance(new Point(this.X,this.Y),new Point(colision.X,colision.Y)) <= this.radius + colision.radius) {
					return true;
				}
				else {
					return false;
				}
				
			}
			else {
				//this é rect e e colision é circular
				
				float dx = Math.abs(colision.X - this.X);
				float dy = Math.abs(colision.Y - this.Y);
				if(dx > this.width/2 + colision.radius) {return false;}
				if(dy > +this.height/2 + colision.radius) {return false;}
				
				double cornerD = Math.pow((dx - (this.width/2)),2) + Math.pow((dy - (this.height/2)),2);
				if(cornerD <= Math.pow(colision.radius,2)) {
					return true;
				}
				
				return false;
			}
		}
		
		else {
			System.out.println("Collider: Error - Shapeless Collider initialized");
			throw new RuntimeException(new Error("Terminated - Error 0x0003 - Colliders must not be initialized without a shape"));
		}
		
	}

}
