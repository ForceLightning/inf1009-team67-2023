package com.inf1009.team67.game.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.team67.game.AssetsManager;
import com.inf1009.team67.game.screens.*;


public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;

	private SplashScreen splashScreen;
	private SettingsScreen preferencesScreen;
	private GameScreen mainScreen;
	private MenuScreen menuScreen;
	private EndScreen endScreen;

	public Game game;

	public AssetsManager assetsManager = new AssetsManager();

	public final static int MENU = 0;
	public final static int PREFERENCES=1;

	public final static int APPLICATION = 2;
	public final static int ENDGAME = 3;

	public final static int SPLASHSCREEN = 4;



	@Override
	public void create() {

		splashScreen = new SplashScreen(this);
		setScreen(splashScreen);

	}


}
