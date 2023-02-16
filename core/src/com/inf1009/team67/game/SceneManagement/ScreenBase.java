package com.inf1009.team67.game.SceneManagement;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.game.Main.MyGdxGame;

public class ScreenBase implements Screen {

    public MyGdxGame game;
    private Stage stage;
    private boolean isPaused = false;

    public ScreenBase(MyGdxGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(delta, 1/60f));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        setPaused(true);
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

    public Stage getStage() {
        return stage;
    }

    public void hideAfterTransition() {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void togglePause() {
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}



