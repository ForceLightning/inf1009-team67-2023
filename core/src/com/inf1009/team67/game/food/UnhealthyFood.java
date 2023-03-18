package com.inf1009.team67.game.food;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class UnhealthyFood extends HealthPack {
    public UnhealthyFood() {
        this(50, 0.5f, 0.5f);
    }

    public UnhealthyFood(int health, float movementSpeedAilment, float maxHealthAilment) {
        super(health, movementSpeedAilment, maxHealthAilment);
        this.setTexture("textures/circle.png");
        this.setSize(10, 10);
        this.setColor(Color.RED);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        // Color oldColor = shapes.getColor();
        // shapes.setColor(Color.RED);
        // super.drawDebug(shapes);
        // shapes.setColor(oldColor);
        Color oldColor = shapes.getColor();
        ShapeType oldType = shapes.getCurrentType();
        shapes.setColor(Color.RED.r, Color.RED.g, Color.RED.b, 0.5f);
        shapes.set(ShapeType.Filled);
        shapes.circle(this.getCentreX(), this.getCentreY(), this.getWidth() / 2);
        shapes.setColor(oldColor);
        shapes.set(oldType);
    }
}
