package com.inf1009.team67.game.CollisionManagement;

import com.badlogic.gdx.math.Circle;
import com.inf1009.team67.game.EntityManagement.Accumulator;

public interface DynamicBody {
    public void applyFromAccumulator();
    public void setAccumulator(Accumulator accumulator);
    public void resetAccumulator();
    public Accumulator getAccumulator();
    public boolean isCollidingWith(DynamicBody other);
    public void handleCollision(DynamicBody other);
    public Circle getHitBox();
}
