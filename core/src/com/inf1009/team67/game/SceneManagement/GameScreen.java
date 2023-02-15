package com.inf1009.team67.game.SceneManagement;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.game.CollisionManagement.CollisionHelper;
import com.inf1009.team67.game.Controllables.Player;
import com.inf1009.team67.game.Controllables.TestEntity;
import com.inf1009.team67.game.EntityManagement.EntityCollection;
import com.inf1009.team67.game.InputBehaviourManagement.BasicCombat.BasicCombatHelper;
import com.inf1009.team67.game.Main.MyGdxGame;
import com.inf1009.team67.game.Shape.Rectangle;
public class GameScreen extends ScreenBase {

    private SpriteBatch batch;
    private Sprite sprite;
    private Rectangle rectangle;
    private Music playingMusic;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private EntityCollection entityCollection;
    private Stage stage;
    private final CollisionHelper collisionHelper;
    private final BasicCombatHelper basicCombatHelper;


    public GameScreen(MyGdxGame myGdxGame){
        super(myGdxGame);

        batch = new SpriteBatch();
        rectangle = new Rectangle(150,60,90,50,5);
        this.sprite = new Sprite();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        stage = new Stage(new ScreenViewport(camera), batch);
        stage.setDebugAll(true);
        entityCollection = new EntityCollection(stage);
        collisionHelper = new CollisionHelper();
        basicCombatHelper = new BasicCombatHelper();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        TestEntity test = new TestEntity();
        TestEntity test2 = new TestEntity();
        TestEntity test3 = new TestEntity();
        Player player = new Player();
        test.setPosition(400, 240);
        test.setColor(0xFF0000FF);
        test2.setPosition(450, 240);
        test2.setColor(0x00FF00FF);
        test3.setPosition(450, 241);
        test3.setColor(0x0000FFFF);
        player.setPosition(100, 100);
        player.setColor(0xFFFFFFFF);
        entityCollection.insertEntity(test);
        entityCollection.insertEntity(test2);
        entityCollection.insertEntity(test3);
        entityCollection.insertEntity(player);
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
        collisionHelper.updateCollisions(entityCollection.getEntityCollection(), delta);
        basicCombatHelper.updateCombatStates(entityCollection.getEntityCollection());
        entityCollection.update(delta);
        // rectangle.render();
        // //rectangle.movement();
        // if(rectangle.getX() <= 0 + 40) rectangle.setX(0 + 40);
        // if(rectangle.getX() > 800 - 40) rectangle.setX(800 - 40);
        // if(rectangle.getY() <=0 + 40) rectangle.setY(0 + 40);
        // if(rectangle.getY() > 600 - 40) rectangle.setY(600 - 40);
        stage.draw();
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
        stage.unfocusAll();
    }

    @Override
    public void dispose() {
        batch.dispose();
        entityCollection.dispose();
        stage.dispose();
    }
}