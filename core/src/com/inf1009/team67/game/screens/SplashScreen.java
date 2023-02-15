package com.inf1009.team67.game.screens;


import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.inf1009.team67.game.game.MyGdxGame;


public class SplashScreen extends ScreenManager {

    private MyGdxGame myGdxGame;
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

    private Sprite sprite;

    private SpriteBatch batch;
    private BitmapFont font;

    private MyGdxGame parent; //store ochestrator


    //constructor with Box2d

    public SplashScreen(Game myGdxGame) {

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
                    game.setScreen(new MenuScreen(game));
                }
                return true;
            }

        });



    }

    @Override
    public void render(float delta) {


        game.setScreen(new SplashScreen(game));
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
