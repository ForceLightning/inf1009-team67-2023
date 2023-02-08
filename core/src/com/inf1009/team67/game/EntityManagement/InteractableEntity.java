package com.inf1009.team67.game.EntityManagement;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.inf1009.team67.game.CollisionManagement.Accumulator;
import com.inf1009.team67.game.CollisionManagement.DynamicBody;

public class InteractableEntity extends EntityBase implements Interactable, DynamicBody<InteractableEntity> {
    private Accumulator accumulator;

    public InteractableEntity() {
        super();
        accumulator = new Accumulator(this);
    }
    public boolean isInteractingWith(Interactable other) {
        return this.getInteractionCircle().overlaps(other.getInteractionCircle());
    }

    public Circle getInteractionCircle() {
        return super.getInteractionCircle();
    }

    public void handleInteraction(Interactable other) {
        // TODO Auto-generated method stub
    }

    public void addReaction(Interactable other, Action reaction) {
        // TODO Auto-generated method stub
    }

    public Circle getHitBox() {
        return super.getHitBox();
    }

    public <V extends DynamicBody<?>> boolean isCollidingWith(V other) {
        return this.getHitBox().overlaps(other.getHitBox());
    }

    public <V extends DynamicBody<?>> void handleCollision(V other) {
        // TODO Auto-generated method stub
    }

    public Accumulator getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(Accumulator accumulator) {
        this.accumulator = accumulator;
    }

    public void resetAccumulator() {
        accumulator.reset();
    }

    public void applyFromAccumulator() {
        setPosition(getPosition().add(accumulator.getPositionUpdate()));
        setVelocity(getVelocity().add(accumulator.getVelocityUpdate()));
        setAcceleration(getAcceleration().add(accumulator.getAccelerationUpdate()));
        setRotation(getRotation() + accumulator.getAngleUpdate());
        setAngularVelocity(getAngularVelocity() + accumulator.getAngularVelocityUpdate());
        setAngularAcceleration(getAngularAcceleration() + accumulator.getAngularAccelerationUpdate());
    }

    public InteractableEntity getEntity() {
        return this;
    }
}
