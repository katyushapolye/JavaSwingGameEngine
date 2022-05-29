package jsge.prefabs;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.utils.Layers.Layer;

public class Text extends GameObject{
	static Font defaultFont = new Font("Arial",Font.PLAIN,12);
	
	public Font textFont = null;
	public Color textColor = new Color(255,255,255);
	public String text = "- - Sample Text - -"; 

	public Text(String name,String text,Transform transform, Layer initLayer,Font textFont) {
		super(name,transform, initLayer);
		this.text = text;
		if(textFont == null) {
			this.textFont = defaultFont;
		}
		
	}
	
	
	@Override
	public void draw(Graphics2D g) {
		g.setFont(textFont);
		g.setColor(textColor);
		
		g.drawString(text,this.transform.getX(),this.transform.getY());
		
		g.setColor(new Color(0,0,0));
		g.setFont(defaultFont);
	}
	
	

}
