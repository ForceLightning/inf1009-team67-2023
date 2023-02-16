package com.inf1009.team67.game.SceneManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inf1009.team67.game.Main.MyGdxGame;

public class TransitionScreen extends ScreenBase {
    private ScreenBase currentScreen;
    private ScreenBase nextScreen;
    private float alpha = 0f;
    private boolean fadeDirection = true;
    private ShapeRenderer shapeRenderer;

    public TransitionScreen(MyGdxGame game, ScreenBase currentScreen, ScreenBase nextScreen) {
        super(game);
        this.currentScreen = currentScreen;
        this.nextScreen = nextScreen;
        game.setScreen(nextScreen);
        game.setScreen(currentScreen);
        currentScreen.pause();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        if (fadeDirection) {
            currentScreen.render(delta);
        } else {
            nextScreen.render(delta);
        }
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(1, 1, 1, alpha);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

        if (alpha >= 1) {
            fadeDirection = false;
        } else if (alpha <= 0 && !fadeDirection) {
            currentScreen.hideAfterTransition();
            game.setScreen(nextScreen);
        }
        alpha += fadeDirection ? 0.01f : -0.01f;
    }
}
