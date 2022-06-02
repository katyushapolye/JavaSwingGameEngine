package jsge.prefabs;

import java.awt.Color;
import java.awt.Graphics2D;

import jsge.components.Sprite;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.utils.Layers.Layer;




//Panel with no colliderbox, only color and sprite, good for fast UI development
public class Panel extends GameObject {
	Color color = new Color(0, 0, 0, 0);
	int height = -1;
	int width = -1;

	public Panel(String panelName, Layer layer, int width, int height, int x, int y) {
		super(panelName, new Transform(x, y), layer);
		this.width = width;
		this.height = height;
		this.sprite = null;
		
	}

	public Panel(String panelName, Layer layer, int width, int height, int x, int y, Color color) {
		super(panelName, new Transform(x, y), layer);
		this.width = width;
		this.sprite = null;
		this.height = height;
	}
	public Panel(String panelName,String pathToSprite, Layer layer, int width, int height, int x, int y, Color color) {
		
		super(panelName, new Transform(x, y), layer);
		this.width = width;
		this.sprite = new Sprite(pathToSprite);
		this.height = height;
		
	}

	public void offsetColor(int r, int g, int b, int a) {
		if (this.color.getAlpha() + a > 255) {
			a = 255 - this.color.getAlpha();
		}
		if (this.color.getRed() + r > 255) {
			r = 255 - this.color.getRed();
		}
		if (this.color.getGreen() + g > 255) {
			g = 255 - this.color.getGreen();
		}
		if (this.color.getBlue() + b > 255) {
			b = 255 - this.color.getBlue();
		}
		this.color = new Color(this.color.getRed() + (r), this.color.getGreen() + (g), this.color.getBlue() + (b),this.color.getAlpha() + a);
	}
	
	public void setColor(Color color) {
		this.color =  color;
	}
	
	public void offsetAlpha(int a) {
		if(this.color.getAlpha() + a > 255) {
			a = 255 - this.color.getAlpha();
		}
		this.color = new Color(this.color.getRed(),this.color.getGreen(),this.color.getBlue(),this.color.getAlpha()+a);
		if(this.sprite == null) {return;}
		
		sprite.setAlpha(this.sprite.getAlpha() + a);
		
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawRect(transform.getX() - this.width / 2, this.transform.getY() - this.height / 2, width, height);
		g.setColor(color);
		g.fillRect(transform.getX() - this.width / 2, this.transform.getY() - this.height / 2, width, height);
		g.setColor(new Color(0, 0, 0));
	}

}
