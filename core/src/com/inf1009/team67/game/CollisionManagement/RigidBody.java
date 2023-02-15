package com.inf1009.team67.game.CollisionManagement;

import com.badlogic.gdx.math.Circle;

public interface RigidBody<T> {
    public void applyFromAccumulator(float delta);
    public void setAccumulator(CollisionAccumulator accumulator);
    public void resetAccumulator();
    public CollisionAccumulator getAccumulator();
    public <V extends RigidBody<?>> boolean isCollidingWith(V other);
    public <V extends RigidBody<?>> void handleCollision(V other);
    public T getEntity();
    public Circle getHitBox();
}
