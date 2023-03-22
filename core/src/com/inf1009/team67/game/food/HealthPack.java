package com.inf1009.team67.game.food;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.helpers.HandleEnum;
import com.inf1009.team67.engine.interactionmanagement.Interactable;
import com.inf1009.team67.game.controllables.Player;

public abstract class HealthPack extends EntityBase implements Interactable, Poolable {
    private int health;
    private float movementSpeedAilment;
    private float maxHealthAilment;
    private Circle interactionCircle;
    private boolean isInteractable = false;

    public HealthPack() {
        super();
        this.addRequiredHandle(HandleEnum.INTERACTION);
    }

    public HealthPack(int health, float movementSpeedAilment, float maxHealthAilment) {
        super();
        this.health = health;
        this.movementSpeedAilment = movementSpeedAilment;
        this.maxHealthAilment = maxHealthAilment;
        this.setSize(10, 10);
        this.interactionCircle = new Circle(this.getX(), this.getY(), this.getWidth() / 2);
        this.setColor(0xffffffff);
        this.addRequiredHandle(HandleEnum.INTERACTION);
    }
    
    @Override
    public void scaleFromCentre(float scale) {
        super.scaleFromCentre(scale);
        this.interactionCircle.setRadius(this.interactionCircle.radius * scale);
        this.interactionCircle.setPosition(this.getCentreX(), this.getCentreY());
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
            player.setMovementSpeedModifier(player.getMovementSpeedModifier() <= 0.25 ? 0.25f : movementSpeedAilment);
            player.setMaxHealthModifier(player.getMaxHealthModifier() <= 0.25f ? 0.25f : Math.max(maxHealthAilment * player.getMaxHealthModifier(), 0.25f));
            isInteractable = false;
            setVisible(isInteractable);
            FoodFactory.freeFood(this);
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

    @Override
    public void reset() {
        this.setInteractable(false);
        this.setVisible(false);
    }

    public void init(int health, float movementSpeedAilment, float maxHealthAilment) {
        this.setHealth(health);
        this.setMovementSpeedAilment(movementSpeedAilment);
        this.setMaxHealthAilment(maxHealthAilment);
        this.setInteractable(true);
        this.setVisible(true);
    }

    public void init() {
        this.setInteractable(true);
        this.setVisible(true);
    }
}
