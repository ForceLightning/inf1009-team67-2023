package com.inf1009.team67.game.CollisionManagement;

import com.badlogic.gdx.math.Circle;

public interface DynamicBody<T> {
    public void applyFromAccumulator(float delta);
    public void setAccumulator(Accumulator accumulator);
    public void resetAccumulator();
    public Accumulator getAccumulator();
    public <V extends DynamicBody<?>> boolean isCollidingWith(V other);
    public <V extends DynamicBody<?>> void handleCollision(V other);
    public T getEntity();
    public Circle getHitBox();
}
