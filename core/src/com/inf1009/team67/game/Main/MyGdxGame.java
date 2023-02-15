package com.inf1009.team67.game.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.team67.game.SceneManagement.ScreenEnum;
import com.inf1009.team67.game.SceneManagement.ScreenManager;
import com.inf1009.team67.game.Util.AssetsManager;


public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	private ScreenManager screenManager;
	public AssetsManager assetsManager;
	public MyGdxGame() {
		super();
		screenManager = new ScreenManager(this);
		assetsManager = new AssetsManager();
        assetsManager.queueAddMusic();
        assetsManager.queueAddSkin();
	}

	@Override
	public void create() {
		screenManager.setScreen(ScreenEnum.SPLASHSCREEN);
	}
	public void setScreen(ScreenEnum screen) {
		screenManager.setScreen(screen);
	}

}
