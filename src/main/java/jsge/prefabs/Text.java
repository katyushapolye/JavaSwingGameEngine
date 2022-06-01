package jsge.prefabs;

import java.awt.Graphics2D;
import java.io.File;
import java.awt.Color;
import java.awt.Font;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.utils.Layers.Layer;

public class Text extends GameObject {
	static Font defaultFont = new Font("Arial", Font.PLAIN, 12);

	private Font textFont = null;
	private Color textColor = new Color(255, 255, 255);
	private String text = "- - Sample Text - -";
	private float size = 12f;

	public static void setDefaultFont(String PathToFontFile) {
		try {
			Font newDefault = Font.createFont(Font.TRUETYPE_FONT, new File(PathToFontFile)).deriveFont(12);
			defaultFont = newDefault;
		} catch (Exception e) {
			System.out.println("Text: Warning - Failed to Load File " + PathToFontFile);
		}
	}

	public Text(String name, String text, Transform transform, Layer initLayer, Font textFont) {
		super(name, transform, initLayer);
		this.text = text;
		if (textFont == null) {
			this.textFont = defaultFont;
		}

	}

	public Text(String name, String text, Transform transform, Layer initLayer, Font textFont, float fontSize) {
		super(name, transform, initLayer);
		this.text = text;
		this.size = fontSize;
		if (textFont == null) {
			this.textFont = defaultFont;
		}

	}

	public void setSize(float size) {
		this.size = size;
	}

	public void setColor(Color color) {
		this.textColor = color;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFont(Font font) {
		this.textFont = font;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setFont(textFont.deriveFont(size));
		g.setColor(textColor);

		g.drawString(text, this.transform.getX(), this.transform.getY());

		g.setColor(new Color(0, 0, 0));
		g.setFont(defaultFont);
	}

}
