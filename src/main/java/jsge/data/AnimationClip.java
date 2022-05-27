package jsge.data;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class AnimationClip {
		//vetores puros são usados para maior velocidade, uma vez que serao simples
		public String animationClipName = "NewAnimation";
		private BufferedImage[] spriteSheet = null;
		float animationLength = 0;
		int animationFrames = 0;
		boolean loops = false;
		
		public AnimationClip(){
			
		}
		
		//Feito assim por limitações no swing
		public void loadAnimationSpriteSheet(String animationClipName,String basePathName,float duration,int amountOfFrames,boolean isLooping) {
			BufferedImage[] newSpriteSheet =  new BufferedImage[amountOfFrames];
			this.animationClipName = animationClipName;
			this.animationLength = duration;
			animationFrames = amountOfFrames;
			loops = isLooping;
			for(int i = 0;i<amountOfFrames;i++) {
				try {
				String tempName = basePathName + String.format("_%d",i) + ".png";
				newSpriteSheet[i] =  ImageIO.read(new File(tempName));
				tempName = "";
				}
				catch(Exception e) {
					System.out.println("Error - An animation frame has failed to load - baseName: " + basePathName);
					return;
				}
			}
			this.spriteSheet = newSpriteSheet;
		}

		
		public BufferedImage getAnimationFrame(int index) {
			try {
			return this.spriteSheet[index];
			}
			catch(Exception e) {
				System.out.println("Error - Failed to retrive animation frame " + index + " from clip "+animationClipName);
				return null;
			}
			
		}
		
		public float getAnimationLength() {
			return this.animationLength;
		}
		public int getAnimationFrameCount() {
			return this.animationFrames;
		}
		
		public boolean getAnimationLooping() {
			return this.loops;
		}
}
