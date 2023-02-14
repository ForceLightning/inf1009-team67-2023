package com.inf1009.team67.game.EntityManagement;

import com.inf1009.team67.game.CollisionManagement.CollidableEntity;

public class TestEntity extends CollidableEntity {
    public TestEntity() {
        super();
        this.setTexture("textures/circle.png");
        this.setSize(100, 100);
    }
}
