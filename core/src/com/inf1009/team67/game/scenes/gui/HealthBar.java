package com.inf1009.team67.game.scenes.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class HealthBar {
    private Vector2 healthBarSize = new Vector2(300, 20);
    private Vector2 healthBarPos = new Vector2(20, 20);
    private Label healthLabel;

    public HealthBar(Skin skin) {
        healthLabel = new Label("1000/1000", skin, "font", "white");
        healthLabel.scaleBy(10f);
        healthLabel.setPosition((healthBarPos.x + healthBarSize.x) / 2, (healthBarPos.y + healthBarSize.y) / 2, Align.bottom);
    }

    public void drawShapes(ShapeRenderer shapes, float health, float maxHealth, float baseLineMaxHealth) {
        Color oldColor = shapes.getColor();
        ShapeType oldShapeType = shapes.getCurrentType();
        shapes.set(ShapeType.Filled);
        float currentHealthRatio = health > baseLineMaxHealth ? 1f : health / baseLineMaxHealth;
        float maxHealthRatio = maxHealth > baseLineMaxHealth ? 1f : maxHealth / baseLineMaxHealth;
        float bonusHealthRatio = maxHealthRatio > 1f ? maxHealthRatio - 1f : 0f;
        float maxHealthPenaltyRatio = maxHealthRatio < 1f ? 1f - maxHealthRatio : 0f;
        shapes.setColor(Color.BLACK);
        shapes.rect(healthBarPos.x - 2f, healthBarPos.y - 2f, healthBarSize.x + 2f, healthBarPos.y + 2f);
        shapes.setColor(Color.GREEN);
        shapes.rect(healthBarPos.x, healthBarPos.y, healthBarSize.x * currentHealthRatio, healthBarSize.y);
        if (bonusHealthRatio > 0f) {
            shapes.setColor(Color.BLUE);
            shapes.rect(healthBarPos.x, healthBarPos.y, healthBarSize.x * bonusHealthRatio, healthBarSize.y);
        } else if (maxHealthPenaltyRatio > 0f) {
            shapes.setColor(Color.RED);
            shapes.rect(
                healthBarPos.x + healthBarSize.x - healthBarSize.x * maxHealthPenaltyRatio,
                healthBarPos.y,
                healthBarSize.x * maxHealthPenaltyRatio,
                healthBarSize.y
            );
        }
        shapes.setColor(oldColor);
        shapes.set(oldShapeType);
    }

    public void drawLabel(SpriteBatch batch, float health, float maxHealth, float baseLineMaxHealth) {
        String healthText = String.format("%d/%d HP", (int) health, (int) maxHealth);
        healthLabel.setText(healthText);
        // create a black dropshadow
        healthLabel.setColor(Color.BLACK);
        healthLabel.setPosition(healthLabel.getX() - 1, healthLabel.getY() - 1);
        healthLabel.draw(batch, 1f);
        healthLabel.setColor(Color.WHITE);
        healthLabel.setPosition(healthLabel.getX() + 1, healthLabel.getY() + 1);
        healthLabel.draw(batch, 1f);
    }
}