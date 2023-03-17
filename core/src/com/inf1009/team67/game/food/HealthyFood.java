package com.inf1009.team67.game.food;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.inf1009.team67.engine.interactionmanagement.Interactable;
import com.inf1009.team67.game.controllables.Player;

public class HealthyFood extends HealthPack {
    public HealthyFood() {
        this(10, 1, 1);
    }

    public HealthyFood(int health, float movementSpeedAilment, float maxHealthAilment) {
        super(health, movementSpeedAilment, maxHealthAilment);
        this.setTexture("textures/circle.png");
        this.setSize(10, 10);
        this.setColor(Color.GREEN);
    }

    @Override
    public void handleInteraction(Interactable other) {
        if (other instanceof Player && isInteractable() && other.isInteractable()) {
            Player player = (Player) other;
            player.setMaxHealthModifier(player.getMaxHealthModifier() < 1 ? 1.1f : getMaxHealthAilment());
            player.modifyHealth(player.getHealth() + getHealth());
            player.setMovementSpeedModifier(player.getMovementSpeedModifier() < 1 ? 1.1f : getMovementSpeedAilment());
            setInteractable(false);
            setVisible(isInteractable());
        }
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        Color oldColor = shapes.getColor();
        ShapeType oldType = shapes.getCurrentType();
        shapes.setColor(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, 0.5f);
        shapes.set(ShapeType.Filled);
        shapes.circle(this.getCentreX(), this.getCentreY(), this.getWidth() / 2);
        shapes.setColor(oldColor);
        shapes.set(oldType);
    }
}
