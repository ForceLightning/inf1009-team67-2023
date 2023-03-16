package com.inf1009.team67.game.food;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.interactionmanagement.Interactable;
import com.inf1009.team67.game.controllables.Player;

public abstract class HealthPack extends EntityBase implements Interactable {
    private int health;
    private float movementSpeedAilment;
    private float maxHealthAilment;
    private Circle interactionCircle;
    private boolean isInteractable = true;

    public HealthPack() {
        super();
    }

    public HealthPack(int health, float movementSpeedAilment, float maxHealthAilment) {
        super();
        this.health = health;
        this.movementSpeedAilment = movementSpeedAilment;
        this.maxHealthAilment = maxHealthAilment;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getMovementSpeedAilment() {
        return movementSpeedAilment;
    }

    public void setMovementSpeedAilment(float movementSpeedAilment) {
        this.movementSpeedAilment = movementSpeedAilment;
    }

    public float getMaxHealthAilment() {
        return maxHealthAilment;
    }

    public void setMaxHealthAilment(float maxHealthAilment) {
        this.maxHealthAilment = maxHealthAilment;
    }

    public void handleInteraction(Interactable other) {
        if (other instanceof Player && isInteractable && other.isInteractable()) {
            Player player = (Player) other;
            player.modifyHealth(player.getHealth() + health);
            player.setMovementSpeedModifier(movementSpeedAilment);
            player.setMaxHealth(player.getMaxHealth() * maxHealthAilment);
            isInteractable = false;
            setVisible(isInteractable);
        }
    }

    public boolean isInteractingWith(Interactable other) {
        if (this.interactionCircle.overlaps(other.getInteractionCircle()) && isInteractable) {
            return true;
        }
        return false;
    }

    public Circle getInteractionCircle() {
        return interactionCircle;
    }

    public void setInteractionCircle(Circle circle) {
        this.interactionCircle = circle;
    }

    public void setInteractionCircle(float x, float y, float radius) {
        this.interactionCircle = new Circle(x, y, radius);
    }

    public void setInteractionCircle(float x, float y) {
        this.interactionCircle = new Circle(x, y, this.interactionCircle.radius);
    }

    public void setInteractionCircle(float radius) {
        this.interactionCircle = new Circle(this.getCentreX(), this.getCentreY(), radius);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setInteractionCircle(getCentreX(), getCentreY());
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    @Override
    public void addReaction(Interactable other, Action reaction) {
        return;
    }
}
