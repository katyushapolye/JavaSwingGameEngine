package jsge.components;

import jsge.utils.Point;

public class Collider {

	public static enum ColliderType {
		Rect, Circle,

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
		if (parentTransform == null) {
			System.out.println(
					"Collider: Warning - Parentless Collider, It's position will not be updated, Is this intended Behaviour?");
			return;
		}
		this.parentTransform = parentTransform;
		this.X = parentTransform.X;
		this.Y = parentTransform.Y;
	}

	public Collider(int width, int height, Transform parentTransform) {
		this.width = width;
		this.height = height;
		this.colliderType = ColliderType.Rect;
		if (parentTransform == null) {
			System.out.println(
					"Collider: Warning - Parentless Collider, It's position will not be updated, Is this intended Behaviour?");
			return;
		}
		this.parentTransform = parentTransform;
		this.X = parentTransform.X;
		this.Y = parentTransform.Y;
	}

	public void internalUpdate() {
		if (parentTransform == null) {
			System.out.println(
					"Collider: Warning - Parentless Collider, It's position will not be updated, Is this intended Behaviour?");
			return;
		}
		this.Y = parentTransform.Y;
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
		if(this.radius == 0 || this.height ==  0 || this.width == 0 || colision.radius == 0 || colision.height == 0 || colision.width ==0 || colision == null) {
			return false;
		}
		if (colision.colliderType == ColliderType.Rect) {
			if (this.colliderType == ColliderType.Rect) {
				if(this.X<colision.X+colision.width && this.X+this.width>colision.X && this.Y<colision.Y+colision.height&&this.Y+this.height>colision.Y)//n sei se é this e colision ali
					System.out.println("Collision Detected");
					return true;
			        }

			
			else {
				//implementar aqui colisao circulo-rect
				if(Point.distance(new Point(this.X, this.Y),new Point(colision.X, colision.Y))<= colision.radius){
					System.out.println("Collision Detected");
					
					System.out.println("Not implemented collision between Rect and Circle");
					return true;//tava false
				}
			}
			
		}
		else{
			if (this.colliderType == ColliderType.Circle) {
				if (Point.distance(new Point(this.X, this.Y), new Point(colision.X, colision.Y)) <= this.radius+ colision.radius) {
					return true;
				}
			}
			else {
				//implementar aqui colisao circulo-rect
				if(Point.distance(new Point(this.X, this.Y),new Point(colision.X, colision.Y))<= colision.radius){
                                        System.out.println("Collision Detected");

				System.out.println("Not implemented collision between Rect and Circle");
				return true;//tava false
				}

			}
		
		return false;
		}
		return false;
	}
		
}
	
		



