package com.inf1009.team67.game.InputBehaviourManagement.BasicCombat;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.game.Controllables.ControllableCharacter;
import com.inf1009.team67.game.EntityManagement.EntityBase;

public class BasicCombatHelper {

    public void setInRange(ControllableCharacter combatant, ControllableCharacter other) {
        if (inRange(combatant, other)){
            combatant.getCombatStates().add(BasicCombatState.IN_RANGE);
        } else {
            combatant.getCombatStates().remove(BasicCombatState.IN_RANGE);
        }
    }

    public boolean inRange(ControllableCharacter combatant, ControllableCharacter other) {
        return combatant.getPosition().dst2(other.getPosition()) <= combatant.getAggroRange2();
    }

    public void handleAttack(ControllableCharacter combatant, ControllableCharacter other) {
        if (combatant.getCombatStates().contains(BasicCombatState.ATTACKING) && combatant.getTarget() == other) {
            // System.out.println(combatant.toString() + "Attack timer: " + combatant.getAttackTimer());
            if (combatant.getAttackTimer() >= 0) {
                combatant.resetAttackTimer();
                other.getCombatAccumulator().addToHealth(-1 * combatant.getDamage());
                // System.out.println(combatant.toString() + " attacked " + other.toString() + " for " + combatant.getDamage() + " damage.");
            }
        }
    }

    public void handleFlee(ControllableCharacter combatant, ControllableCharacter other) {
        if (combatant.getCombatStates().contains(BasicCombatState.HURT) && combatant.getCombatStates().contains(BasicCombatState.IN_RANGE)) {
            combatant.setTarget(other);
        }
    }

    public void handleChase(ControllableCharacter combatant, ControllableCharacter other) {
        if (inRange(combatant, other)) {
            float chase = inRange(combatant, other) ? 1 : -1;
            Vector2 chaseVector = new Vector2(
                other.getCentreX() - combatant.getCentreX(),
                other.getCentreY() - combatant.getCentreY()
            ).nor().scl(chase);
            combatant.getCombatAccumulator().addToPositionRelMoveSpeed(chaseVector);
        }
    }

    public void setAggro(ControllableCharacter combatant, ControllableCharacter other) {
        boolean inRange = inRange(combatant, other);
        if (combatant.getTarget() == null && (inRange || combatant.getCombatStates().contains(BasicCombatState.IN_RANGE))) {
            combatant.setTarget(other);
            if (!combatant.getCombatStates().contains(BasicCombatState.HURT)) {
                combatant.getCombatStates().add(BasicCombatState.ATTACKING);
                combatant.setCombatBehaviour(BasicCombatBehaviour.ATTACK);
            }
        } else if (!inRange(combatant, other) && combatant.getTarget() == other) {
            combatant.setTarget(null);
            combatant.getCombatStates().remove(BasicCombatState.ATTACKING);
        }
    }

    public void setTarget(ControllableCharacter combatant, ControllableCharacter other) {
        boolean inRange = inRange(combatant, other);
        if (combatant.getTarget() == null && inRange) {
            combatant.setTarget(other);
        } else if ((!inRange(combatant, other) && combatant.getTarget() == other) || other.getCombatBehaviour() == BasicCombatBehaviour.DEAD) {
            combatant.setTarget(null);
        }
    }

    public void updateCombatStates(TreeMap<Integer, ArrayList<EntityBase>> entityCollection) {
        // reset all combat accumulators
        for (Integer Z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(Z)) {
                if (entity instanceof ControllableCharacter) {
                    ControllableCharacter combatant = (ControllableCharacter) entity;
                    combatant.getCombatAccumulator().reset();
                }
            }
        }
        // update combat states
        for (Integer Z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(Z)) {
                if (entity instanceof ControllableCharacter) {
                    ControllableCharacter combatant = (ControllableCharacter) entity;
                    for (EntityBase otherEntity: entityCollection.get(Z)) {
                        if (otherEntity instanceof ControllableCharacter) {
                            ControllableCharacter other = (ControllableCharacter) otherEntity;
                            if (other != combatant && combatant.getCombatBehaviour() != BasicCombatBehaviour.DEAD && other.getCombatBehaviour() != BasicCombatBehaviour.DEAD) {
                                if (other.isPlayer()) {
                                    setInRange(combatant, other);
                                    setAggro(combatant, other);
                                    handleAttack(combatant, other);
                                    handleChase(combatant, other);
                                }
                                if (combatant.isPlayer() && combatant.getCombatStates().contains(BasicCombatState.ATTACKING)) {
                                    setTarget(combatant, other);
                                    if (combatant.getTarget() == other) {
                                        handleAttack(combatant, other);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}