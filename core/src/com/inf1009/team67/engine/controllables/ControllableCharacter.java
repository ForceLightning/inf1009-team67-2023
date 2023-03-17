package com.inf1009.team67.engine.controllables;

import java.util.EnumSet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.inf1009.team67.engine.collisionmanagement.CollidableEntity;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatAccumulator;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatBehaviour;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatState;

public class ControllableCharacter extends CollidableEntity implements ControllableCombatant {
    private BasicCombatBehaviour combatBehaviour;
    private EnumSet<BasicCombatState> combatStates;
    private float health;
    private float maxHealth;
    private float hurtThreshold;
    private float attackSpeed;
    private float attackDamage;
    private float aggroRange;
    private float attackRange;
    private long lastAttackTimeMillis;
    private ControllableCharacter target;
    private BasicCombatAccumulator combatAccumulator;
    private boolean isPlayer;
    private float maxHealthModifier = 1f;

    public ControllableCharacter(boolean isPlayer) {
        super();
        this.combatBehaviour = BasicCombatBehaviour.IDLE;
        this.combatStates = EnumSet.noneOf(BasicCombatState.class);
        this.combatAccumulator = new BasicCombatAccumulator();
        this.health = 100;
        this.maxHealth = 100;
        this.hurtThreshold = 0.5f;
        this.attackSpeed = 1;
        this.attackDamage = 10;
        this.aggroRange = 200;
        this.attackRange = 200;
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
        setVelocity(getVelocity().add(combatAccumulator.getDeltaPositionRelMoveSpeed().scl(getBaseMovementSpeed()).scl(delta)));
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
        this.health = health;
    }

    public void modifyHealth(float health) {
        this.health = Math.min(this.getMaxHealth(), health);
        if (this.combatStates.contains(BasicCombatState.KILLED)) {
            this.combatStates = EnumSet.of(BasicCombatState.DEAD);
        }
        if (this.health <= 0) {
            this.health = 0;
            this.combatBehaviour = BasicCombatBehaviour.KILLED;
            this.combatStates = EnumSet.of(BasicCombatState.KILLED);
            this.target = null;
        } else if (this.health <= (this.getMaxHealth() * this.hurtThreshold)) {
            this.combatStates.add(BasicCombatState.HURT);
        } else {
            this.combatStates.remove(BasicCombatState.HURT);
        }
    }

    public void hurtCheck() {
        if (this.health <= (this.getMaxHealth() * this.hurtThreshold)) {
            this.combatStates.add(BasicCombatState.HURT);
            this.combatBehaviour = BasicCombatBehaviour.FLEE;
        } else {
            this.combatStates.remove(BasicCombatState.HURT);
        }
    }

    public void addDamage(float damage) {
        this.modifyHealth(this.getHealth() - damage);
    }

    public void addHealth(float health) {
        this.modifyHealth(this.getHealth() + health);
    }

    public float getMaxHealth() {
        return maxHealth * maxHealthModifier;
    }

    public float getMaxHealthLimit() {
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
        if (this.combatBehaviour != BasicCombatBehaviour.DEAD && this.combatBehaviour != BasicCombatBehaviour.KILLED) {
            updateBehaviour(combatBehaviour);
            hurtCheck();
            applyFromCombatAccumulator(delta);
        }
        super.update(delta);
        if (this.combatBehaviour == BasicCombatBehaviour.IDLE) {
            this.target = null;
        }
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
        if (target == null) {
            shapes.circle(getCentreX(), getCentreY(), getAggroRange());
        } else {
            if (this.isPlayer) {
                shapes.rectLine(getCentreX(), getCentreY(), target.getCentreX(), target.getCentreY(), 2f);
            } else {
                shapes.line(getCentreX(), getCentreY(), target.getCentreX(), target.getCentreY());
            }
        }
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

    public float getAttackRange() {
        return attackRange;
    }

    public float getAttackRange2() {
        return attackRange * attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public float getMaxHealthModifier() {
        return maxHealthModifier;
    }

    public void setMaxHealthModifier(float maxHealthModifier) {
        this.maxHealthModifier = maxHealthModifier;
    }

}
