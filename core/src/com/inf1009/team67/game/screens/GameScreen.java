package com.inf1009.team67.game.screens;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.inf1009.team67.game.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.game.AssetsManager;
import com.inf1009.team67.game.Renderer;
import com.inf1009.team67.game.CollisionManagement.CollisionHelper;
import com.inf1009.team67.game.EntityManagement.EntityBase;
import com.inf1009.team67.game.Shape.Rectangle;
public class GameScreen extends ScreenManager {
    public AssetsManager assetsManager = new AssetsManager();

    private MyGdxGame parent;
    private SpriteBatch batch;
    private Sprite sprite;
    private Rectangle rectangle;
    private Music playingMusic;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private TreeMap<Integer, ArrayList<EntityBase>> entityCollection;
    private Renderer renderer;
    private Stage stage;
    private final CollisionHelper collisionHelper;


    public GameScreen(Game myGdxGame){
        super(myGdxGame);

        batch = new SpriteBatch();
        rectangle = new Rectangle(150,60,90,50,5);
        this.sprite = new Sprite();
        renderer = new Renderer(batch);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport(camera), batch);
        entityCollection = new TreeMap<Integer, ArrayList<EntityBase>>();
        // TODO(Entity): create an entity manager class
        // TODO(Entity): link entity manager class and stage
        collisionHelper = new CollisionHelper();
    }

    @Override
    public void show() {
        //assetsManager.queueAddMusic();
        //assetsManager.manager.finishLoading();
        //playingMusic = assetsManager.manager.get("music/loz_title.mp3");
       // playingMusic.play();

        //assetsManager.queueAddBackground();
        //batch = new SpriteBatch();
        //Get the background texture from the asset manager
        //backgroundTexture = assetsManager.manager.get("background.jpg", Texture.class);

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0.2f, 0, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        collisionHelper.updateCollisions(entityCollection);

        batch.begin();
        // rectangle.render();
        // //rectangle.movement();
        // if(rectangle.getX() <= 0 + 40) rectangle.setX(0 + 40);
        // if(rectangle.getX() > 800 - 40) rectangle.setX(800 - 40);
        // if(rectangle.getY() <=0 + 40) rectangle.setY(0 + 40);
        // if(rectangle.getY() > 600 - 40) rectangle.setY(600 - 40);
        for (Integer z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(z)) {
                renderer.render(entity);
            }
        }
        batch.end();



        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            // your actions
            game.setScreen(new MenuScreen(game));
        }

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


