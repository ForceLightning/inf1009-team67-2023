package com.inf1009.team67.game.scenes;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inf1009.team67.engine.collisionmanagement.CollisionHelper;
import com.inf1009.team67.engine.controllables.ControllableCharacter;
import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.entitymanagement.EntityCollection;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatHelper;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.game.Shape.Rectangle;
import com.inf1009.team67.game.controllables.Player;
import com.inf1009.team67.game.controllables.TestEntity;
import com.inf1009.team67.game.main.MyGdxGame;
public class GameScreen extends ScreenBase {

    private SpriteBatch batch;
    private Sprite sprite;
    private Rectangle rectangle;
    private Music playingMusic;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private EntityCollection entityCollection;
    private final CollisionHelper collisionHelper;
    private final BasicCombatHelper basicCombatHelper;
    private ShapeRenderer uiShapeRenderer = new ShapeRenderer();
    private Player player;
    private Timer difficultyTimer = new Timer();
    private Timer spawnTimer = new Timer();
    private float spawnFrequency = 0.2f;
    private int difficulty = 0; // goes from 0 - 9
    private Label scoreLabel;


    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public GameScreen(MyGdxGame myGdxGame){
        super(myGdxGame);
        this.game.setScore(0);

        batch = new SpriteBatch();
        rectangle = new Rectangle(150,60,90,50,5);
        this.sprite = new Sprite();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        setStage(new Stage(new ScreenViewport(camera), batch));
        getStage().setDebugAll(true);
        entityCollection = new EntityCollection(getStage());
        collisionHelper = new CollisionHelper();
        basicCombatHelper = new BasicCombatHelper(myGdxGame, this);
        // TODO: Spawn enemy entities outside of screen bounds
        scheduleSpawner(spawnFrequency);
        // TODO: Setup a timer for difficulty scaling
        difficultyTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (difficulty < 9) {
                    difficulty++;
                    spawnFrequency += 0.1f;
                    float spawnInterval = 1 / spawnFrequency;
                    spawnTimer.clear();
                    scheduleSpawner(spawnInterval);
                }
            }
        }, 60, 60);
        TestEntity test = new TestEntity();
        TestEntity test2 = new TestEntity();
        TestEntity test3 = new TestEntity();
        player = new Player();
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
        Skin skin = game.assetsManager.manager.get("skin/metal-ui.json");
        scoreLabel = new Label("Score: " + game.getScore(), skin, "font", "white");
        scoreLabel.setPosition(player.getX() + 400, player.getY() + 240, Align.top);
        getStage().addActor(scoreLabel);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(getStage());
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
        super.render(delta);
        ScreenUtils.clear(0, 0.2f, 0, 0);
        camera.update();
        camera.position.set(player.getCentreX(), player.getCentreY(), 0);
        ControllableCharacter target = getCursorTarget();
        if (target != null) {
            player.setTarget(target);
        }
        batch.setProjectionMatrix(camera.combined);
        basicCombatHelper.updateCombatStates(entityCollection.getEntityCollection());
        collisionHelper.updateCollisions(entityCollection.getEntityCollection(), delta);
        entityCollection.update(delta);
        getStage().draw();
        uiShapeRenderer.setProjectionMatrix(camera.combined);
        uiShapeRenderer.begin(ShapeType.Line);
        // TODO: UI Renderering here
        scoreLabel.setPosition(player.getCentreX(), player.getCentreY() + 235, Align.top);
        scoreLabel.setText("Score: " + game.getScore());
        uiShapeRenderer.end();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            // your actions
            // game.setScreen(new MenuScreen(game));
            // game.setScreen(ScreenEnum.MENU);
            game.switchScreen(ScreenEnum.MENU);
        }

    }

    public void scheduleSpawner(float frequency) {
        float spawnInterval = 1 / frequency;
        spawnTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                TestEntity newEnemy = new TestEntity();
                float offsetX = (float) (Math.random() > 0.5 ? 1 : -1) * ((float) Math.random() * 800 + 400);
                float offsetY = (float) (Math.random() > 0.5 ? 1 : -1) * ((float) Math.random() * 480 + 240);
                newEnemy.setPosition(player.getX() + offsetX, player.getY() + offsetY);
                newEnemy.setColor(0xFF0000FF);
                entityCollection.insertEntity(newEnemy);
            }
        }, spawnInterval, spawnInterval);
    }

    public ControllableCharacter getCursorTarget() {
        ControllableCharacter target = null;
        for (EntityBase entity: entityCollection.getEntityCollection().get(player.getZ())) {
            if (entity instanceof ControllableCharacter && entity != player) {
                ControllableCharacter controllableCharacter = (ControllableCharacter) entity;
                Vector3 cursorLocation = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(cursorLocation);
                if (controllableCharacter.getHitBox().contains(cursorLocation.x, cursorLocation.y)) {
                    target = (ControllableCharacter) entity;
                }
            }
        }
        return target;
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
        // getStage().unfocusAll();
        // entityCollection.dispose();
        // getStage().dispose();
    }

    public void hideAfterTransition() {
        getStage().unfocusAll();
        entityCollection.dispose();
        getStage().dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        entityCollection.dispose();
        getStage().dispose();
    }
}