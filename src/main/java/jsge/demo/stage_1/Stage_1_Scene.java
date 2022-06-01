package jsge.demo.stage_1;

import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.utils.Layers.Layer;

public class Stage_1_Scene extends Scene {
	Player player;
	GameObject BG;

	public Stage_1_Scene() {
		super("stage_1");
	}

	@Override
	public void sceneBootStrap() {
		player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", 320, 240, 0);
		BG = new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png", new Transform(320, 240), Layer.UI);
		Enemy debug = new Enemy("debug1", "src/main/resources/Assets/Scratchs/EarthSpirit_SpriteSheet.png",
				new Transform(320, 180), Layer.GAMEOBJECT, 40);
	}

}
