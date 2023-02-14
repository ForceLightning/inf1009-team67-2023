package com.inf1009.team67.game.SceneManagement;

import com.badlogic.gdx.Screen;
import com.inf1009.team67.game.Main.MyGdxGame;

public class ScreenBase implements Screen {

    public MyGdxGame game;

    public ScreenBase(MyGdxGame game) {
        this.game = game;
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


