package com.inf1009.team67.engine.controllables;

import java.util.EnumSet;

import com.badlogic.gdx.math.Vector2;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatAccumulator;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatBehaviour;
import com.inf1009.team67.engine.inputbehaviourmanagement.basiccombat.BasicCombatState;

public interface ControllableCombatant {
    public void updateBehaviour(BasicCombatBehaviour behaviour);
    public BasicCombatBehaviour getCombatBehaviour();
    public EnumSet<BasicCombatState> getCombatStates();
    public void setCombatBehaviour(BasicCombatBehaviour behaviour);
    public void setCombatStates(EnumSet<BasicCombatState> states);
    public boolean inAggroRange(Vector2 otherPos);
    public void actCombat();
    public BasicCombatAccumulator getCombatAccumulator();
    public void resetCombatAccumulator();
    public void applyFromCombatAccumulator(float delta);
    public void setTarget(ControllableCharacter other);
    public ControllableCharacter getTarget();
}
