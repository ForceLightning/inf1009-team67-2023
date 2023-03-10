package com.inf1009.team67.game.controllables;

import java.util.EnumSet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.engine.controllables.ControllableCharacter;
import com.inf1009.team67.engine.inputbehaviourmanagement.Inputs;
import com.inf1009.team67.engine.inputbehaviourmanagement.StateManager;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatBehaviour;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatState;

public class Player extends ControllableCharacter {
    private StateManager stateManager;
    // Constructor
    public Player() {
        super(true);
        this.setTexture("textures/circle.png");
        this.setSize(100, 100);
        this.stateManager = new StateManager();
        this.setBaseMovementSpeed(100);
        this.setAttackSpeed(5);
        this.setMaxHealth(1000f);
        this.setHealth(1000f);
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
        this.setCombatStates(EnumSet.noneOf(BasicCombatState.class));
        this.setCombatBehaviour(BasicCombatBehaviour.IDLE);
        for (Inputs input : combatInputs) {
            switch (input) {
                case ATTACK:
                    this.addCombatState(BasicCombatState.ATTACKING);
                    this.setCombatBehaviour(BasicCombatBehaviour.ATTACK);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
    }
}