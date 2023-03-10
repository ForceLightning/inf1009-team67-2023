package com.inf1009.team67.game.controllables;

import com.inf1009.team67.engine.controllables.ControllableCharacter;

public class TestEntity extends ControllableCharacter {
    public TestEntity() {
        super();
        this.setTexture("textures/circle.png");
        this.setSize(100, 100);
        this.setBaseMovementSpeed(20);
    }
}
