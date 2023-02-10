package com.inf1009.team67.game.EntityManagement;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
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
        Vector2 center = new Vector2(this.getHitBox().x, this.getHitBox().y);
        Vector2 otherCenter = new Vector2(other.getHitBox().x, other.getHitBox().y);
        Vector2 normal = new Vector2(
            center.x - otherCenter.x,
            center.y - otherCenter.y
        ).nor();
        double distance = center.dst(otherCenter);
        double intersect = this.getHitBox().radius + other.getHitBox().radius - distance;
        float overlap = Math.max(0, (float) intersect);
        Vector2 delta_p = normal.cpy().scl(overlap / 16);
        Vector2 delta_v = normal.cpy().scl(overlap / 2);
        Vector2 delta_a = normal.cpy().scl(overlap / 4);
        this.getAccumulator().addToPosition(delta_p);
        this.getAccumulator().addToVelocity(delta_v);
        this.getAccumulator().addToAcceleration(delta_a);
        other.getAccumulator().addToPosition(delta_p.scl(-1));
        other.getAccumulator().addToVelocity(delta_v.scl(-1));
        other.getAccumulator().addToAcceleration(delta_a.scl(-1));
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

    public void applyFromAccumulator(float delta) {
        setPosition(getPosition().add(accumulator.getPositionUpdate()));
        setVelocity(getVelocity().add(accumulator.getVelocityUpdate().scl(delta)).scl(0.9f));
        setAcceleration(getAcceleration().add(accumulator.getAccelerationUpdate().scl(delta)));
        setRotation(getRotation() + accumulator.getAngleUpdate() * delta);
        setAngularVelocity(getAngularVelocity() + accumulator.getAngularVelocityUpdate() * delta);
        setAngularAcceleration(getAngularAcceleration() + accumulator.getAngularAccelerationUpdate() * delta);
    }

    public InteractableEntity getEntity() {
        return this;
    }
}
