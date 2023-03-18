package com.inf1009.team67.game.controllables;

import com.inf1009.team67.engine.controllables.ControllableCharacter;

public class HostileEntity extends ControllableCharacter {
    public HostileEntity() {
        super();
        this.setTexture("textures/circle.png");
        this.setSize(100, 100);
        this.setBaseMovementSpeed(20);
        this.setAggroRange(1000);
        this.setAttackRange(100);
    }
}
