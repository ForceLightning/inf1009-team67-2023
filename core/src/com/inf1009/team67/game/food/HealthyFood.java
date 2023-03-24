package com.inf1009.team67.game.food;

import com.badlogic.gdx.graphics.Color;
import com.inf1009.team67.engine.interactionmanagement.Interactable;
import com.inf1009.team67.game.controllables.Player;

public class HealthyFood extends HealthPack {
    public HealthyFood() {
        this(10, 1, 1);
        this.setColor(Color.WHITE);
    }

    public HealthyFood(int health, float movementSpeedAilment, float maxHealthAilment) {
        super(health, movementSpeedAilment, maxHealthAilment);
        this.setTexture("textures/circle.png");
        this.setSize(10, 10);
        String[] textures = {"textures/healthyFood/apple.png", "textures/healthyFood/broccoli.png", "textures/healthyFood/carrot.png"};
        this.setTexture(textures[(int) (Math.random() * textures.length)]);
        this.setColor(Color.WHITE);
    }

    @Override
    public void handleInteraction(Interactable other) {
        if (other instanceof Player && isInteractable() && other.isInteractable()) {
            Player player = (Player) other;
            player.setMaxHealthModifier(player.getMaxHealthModifier() < 2 ? Math.min(1.1f * getMaxHealthAilment(), 2f) : getMaxHealthAilment());
            player.modifyHealth(player.getHealth() + getHealth());
            player.setMovementSpeedModifier(player.getMovementSpeedModifier() < 1 ? Math.min(1.1f * getMovementSpeedAilment(), 1f) : getMovementSpeedAilment());
            setInteractable(false);
            setVisible(isInteractable());
        }
    }

    @Override
    public void init(int health, float movementSpeedAilment, float maxHealthAilment) {
        super.init(health, movementSpeedAilment, maxHealthAilment);
        String[] textures = {"textures/healthyFood/apple.png", "textures/healthyFood/broccoli.png", "textures/healthyFood/carrot.png"};
        this.setTexture(textures[(int) (Math.random() * textures.length)]);
    }

    @Override
    public void init() {
        super.init();
        String[] textures = {"textures/healthyFood/apple.png", "textures/healthyFood/broccoli.png", "textures/healthyFood/carrot.png"};
        this.setTexture(textures[(int) (Math.random() * textures.length)]);
    }
}
