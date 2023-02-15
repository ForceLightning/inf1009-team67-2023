package com.inf1009.team67.game.Controllables;

import java.util.EnumSet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.game.InputBehaviourManagement.Inputs;
import com.inf1009.team67.game.InputBehaviourManagement.StateManager;
import com.inf1009.team67.game.InputBehaviourManagement.BasicCombat.BasicCombatBehaviour;
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
        Color oldColor = shapes.getColor();
        shapes.setColor(Color.RED);
        // define the hp bar size
        Vector2 hpBarSize = new Vector2(100, 10);
        // get position above player bounds
        // note that x and y are the bottom left corner of the rectangle
        Vector2 hpBarPos = new Vector2(this.getCentreX() - hpBarSize.x / 2, this.getCentreY() + this.getHeight() / 2 + hpBarSize.y / 2);
        // draw hp bar
        shapes.set(ShapeType.Filled);
        shapes.rect(hpBarPos.x, hpBarPos.y, hpBarSize.x, hpBarSize.y);
        shapes.setColor(Color.WHITE);
        shapes.rect(hpBarPos.x, hpBarPos.y, hpBarSize.x * (this.getHealth() / this.getMaxHealth()), hpBarSize.y);
        shapes.set(ShapeType.Line);
        shapes.setColor(oldColor);
    }
}