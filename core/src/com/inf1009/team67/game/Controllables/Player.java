package com.inf1009.team67.game.Controllables;

import java.util.EnumSet;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.game.InputBehaviourManagement.Inputs;
import com.inf1009.team67.game.InputBehaviourManagement.StateManager;
import com.inf1009.team67.game.InputBehaviourManagement.BasicCombat.BasicCombatState;

public class Player extends ControllableCharacter {
    private StateManager stateManager;
    // Constructor
    public Player() {
        super(true);
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
        if (getCombatStates().contains(BasicCombatState.DEAD)) {
            return;
        }
        EnumSet<Inputs> movementInputs = this.stateManager.getMovementInputs();
        EnumSet<Inputs> combatInputs = this.stateManager.getCombatInputs();
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
        for (Inputs input : combatInputs) {
            switch (input) {
                case ATTACK:
                    this.addCombatState(BasicCombatState.ATTACKING);
                    break;
                default:
                    this.removeCombatState(BasicCombatState.ATTACKING);
                    break;
            }
        }
    }
}