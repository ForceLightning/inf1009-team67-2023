package com.inf1009.team67.game.InputBehaviourManagement;

import java.util.EnumSet;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.game.EntityManagement.InteractableEntity;

public class Player extends InteractableEntity {
    private StateManager stateManager;
    // Constructor
    public Player() {
        super();
        this.setTexture("textures/circle.png");
        this.setSize(100, 100);
        this.stateManager = new StateManager();
        this.setBaseMovementSpeed(10);
    }

    public void update(float delta) {
        this.stateManager.handleInput();
        handleMovementInput();
        this.applyFromAccumulator(delta);
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
        deltaPosition.scl(getBaseMovementSpeed());
        this.getAccumulator().addToPosition(deltaPosition);
    }
}