package com.inf1009.team67.game.InputBehaviourManagement.BasicCombat;

import com.badlogic.gdx.math.Vector2;

public class BasicCombatAccumulator {
    private Vector2 deltaPosition;
    private Vector2 deltaPositionRelMoveSpeed;
    private float deltaHealth;

    public BasicCombatAccumulator() {
        this.deltaPosition = new Vector2(0, 0);
        this.deltaPositionRelMoveSpeed = new Vector2(0, 0);
        this.deltaHealth = 0;
    }

    public void addToPosition(Vector2 deltaPosition) {
        this.deltaPosition.add(deltaPosition);
    }

    public void addToHealth(float deltaHealth) {
        this.deltaHealth += deltaHealth;
    }

    public Vector2 getDeltaPosition() {
        return this.deltaPosition;
    }

    public float getDeltaHealth() {
        return this.deltaHealth;
    }

    public void reset() {
        this.deltaPosition = new Vector2(0, 0);
        this.deltaHealth = 0;
    }

    public void setDeltaPosition(Vector2 deltaPosition) {
        this.deltaPosition = deltaPosition;
    }

    public void addToPositionRelMoveSpeed(Vector2 deltaPositionRelMoveSpeed) {
        this.deltaPositionRelMoveSpeed.add(deltaPositionRelMoveSpeed);
    }

    public Vector2 getDeltaPositionRelMoveSpeed() {
        return deltaPositionRelMoveSpeed;
    }

    public void setDeltaPositionRelMoveSpeed(Vector2 deltaPositionRelMoveSpeed) {
        this.deltaPositionRelMoveSpeed = deltaPositionRelMoveSpeed;
    }

    public void setDeltaHealth(float deltaHealth) {
        this.deltaHealth = deltaHealth;
    }

}
