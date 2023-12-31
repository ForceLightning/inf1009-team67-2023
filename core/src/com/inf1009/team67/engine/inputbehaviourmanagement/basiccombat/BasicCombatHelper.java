package com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.engine.controllables.ControllableCharacter;
import com.inf1009.team67.engine.entitymanagement.EntityBase;
import com.inf1009.team67.engine.helpers.HandleEnum;
import com.inf1009.team67.engine.helpers.HelperBase;
import com.inf1009.team67.game.main.MyGdxGame;
import com.inf1009.team67.game.scenes.GameScreen;

public class BasicCombatHelper extends HelperBase<ControllableCharacter> {
    private MyGdxGame game;
    private GameScreen gameScreen;

    public BasicCombatHelper(MyGdxGame game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.setHandleEnum(HandleEnum.COMBAT);
    }

    public void setInAggroRange(ControllableCharacter combatant, ControllableCharacter other) {
        if (inAggroRange(combatant, other)){
            combatant.getCombatStates().add(BasicCombatState.IN_AGGRO_RANGE);
        } else {
            combatant.getCombatStates().remove(BasicCombatState.IN_AGGRO_RANGE);
        }
    }

    public void setInAttackRange(ControllableCharacter combatant, ControllableCharacter other) {
        if (inAttackRange(combatant, other)){
            combatant.getCombatStates().add(BasicCombatState.IN_ATTACK_RANGE);
        } else {
            combatant.getCombatStates().remove(BasicCombatState.IN_ATTACK_RANGE);
        }
    }

    public static boolean inAggroRange(ControllableCharacter combatant, ControllableCharacter other) {
        return combatant.getPosition().dst2(other.getPosition()) <= combatant.getAggroRange2();
    }

    public static boolean inAttackRange(ControllableCharacter combatant, ControllableCharacter other) {
        return combatant.getPosition().dst2(other.getPosition()) <= combatant.getAttackRange2();
    }

    public void handleAttack(ControllableCharacter combatant, ControllableCharacter other) {
        if (combatant.getCombatStates().contains(BasicCombatState.ATTACKING) && combatant.getTarget() == other) {
            if (combatant.getAttackTimer() >= 0) {
                combatant.resetAttackTimer();
                other.getCombatAccumulator().addToHealth(-1 * combatant.getDamage());
            }
        }
    }

    public void handleFlee(ControllableCharacter combatant, ControllableCharacter other) {
        if (combatant.getCombatStates().contains(BasicCombatState.HURT) && combatant.getCombatStates().contains(BasicCombatState.IN_AGGRO_RANGE)) {
            combatant.setTarget(other);
        }
    }

    public void handleChase(ControllableCharacter combatant, ControllableCharacter other) {
        if (inAggroRange(combatant, other)) {
            boolean flee = combatant.getCombatBehaviour() == BasicCombatBehaviour.FLEE;
            Vector2 chaseVector = new Vector2(
                other.getCentreX() - combatant.getCentreX(),
                other.getCentreY() - combatant.getCentreY()
            ).nor();
            if (flee) {
                chaseVector.rotateDeg(180);
            }
            combatant.getCombatAccumulator().addToPositionRelMoveSpeed(chaseVector);
        }
    }

    public void setAggro(ControllableCharacter combatant, ControllableCharacter other) {
        boolean inAttackRange = inAttackRange(combatant, other);
        if (combatant.getTarget() == null && (inAttackRange || combatant.getCombatStates().contains(BasicCombatState.IN_ATTACK_RANGE))) {
            combatant.setTarget(other);
            if (!combatant.getCombatStates().contains(BasicCombatState.HURT)) {
                combatant.getCombatStates().add(BasicCombatState.ATTACKING);
                combatant.setCombatBehaviour(BasicCombatBehaviour.ATTACK);
            }
        } else if (!inAttackRange(combatant, other) && combatant.getTarget() == other) {
            combatant.setTarget(null);
            combatant.getCombatStates().remove(BasicCombatState.ATTACKING);
        }
    }

    public void setTarget(ControllableCharacter combatant, ControllableCharacter other) {
        boolean inRange = inAggroRange(combatant, other);
        if (combatant.getTarget() == null && inRange) {
            combatant.setTarget(other);
        } else if ((!inAggroRange(combatant, other) && combatant.getTarget() == other) || other.getCombatBehaviour() == BasicCombatBehaviour.DEAD || other.getCombatBehaviour() == BasicCombatBehaviour.KILLED) {
            combatant.setTarget(null);
        }
    }

    @Override
    public <T extends ControllableCharacter, V extends ControllableCharacter> void handleAbstractInteraction(T a, V b) {
        if (b.isPlayer()) {
            setInAggroRange(a, b);
            setAggro(a, b);
            handleAttack(a, b);
            handleChase(a, b);
        } else if (a.isPlayer()) {
            setInAggroRange(b, a);
            setAggro(b, a);
            handleAttack(b, a);
            handleChase(b, a);
            if(a.getCombatStates().contains(BasicCombatState.ATTACKING)) {
                setTarget(a, b);
                if (a.getTarget() == b) {
                    handleAttack(a, b);
                }
            }
        }
    }

    @Override
    public void preLoop(TreeMap<Integer, ArrayList<EntityBase>> entityCollection, float delta) {
        // reset all combat accumulators
        for (Integer Z : entityCollection.keySet()) {
            for (EntityBase entity : entityCollection.get(Z)) {
                if (entity instanceof ControllableCharacter) {
                    ControllableCharacter combatant = (ControllableCharacter) entity;
                    if (combatant.getCombatBehaviour() == BasicCombatBehaviour.KILLED && !combatant.isPlayer()) {
                        game.setScore(game.getScore() + 10 * (gameScreen.getDifficulty() + 1));
                        combatant.setCombatBehaviour(BasicCombatBehaviour.DEAD);
                        combatant.setAlpha(0.25f);
                    }
                    combatant.getCombatAccumulator().reset();
                }
            }
        }
    }

    @Override
    public boolean conditionCheck(ControllableCharacter entity, ControllableCharacter other) {
        return entity != other && entity.getCombatBehaviour() != BasicCombatBehaviour.DEAD && other.getCombatBehaviour() != BasicCombatBehaviour.DEAD && entity.getCombatBehaviour() != BasicCombatBehaviour.KILLED && other.getCombatBehaviour() != BasicCombatBehaviour.KILLED;
    }
}
