package com.inf1009.team67.game.CollisionManagement;

import com.badlogic.gdx.math.Circle;
import com.inf1009.team67.game.EntityManagement.Accumulator;

public interface DynamicBody<T> {
    public void applyFromAccumulator();
    public void setAccumulator(Accumulator accumulator);
    public void resetAccumulator();
    public Accumulator getAccumulator();
    public boolean isCollidingWith(DynamicBody<T> other);
    public void handleCollision(DynamicBody<T> other);
    public T getEntity();
    public Circle getHitBox();
}
