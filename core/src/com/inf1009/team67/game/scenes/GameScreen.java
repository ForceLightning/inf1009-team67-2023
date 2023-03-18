package com.inf1009.team67.game.scenes;


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
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
import com.inf1009.team67.engine.helpers.SuperHelper;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatHelper;
import com.inf1009.team67.engine.interactionmanagement.InteractionHelper;
import com.inf1009.team67.engine.scenemanagement.ScreenBase;
import com.inf1009.team67.game.Shape.Rectangle;
import com.inf1009.team67.game.controllables.Player;
import com.inf1009.team67.game.food.FoodFactory;
import com.inf1009.team67.game.food.HealthPack;
import com.inf1009.team67.game.food.HealthyFood;
import com.inf1009.team67.game.food.UnhealthyFood;
import com.inf1009.team67.game.controllables.HostileEntity;
import com.inf1009.team67.game.main.MyGdxGame;
import com.inf1009.team67.game.scenes.gui.GUI;
public class GameScreen extends ScreenBase {

    private SpriteBatch batch;
    private SpriteBatch uiBatch = new SpriteBatch();
    private Music playingMusic;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private EntityCollection entityCollection;
    private final CollisionHelper collisionHelper;
    private final BasicCombatHelper basicCombatHelper;
    private final InteractionHelper interactionHelper;
    private final SuperHelper superHelper = SuperHelper.getInstance();
    private ShapeRenderer uiShapeRenderer = new ShapeRenderer();
    private Player player;  // getHealth and getMaxHealth
    private Timer difficultyTimer = new Timer();
    private Timer spawnTimer = new Timer();
    private float spawnFrequency = 0.2f;
    private int difficulty = 8; // goes from 0 - 9
    private Label scoreLabel;
    private GUI gui;


    public int getDifficulty() {
        return difficulty + 1;
    }

    public int getMaxDifficulty() {
        return 10;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public GameScreen(MyGdxGame myGdxGame){
        super(myGdxGame);
        this.game.setScore(0);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        setStage(new Stage(new ScreenViewport(camera), batch));
        getStage().setDebugAll(true);
        entityCollection = new EntityCollection(getStage());
        collisionHelper = new CollisionHelper();
        basicCombatHelper = new BasicCombatHelper(myGdxGame, this);
        interactionHelper = new InteractionHelper();
        superHelper.setAllHelpers(collisionHelper, basicCombatHelper, interactionHelper);
        scheduleDifficulty();
        scheduleSpawner(spawnFrequency);
        player = new Player();
        player.setPosition(100, 100);
        player.setColor(0xFFFFFFFF);
        entityCollection.insertEntity(player);
        Skin skin = game.assetsManager.manager.get("skin/metal-ui.json");
        scoreLabel = new Label("Score: " + game.getScore(), skin, "font", "white");
        gui = new GUI(this.game, this);
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
        // basicCombatHelper.updateCombatStates(entityCollection.getEntityCollection());
        // collisionHelper.updateCollisions(entityCollection.getEntityCollection(), delta);
        // interactionHelper.updateInteractions(entityCollection.getEntityCollection());
        superHelper.updateHelpers(entityCollection.getEntityCollection(), delta);
        entityCollection.update(delta);
        getStage().draw();
        // TODO: UI Renderering here
        gui.drawGUI(uiShapeRenderer, uiBatch);
        // uiShapeRenderer.setProjectionMatrix(camera.combined);
        // uiShapeRenderer.begin(ShapeType.Line);
        // uiShapeRenderer.end();
        // uiBatch.begin();
        // scoreLabel.setText("Score: " + game.getScore());
        // scoreLabel.setPosition(400, 475, Align.top);
        // scoreLabel.draw(uiBatch, 1);
        // uiBatch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.switchScreen(ScreenEnum.MENU);
        }
        // TODO: If player is dead, switch to end screen
        if (player.getHealth() <= 0) {
            game.switchScreen(ScreenEnum.END);
        }
    }

    public void spawnEnemy(float x, float y, float speedIncrease) {
        HostileEntity newEnemy = new HostileEntity();
        newEnemy.setPosition(x, y);
        newEnemy.setColor(0xFF0000FF);
        newEnemy.setBaseMovementSpeed(newEnemy.getBaseMovementSpeed() + speedIncrease);
        entityCollection.insertEntity(newEnemy);
    }

    public void scheduleSpawner(float frequency) {
        float spawnInterval = 1 / frequency;
        spawnTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                // System.out.println("Spawning Enemies, Spawn interval: " + spawnInterval);
                float offsetX = (float) (Math.random() > 0.5 ? 1 : -1) * ((float) Math.random() * 800 + 400);
                float offsetY = (float) (Math.random() > 0.5 ? 1 : -1) * ((float) Math.random() * 480 + 240);
                offsetX += player.getCentreX();
                offsetY += player.getCentreY();
                spawnEnemy(offsetX, offsetY, difficulty * 8f);
            }
        }, 0, spawnInterval);
    }

    public void scheduleDifficulty() {
        difficultyTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (difficulty < 9) {
                    difficulty++;
                    spawnFrequency += 0.1f;
                    spawnTimer.clear();
                    scheduleSpawner(spawnFrequency);
                }
                Vector2 leftCorner = new Vector2(player.getX() - 1400, player.getY() - 1240);
                Vector2 rightCorner = new Vector2(player.getX() + 1400, player.getY() + 1240);
                spawnHealthPacks(difficulty, leftCorner, rightCorner, 2, 4);
            }
        }, 6, 60);
    }

    public void spawnHealthPacks(int difficulty, Vector2 leftCorner, Vector2 rightCorner, int rows, int columns) {
        // System.out.println("Spawning food");
        float xStep = (rightCorner.x - leftCorner.x) / (columns - 1);
        float yStep = (rightCorner.y - leftCorner.y) / (rows - 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Random rng = new Random((int) (System.currentTimeMillis() + i + j + difficulty));
                HealthPack newFood = FoodFactory.createFood(rng.nextInt(columns + rows));
                if (newFood == null) {
                    continue;
                } else if (newFood instanceof HealthyFood) {
                    newFood.setColor(0x00FF00FF);
                } else if (newFood instanceof UnhealthyFood) {
                    newFood.setColor(0xFF0000FF);
                }
                newFood.setHealth(newFood.getHealth() + (difficulty + 1) * 10);
                newFood.setPosition(leftCorner.x + xStep * j, leftCorner.y + yStep * i);
                newFood.setColor(0x00FF00FF);
                newFood.scaleFromCentre(difficulty / 5f + 1);
                entityCollection.insertEntity(newFood);
            }
        }
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

    public Player getPlayer() {
        return this.player;
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