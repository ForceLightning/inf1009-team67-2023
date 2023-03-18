package com.inf1009.team67.game.scenes.gui;

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

    public GUI(MyGdxGame game, GameScreen gameScreen) {
        // difficulty = player.getDifficulty();
        // currentHealth = player.getHealth;
        // maxHealth= player.getMaxHealth;
        this.game = game;
        this.gameScreen = gameScreen;
        this.player = gameScreen.getPlayer();
        Skin skin = game.assetsManager.manager.get("skin/metal-ui.json");
        this.healthBar = new HealthBar(skin);
        this.score = new Score(skin);
    }

    public void drawGUI(ShapeRenderer shapes, SpriteBatch batch) {
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
        score.drawLabel(batch, game.getScore());
        healthBar.drawLabel(batch, player.getHealth(), player.getMaxHealth(), player.getMaxHealthLimit());
    }

    private void drawShapeUI(ShapeRenderer shapes) {
        difficultyBar.drawDifficultyBar(shapes, gameScreen.getDifficulty(), gameScreen.getMaxDifficulty());
        healthBar.drawShapes(shapes, player.getHealth(), player.getMaxHealth(), player.getMaxHealthLimit());
    }

}
