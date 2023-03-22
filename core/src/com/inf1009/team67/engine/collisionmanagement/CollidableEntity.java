package com.inf1009.team67.engine.collisionmanagement;

import com.badlogic.gdx.graphics.Color;

public class CollidableEntity extends DynamicBody {
    private Color nonColidingColor;
    private Color collidingColor;

    public CollidableEntity(Color colidingColor) {
        super();
        nonColidingColor = getColor().cpy();
        this.collidingColor = colidingColor;
    }

    public CollidableEntity() {
        super();
        this.nonColidingColor = getColor().cpy();
        this.collidingColor = Color.PINK;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setColor(nonColidingColor);
    }

    @Override
    public <V extends RigidBody<?>> void handleCollision(V other) {
        super.handleCollision(other);
        setColor(collidingColor);
    }
}
