package com.inf1009.team67.game.SceneManagement;


import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inf1009.team67.game.Main.MyGdxGame;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class SplashScreen extends ScreenBase {

    private Sprite sprite;

    private SpriteBatch batch;
    private BitmapFont font;



    //constructor with Box2d

    public SplashScreen(MyGdxGame myGdxGame) {

        super(myGdxGame);
        //parent = myGdxGame;

        font = new BitmapFont();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown(int keyCode) {
                if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
                    game.setScreen(ScreenEnum.MENU);
                }
                return true;
            }

        });
    }

    @Override
    public void render(float delta) {


        batch = new SpriteBatch();
        sprite = new Sprite(new Texture(Gdx.files.internal("background.jpg")));
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

    }
}
