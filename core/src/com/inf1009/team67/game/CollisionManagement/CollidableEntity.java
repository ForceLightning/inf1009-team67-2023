package com.inf1009.team67.game.CollisionManagement;

import com.badlogic.gdx.graphics.Color;

public class CollidableEntity extends DynamicBody {
    private Color nonColidingColor;
    private Color colidingColor;

    public CollidableEntity(Color colidingColor) {
        super();
        nonColidingColor = getColor().cpy();
        this.colidingColor = colidingColor;
    }

    public CollidableEntity() {
        super();
        this.nonColidingColor = getColor().cpy();
        this.colidingColor = Color.PINK;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        setColor(nonColidingColor);
    }

    @Override
    public <V extends RigidBody<?>> void handleCollision(V other) {
        super.handleCollision(other);
        setColor(colidingColor);
    }

}
