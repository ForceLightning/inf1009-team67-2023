package com.inf1009.team67.game.controllables;

import java.util.EnumSet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.inf1009.team67.engine.controllables.ControllableCharacter;
import com.inf1009.team67.engine.helpers.HandleEnum;
import com.inf1009.team67.engine.inputbehaviourmanagement.Inputs;
import com.inf1009.team67.engine.inputbehaviourmanagement.StateManager;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatBehaviour;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatState;
import com.inf1009.team67.engine.interactionmanagement.Interactable;

public class Player extends ControllableCharacter implements Interactable {
    private StateManager stateManager;
    private Circle interactionCircle;
    private boolean isInteractable = true;
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
        this.interactionCircle = new Circle(this.getCentreX(), this.getCentreY(), this.getWidth() / 2);
        this.addRequiredHandle(HandleEnum.INTERACTION);
    }

    public void update(float delta) {
        // super.update();
        this.stateManager.handleInput();
        handleMovementInput();
        super.update(delta);
        this.interactionCircle.setPosition(this.getCentreX(), this.getCentreY());
        // this.applyFromAccumulator(delta);
    }

    @Override
    public void scaleFromCentre(float scale) {
        super.scaleFromCentre(scale);
        this.interactionCircle.setRadius(this.interactionCircle.radius * scale);
        this.interactionCircle.setPosition(this.getCentreX(), this.getCentreY());
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
        deltaPosition.nor().scl(getMovementSpeed());
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
        Color previousColor = shapes.getColor();
        shapes.setColor(Color.RED);
        shapes.circle(interactionCircle.x, interactionCircle.y, interactionCircle.radius);
        shapes.setColor(previousColor);
    }

    @Override
    public void handleInteraction(Interactable other) {
        return;
    }

    @Override
    public boolean isInteractingWith(Interactable other) {
        if (this.interactionCircle.overlaps(other.getInteractionCircle()) && this.isInteractable() && other.isInteractable()) {
            return true;
        }
        return false;
    }

    @Override
    public Circle getInteractionCircle() {
        return this.interactionCircle;
    }

    @Override
    public void addReaction(Interactable other, Action reaction) {
        return;
    }

    @Override
    public void setInteractionCircle(Circle circle) {
        this.interactionCircle = circle;
    }

    @Override
    public void setInteractionCircle(float x, float y, float radius) {
        this.interactionCircle = new Circle(x, y, radius);
    }

    @Override
    public void setInteractionCircle(float x, float y) {
        this.interactionCircle = new Circle(x, y, this.getWidth() / 2);
    }

    @Override
    public void setInteractionCircle(float radius) {
        this.interactionCircle = new Circle(this.getX(), this.getY(), radius);
    }

    @Override
    public void setInteractable(boolean isInteractable) {
        this.isInteractable = isInteractable;
    }

    @Override
    public boolean isInteractable() {
        return this.isInteractable;
    }
}