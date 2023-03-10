package com.inf1009.team67.game.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.team67.engine.util.AssetsManager;
import com.inf1009.team67.engine.scenemanagement.ScreenManager;
import com.inf1009.team67.game.scenes.EndScreen;
import com.inf1009.team67.game.scenes.GameScreen;
import com.inf1009.team67.game.scenes.InstructionsScreen;
import com.inf1009.team67.game.scenes.LeaderboardScreen;
import com.inf1009.team67.game.scenes.MenuScreen;
import com.inf1009.team67.game.scenes.ScreenEnum;
import com.inf1009.team67.game.scenes.SettingsScreen;
import com.inf1009.team67.game.scenes.SplashScreen;


public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	private ScreenManager screenManager;
	public AssetsManager assetsManager;
	public MyGdxGame() {
		super();
		screenManager = new ScreenManager(this);
		initialiseScreens();
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
	public void switchScreen(ScreenEnum screen) {
		screenManager.switchScreen(screen);
	}
	public void initialiseScreens() {
		screenManager.addScreen(ScreenEnum.SPLASHSCREEN, SplashScreen.class);
		screenManager.addScreen(ScreenEnum.MENU, MenuScreen.class);
		screenManager.addScreen(ScreenEnum.SETTINGS, SettingsScreen.class);
		screenManager.addScreen(ScreenEnum.GAME, GameScreen.class);
		screenManager.addScreen(ScreenEnum.END, EndScreen.class);
		screenManager.addScreen(ScreenEnum.INSTRUCTIONS, InstructionsScreen.class);
		screenManager.addScreen(ScreenEnum.LEADERBOARD, LeaderboardScreen.class);
	}

}
