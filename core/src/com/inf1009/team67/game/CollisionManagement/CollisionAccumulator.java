package com.inf1009.team67.game.CollisionManagement;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.game.EntityManagement.EntityBase;

public class CollisionAccumulator {
    protected Vector2 deltaPosition;
    protected Vector2 deltaAbsolutePosition;
    protected Vector2 deltaVelocity;
    protected Vector2 deltaAcceleration;
    protected float deltaAngle;
    protected float deltaAngularVelocity;
    protected float deltaAngularAcceleration;
    protected EntityBase entity;

    public CollisionAccumulator(EntityBase entity) {
        this.entity = entity;
        getFromEntity();
    }

    public CollisionAccumulator() {
        deltaPosition = new Vector2();
        deltaAbsolutePosition = new Vector2();
        deltaVelocity = new Vector2();
        deltaAcceleration = new Vector2();
        deltaAngle = 0.0f;
        deltaAngularVelocity = 0.0f;
        deltaAngularAcceleration = 0.0f;
        entity = null;
    }

    public void setEntity(EntityBase entity) {
        this.entity = entity;
    }

    public void getFromEntity() {
        deltaPosition = entity.getVelocity().cpy();
        deltaVelocity = entity.getAcceleration().cpy();
        deltaAcceleration = new Vector2(0, 0);
        deltaAngle = entity.getAngularVelocity();
        deltaAngularVelocity = entity.getAngularAcceleration();
        deltaAngularAcceleration = 0.0f;
    }

    public void addToPosition(Vector2 velocity) {
        deltaPosition.add(velocity);
    }

    public void addToAbsolutePosition(Vector2 deltaAbsolutePosition) {
        this.deltaAbsolutePosition.add(deltaAbsolutePosition);
    }

    public void addToVelocity(Vector2 acceleration) {
        deltaVelocity.add(acceleration);
    }

    public void addToAcceleration(Vector2 acceleration) {
        deltaAcceleration.add(acceleration);
    }
    
    public void addToAngle(float deltaAngle) {
        this.deltaAngle += deltaAngle;
    }

    public void addToAngularVelocity(float deltaAngularVelocity) {
        this.deltaAngularVelocity += deltaAngularVelocity;
    }

    public void addToAngularAcceleration(float deltaAngularAcceleration) {
        this.deltaAngularAcceleration += deltaAngularAcceleration;
    }
    
    public Vector2 getPositionUpdate() {
        return deltaPosition;
    }

    public Vector2 getAbsolutePositionUpdate() {
        return deltaAbsolutePosition;
    }

    public Vector2 getCombinedPositionUpdate(float delta) {
        return deltaPosition.cpy().scl(delta).add(deltaAbsolutePosition);
    }

    public Vector2 getVelocityUpdate() {
        return deltaVelocity;
    }

    public Vector2 getAccelerationUpdate() {
        return deltaAcceleration;
    }

    public float getRotationUpdate() {
        return deltaAngle;
    }

    public float getAngularVelocityUpdate() {
        return deltaAngularVelocity;
    }

    public float getAngularAccelerationUpdate() {
        return deltaAngularAcceleration;
    }

    public void reset() {
        deltaPosition = new Vector2();
        deltaAbsolutePosition = new Vector2();
        deltaVelocity = new Vector2();
        deltaAcceleration = new Vector2();
        deltaAngle = 0.0f;
        deltaAngularVelocity = 0.0f;
        deltaAngularAcceleration = 0.0f;
    }

    public CollisionAccumulator mergeAccumulator(CollisionAccumulator other) {
        CollisionAccumulator merged = new CollisionAccumulator();
        merged.deltaPosition = this.deltaPosition.cpy().add(other.deltaPosition);
        merged.deltaVelocity = this.deltaVelocity.cpy().add(other.deltaVelocity);
        merged.deltaAcceleration = this.deltaAcceleration.cpy().add(other.deltaAcceleration);
        merged.deltaAngle = this.deltaAngle + other.deltaAngle;
        merged.deltaAngularVelocity = this.deltaAngularVelocity + other.deltaAngularVelocity;
        merged.deltaAngularAcceleration = this.deltaAngularAcceleration + other.deltaAngularAcceleration;
        return merged;
    }
}
