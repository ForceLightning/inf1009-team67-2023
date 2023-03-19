package com.inf1009.team67.game.scenes.gui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.inf1009.team67.game.controllables.Player;
import com.inf1009.team67.game.main.MyGdxGame;
import com.inf1009.team67.game.scenes.GameScreen;

public class GUI {
    private MyGdxGame game;
    private GameScreen gameScreen;
    private Player player;
    private DifficultyBar difficultyBar = new DifficultyBar();
    private HealthBar healthBar;
    private Score score;
    private Target target;
    private Camera camera;

    public GUI(MyGdxGame game, GameScreen gameScreen, Camera camera) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.camera = camera;
        this.player = gameScreen.getPlayer();
        Skin skin = game.assetsManager.manager.get("skin/metal-ui.json");
        this.healthBar = new HealthBar(skin);
        this.score = new Score(skin);
        this.target = new Target(camera);
    }

    public void drawGUI(ShapeRenderer shapes, SpriteBatch batch) {
        this.player = gameScreen.getPlayer();
        if (batch.isDrawing()) {
            batch.end();
        }
        shapes.begin(ShapeType.Filled);
        drawShapeUI(shapes);
        shapes.end();
        batch.begin();
        drawSpriteUI(batch);
        batch.end();
    }

    private void drawSpriteUI(SpriteBatch batch) {
        target.draw(batch, 1.0f);
        score.drawLabel(batch, game.getScore());
        healthBar.drawLabel(batch, player.getHealth(), player.getMaxHealth(), player.getMaxHealthLimit());
    }

    private void drawShapeUI(ShapeRenderer shapes) {
        difficultyBar.drawDifficultyBar(shapes, gameScreen.getDifficulty(), gameScreen.getMaxDifficulty());
        healthBar.drawShapes(shapes, player.getHealth(), player.getMaxHealth(), player.getMaxHealthLimit());
    }

    public void updateTarget() {
        target.updateTarget(player, camera);
    }

}
