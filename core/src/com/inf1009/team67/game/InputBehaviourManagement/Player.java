package com.inf1009.team67.game.InputBehaviourManagement;

import java.util.EnumSet;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.game.CollisionManagement.CollidableEntity;

public class Player extends CollidableEntity {
    private StateManager stateManager;
    // Constructor
    public Player() {
        super();
        this.setTexture("textures/circle.png");
        this.setSize(100, 100);
        this.stateManager = new StateManager();
        this.setBaseMovementSpeed(100);
    }

    public void update(float delta) {
        this.stateManager.handleInput();
        handleMovementInput();
        super.update(delta);
        // this.applyFromAccumulator(delta);
    }

    public void handleMovementInput() {
        EnumSet<Inputs> movementInputs = this.stateManager.getMovementInputs();
        Vector2 deltaPosition = new Vector2(0, 0);
        for (Inputs input : movementInputs) {
            switch (input) {
                case UP:
                    deltaPosition.y += 1.0f;
                    break;
                case DOWN:
                    deltaPosition.y -= 1.0f;
                    break;
                case LEFT:
                    deltaPosition.x -= 1.0f;
                    break;
                case RIGHT:
                    deltaPosition.x += 1.0f;
                    break;
                default:
                    break;
            }
        }
        deltaPosition.nor().scl(getBaseMovementSpeed());
        // this.getAccumulator().addToPosition(deltaPosition);
        this.setVelocity(deltaPosition);
    }
}