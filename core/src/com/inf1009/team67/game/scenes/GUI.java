package com.inf1009.team67.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GUI {

    public GUI(int difficulty, int currentHealth, int maxHealth){
        difficulty = player.getDifficulty();
        currentHealth = player.getHealth;
        maxHealth= player.getMaxHealth;
    }

    public void drawDifficultyBar(ShapeRenderer shapes) {
        Color oldColor = shapes.getColor();
        if (shapes.isDrawing() && shapes.getCurrentType() != ShapeRenderer.ShapeType.Filled) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }

        shapes.setColor(Color.RED);
        // define the difficulty bar size
        Vector2 difficultyBarSize = new Vector2(300, 20);

        // fix position of difficulty bar to top centre of screen
        Vector2 difficultyBarPos = new Vector2();


        // draw difficulty bar
        shapes.setColor(Color.WHITE);
        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.rect(difficultyBarPos.x, difficultyBarPos.y, difficultyBarSize.x, difficultyBarSize.y);
        shapes.setColor(Color.RED);
        shapes.rect(difficultyBarPos.x, difficultyBarPos.y, (difficultyBarSize.x / 10) * (this.getDifficulty() + 1),
                difficultyBarSize.y);
        shapes.set(ShapeRenderer.ShapeType.Line);
        shapes.setColor(oldColor);
    }

    public void drawHealthBar(ShapeRenderer shapes) {
        Color oldColor = shapes.getColor();
        if (shapes.isDrawing() && shapes.getCurrentType() != ShapeRenderer.ShapeType.Filled) {
            shapes.set(ShapeRenderer.ShapeType.Filled);
        }

        shapes.setColor(Color.RED);
        // define the hp bar size
        Vector2 healthBarSize = new Vector2(300, 20);

        // fix position of difficulty bar to top centre of screen
        Vector2 healthBarPos = new Vector2();


        // draw difficulty bar
        shapes.setColor(Color.WHITE);
        shapes.set(ShapeRenderer.ShapeType.Filled);
        shapes.rect(healthBarPos.x, healthBarPos.y, healthBarSize.x, healthBarSize.y);
        shapes.setColor(Color.RED);
        shapes.rect(healthBarPos.x, healthBarPos.y, healthBarSize * (this.getHealth() / this.getMaxHealth()), hpBarSize.y);
        shapes.set(ShapeRenderer.ShapeType.Line);
        shapes.setColor(oldColor);
    }

}
