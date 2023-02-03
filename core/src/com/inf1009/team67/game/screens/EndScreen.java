package com.inf1009.team67.game.screens;

import com.inf1009.team67.game.game.MyGdxGame;
import com.badlogic.gdx.Game;

public class EndScreen extends ScreenManager {
    private MyGdxGame parent;
    private MenuScreen menuScreen;
    private GameScreen mainScreen;
    private SettingsScreen preferencesScreen;
    private EndScreen endScreen;

    private SplashScreen splashScreen;

    public final static int MENU = 0;
    public final static int PREFERENCES=1;

    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;

    public  final static int SPLASHSCREEN = 4;

    public EndScreen(Game myGdxGame) {
        super(myGdxGame);
        //parent = myGdxGame;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

