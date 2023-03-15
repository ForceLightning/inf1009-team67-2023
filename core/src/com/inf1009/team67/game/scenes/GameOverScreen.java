package com.inf1009.team67.game.scenes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.engine.util.AssetsManager;
import com.inf1009.team67.game.main.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameOverScreen extends ScreenBase{

    private AssetsManager assetsManager = new AssetsManager();
    private Sprite sprite;
    private SpriteBatch batch;
    private BitmapFont font;

    public GameOverScreen(MyGdxGame game) {
        super(game);
        font = new BitmapFont();
        //spriteBatch = new SpriteBatch();
        //gameOverTexture = new TextureRegion(AssetManager.get("gameover.png", Texture.class));
        //parent = myGdxGame;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());

        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keyCode) {
                if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
                    game.setScreen(ScreenEnum.MENU);
                }
                return true;
            }

        });
        assetsManager.queueAddGameOverScreen();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("gameover.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


    }

    @Override
    public void render(float delta) {

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("gameover.jpg")));
        sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.begin(); // Call the batch processing (Has to be called first for the sprite to be on top of UI)
        sprite.draw(batch);
        font.draw(batch, "Press any key to start", Gdx.graphics.getWidth() * .43f, Gdx.graphics.getHeight() * .50f);
        batch.end();

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
        //spriteBatch.dispose();
        //gameOverTexture.getTexture().dispose();

    }
}
