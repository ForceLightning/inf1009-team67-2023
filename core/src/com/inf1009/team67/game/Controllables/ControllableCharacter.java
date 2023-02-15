package com.inf1009.team67.game.Controllables;

import java.util.EnumSet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.inf1009.team67.game.CollisionManagement.CollidableEntity;
import com.inf1009.team67.game.InputBehaviourManagement.BasicCombat.BasicCombatAccumulator;
import com.inf1009.team67.game.InputBehaviourManagement.BasicCombat.BasicCombatBehaviour;
import com.inf1009.team67.game.InputBehaviourManagement.BasicCombat.BasicCombatState;

public class ControllableCharacter extends CollidableEntity implements ControllableCombatant {
    private BasicCombatBehaviour combatBehaviour;
    private EnumSet<BasicCombatState> combatStates;
    private float health;
    private float maxHealth;
    private float hurtThreshold;
    private float attackSpeed;
    private float attackDamage;
    private float aggroRange;
    private long lastAttackTimeMillis;
    private ControllableCharacter target;
    private BasicCombatAccumulator combatAccumulator;
    private boolean isPlayer;

    public ControllableCharacter(boolean isPlayer) {
        super();
        this.combatBehaviour = BasicCombatBehaviour.IDLE;
        this.combatStates = EnumSet.noneOf(BasicCombatState.class);
        this.combatAccumulator = new BasicCombatAccumulator();
        this.health = 100;
        this.maxHealth = 100;
        this.hurtThreshold = 0.1f;
        this.attackSpeed = 1;
        this.attackDamage = 10;
        this.aggroRange = 200;
        this.lastAttackTimeMillis = 0;
        this.target = null;
        this.isPlayer = isPlayer;
    }

    public ControllableCharacter() {
        this(false);
    }

    @Override
    public void updateBehaviour(BasicCombatBehaviour behaviour) {
        this.combatBehaviour = this.combatBehaviour.updateState(this.combatBehaviour, combatStates);
    }

    @Override
    public BasicCombatBehaviour getCombatBehaviour() {
        return this.combatBehaviour;
    }

    @Override
    public EnumSet<BasicCombatState> getCombatStates() {
        return this.combatStates;
    }

    @Override
    public void setCombatBehaviour(BasicCombatBehaviour behaviour) {
        this.combatBehaviour = behaviour;
    }

    @Override
    public void setCombatStates(EnumSet<BasicCombatState> states) {
        this.combatStates = states;
    }

    @Override
    public boolean inAggroRange(Vector2 otherPos) {
        return getPosition().dst2(otherPos) <= aggroRange * aggroRange;
    }

    @Override
    public void actCombat() {

    }

    public float getDamage() {
        return attackDamage;
    }

    public float getAggroRange() {
        return aggroRange;
    }

    public float getAggroRange2() {
        return aggroRange * aggroRange;
    }

    public long getAttackTimer() {
        return TimeUtils.timeSinceMillis(lastAttackTimeMillis) - (long) (1000 / attackSpeed);
    }

    public void resetAttackTimer() {
        this.lastAttackTimeMillis = TimeUtils.millis();
    }

    @Override
    public BasicCombatAccumulator getCombatAccumulator() {
        return this.combatAccumulator;
    }

    @Override
    public void resetCombatAccumulator() {
        this.combatAccumulator.reset();
    }

    @Override
    public void applyFromCombatAccumulator(float delta) {
        setPosition(getPosition().add(combatAccumulator.getDeltaPosition().scl(delta)));
        setPosition(getPosition().add(combatAccumulator.getDeltaPositionRelMoveSpeed().scl(getBaseMovementSpeed()).scl(delta)));
        addHealth(combatAccumulator.getDeltaHealth());
        // if (combatAccumulator.getDeltaHealth() < 0) {
        //     System.out.println("Health: " + getHealth() + ", Delta Health: " + combatAccumulator.getDeltaHealth());
        // }
    }

    @Override
    public void setTarget(ControllableCharacter other) {
        this.target = other;
    }

    @Override
    public ControllableCharacter getTarget() {
        return this.target;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = Math.min(this.health, health);
        if (this.health <= 0) {
            this.health = 0;
            this.combatBehaviour = BasicCombatBehaviour.DEAD;
            this.combatStates = EnumSet.of(BasicCombatState.DEAD);
        } else if (this.health <= this.maxHealth * this.hurtThreshold) {
            this.combatStates.add(BasicCombatState.HURT);
        } else {
            this.combatStates.remove(BasicCombatState.HURT);
        }
    }

    public void addDamage(float damage) {
        this.setHealth(this.getHealth() - damage);
    }

    public void addHealth(float health) {
        this.setHealth(this.getHealth() + health);
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public float getHurtThreshold() {
        return hurtThreshold;
    }

    public void setHurtThreshold(float hurtThreshold) {
        this.hurtThreshold = hurtThreshold;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setAggroRange(float aggroRange) {
        this.aggroRange = aggroRange;
    }

    public long getLastAttackTimeMillis() {
        return lastAttackTimeMillis;
    }

    public void setLastAttackTimeMillis(long lastAttackTimeMillis) {
        this.lastAttackTimeMillis = lastAttackTimeMillis;
    }

    public void setCombatAccumulator(BasicCombatAccumulator combatAccumulator) {
        this.combatAccumulator = combatAccumulator;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    @Override
    public void update(float delta) {
        updateBehaviour(combatBehaviour);
        applyFromCombatAccumulator(delta);
        super.update(delta);
    }
    
    public void addCombatState(BasicCombatState state) {
        this.combatStates.add(state);
    }

    public void removeCombatState(BasicCombatState state) {
        this.combatStates.remove(state);
    }

    public void clearCombatStates() {
        this.combatStates = EnumSet.noneOf(BasicCombatState.class);
    }

    public void setCombatStates(BasicCombatState... states) {
        this.combatStates = EnumSet.of(states[0], states);
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);
        Color oldColor = shapes.getColor();
        if (shapes.isDrawing() && shapes.getCurrentType() != ShapeType.Filled) {
            shapes.set(ShapeType.Filled);
        }
        float healthPercent = getHealth() / getMaxHealth();
        Color healthColor = new Color(1 - healthPercent, healthPercent, 0, 0.5f);
        shapes.setColor(healthColor);
        shapes.circle(getCentreX(), getCentreY(), getHitBox().radius);
        shapes.set(ShapeType.Line);
        shapes.setColor(oldColor);
        if (!isPlayer) {
            if (target == null) {
                shapes.circle(getCentreX(), getCentreY(), getAggroRange());
            } else {
                shapes.line(getCentreX(), getCentreY(), target.getCentreX(), target.getCentreY());
            }
        }
    }
}
