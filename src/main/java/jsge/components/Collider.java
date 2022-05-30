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
		if(colision == null) {
			return false;
		}
		if (colision.colliderType == ColliderType.Rect) {
			if (this.colliderType == ColliderType.Rect) {
				//implementar aqi AABB
				System.out.println("Not implemented AABB collision");
				return false;

			}
			else {
				//implementar aqui colisao circulo-rect
				System.out.println("Not implemented collision between Rect and Circle");
				return false;
			}
		}
			
		else {
			if (this.colliderType == ColliderType.Circle) {
				if (Point.distance(new Point(this.X, this.Y), new Point(colision.X, colision.Y)) <= this.radius+ colision.radius) {
					return true;
				}
			}
			else {
				//implementar aqui colisao circulo-rect
				System.out.println("Not implemented collision between Rect and Circle");
				return false;

			}
		}
		return false;
	}
}
	
		


