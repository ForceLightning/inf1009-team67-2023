package com.inf1009.team67.game.scenes.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class DifficultyBar {
    public void drawDifficultyBar(ShapeRenderer shapes, int difficulty, int maxDifficulty) {
        Color oldColor = shapes.getColor();
        ShapeType oldShapeType = shapes.getCurrentType();
        shapes.set(ShapeType.Filled);
        // define the difficulty bar size
        Vector2 difficultyBarSize = new Vector2(300, 20);
        Vector2 difficultyBarPos = new Vector2(400 - difficultyBarSize.x / 2, 600 - difficultyBarSize.y * 2);
        shapes.setColor(Color.WHITE);
        shapes.rect(difficultyBarPos.x, difficultyBarPos.y, difficultyBarSize.x, difficultyBarSize.y);
        shapes.setColor(Color.RED);
        shapes.rect(difficultyBarPos.x, difficultyBarPos.y, (difficultyBarSize.x * difficulty / maxDifficulty), difficultyBarSize.y);
        shapes.set(oldShapeType);
        shapes.setColor(oldColor);
    }
}
