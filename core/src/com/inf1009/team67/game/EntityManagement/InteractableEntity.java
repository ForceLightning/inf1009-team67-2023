package com.inf1009.team67.game.EntityManagement;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Action;
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

    public boolean isCollidingWith(DynamicBody<InteractableEntity> other) {
        return this.getHitBox().overlaps(other.getHitBox());
    }

    public void handleCollision(DynamicBody<InteractableEntity> other) {
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
        setPosition(getPosition().add(accumulator.deltaPosition));
        setVelocity(getVelocity().add(accumulator.deltaVelocity));
        setAcceleration(getAcceleration().add(accumulator.deltaAcceleration));
        setRotation(getRotation() + accumulator.deltaAngle);
        setAngularVelocity(getAngularVelocity() + accumulator.deltaAngularVelocity);
        setAngularAcceleration(getAngularAcceleration() + accumulator.deltaAngularAcceleration);
    }

    public InteractableEntity getEntity() {
        return this;
    }
}
