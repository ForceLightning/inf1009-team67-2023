package com.inf1009.team67.game.food;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UnhealthyFood extends HealthPack {
    public UnhealthyFood() {
        this(50, 0.5f, 0.5f);
    }

    public UnhealthyFood(int health, float movementSpeedAilment, float maxHealthAilment) {
        super(health, movementSpeedAilment, maxHealthAilment);
        this.setTexture("textures/circle.png");
        this.setSize(10, 10);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        Color oldColor = shapes.getColor();
        shapes.setColor(Color.RED);
        super.drawDebug(shapes);
        shapes.setColor(oldColor);
    }
}
