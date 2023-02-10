package com.inf1009.team67.game.InputBehaviourManagement;

import java.util.EnumSet;
import java.util.HashMap;

import com.inf1009.team67.game.Util.Pair;

public class StateMap {
    private static EnumSet<State> movementStates = EnumSet.of(State.NONE_MOVEMENT, State.MOVING);
    private static EnumSet<State> combatStates = EnumSet.of(State.NONE_COMBAT, State.ATTACKING);
    private static EnumSet<State> jumpStates = EnumSet.of(State.NONE_JUMP, State.GROUNDED, State.JUMPING, State.AIRBORNE, State.COYOTE_TIME);
    private static EnumSet<Inputs> movementInputs = EnumSet.of(Inputs.UP, Inputs.DOWN, Inputs.LEFT, Inputs.RIGHT, Inputs.JUMP);
    private static EnumSet<Inputs> combatInputs = EnumSet.of(Inputs.ATTACK, Inputs.INTERACT);
    private static EnumSet<Inputs> jumpInputs = EnumSet.of(Inputs.JUMP);
    private HashMap<Pair<Inputs, State>, State> stateMap = new HashMap<Pair<Inputs, State>, State>();
    public StateMap() {
        // set all mappings to NONE
        for (State state : State.values()) {
            for (Inputs input : Inputs.values()) {
                stateMap.put(new Pair<Inputs, State>(input, state), State.NONE);
            }
        }
        // initialise default mappings
        // NONE_MOVEMENT(movementInputs) -> MOVING
        associateInput(movementInputs, State.NONE_MOVEMENT, State.MOVING);
        // MOVING(movementInputs) -> MOVING
        associateInput(movementInputs, State.MOVING, State.MOVING);
        // MOVING(NONE_MOVEMENT) -> NONE_MOVEMENT
        associateInput(Inputs.NONE_MOVEMENT, State.MOVING, State.NONE_MOVEMENT);
        // NONE_COMBAT(combatInputs) -> ATTACKING
        associateInput(combatInputs, State.NONE_COMBAT, State.ATTACKING);
        // ATTACKING(combatInputs) -> ATTACKING
        associateInput(combatInputs, State.ATTACKING, State.ATTACKING);
        // ATTACKING(NONE_COMBAT) -> NONE_COMBAT
        associateInput(Inputs.NONE_COMBAT, State.ATTACKING, State.NONE_COMBAT);
        // GROUNDED or COYOTE_TIME(jumpInputs) -> JUMPING
        associateInput(jumpInputs, State.GROUNDED, State.JUMPING);
        associateInput(jumpInputs, State.COYOTE_TIME, State.JUMPING);
    }

    public void associateInput(Inputs input, State currentState, State nextState) {
        associateInput(input, currentState, nextState, false);
    }

    public void associateInput(Inputs input, State currentState, State nextState, boolean bidirectional) {
        stateMap.put(new Pair<Inputs, State>(input, currentState), nextState);
        if (bidirectional) {
            stateMap.put(new Pair<Inputs, State>(input, nextState), currentState);
        }
    }

    public void associateInput(EnumSet<Inputs> inputSet, State currentState, State nextState) {
        associateInput(inputSet, currentState, nextState, false);
    }
    public void associateInput(EnumSet<Inputs> inputSet, State currentState, State nextState, boolean bidirectional) {
        for (Inputs input : inputSet) {
            stateMap.put(new Pair<Inputs, State>(input, currentState), nextState);
        }
        if (bidirectional) {
            for (Inputs input : inputSet) {
                stateMap.put(new Pair<Inputs, State>(input, nextState), currentState);
            }
        }
    }
    public State getState(Inputs input, State currentState) {
        return stateMap.get(new Pair<Inputs, State>(input, currentState));
    }

    public EnumSet<State> getStates(EnumSet<Inputs> inputs, EnumSet<State> currentStates) {
        EnumSet<State> nextStates = EnumSet.noneOf(State.class);
        for (State currentState : currentStates) {
            for (Inputs input : inputs) {
                nextStates.add(getState(input, currentState));
            }
        }
        return nextStates;
    }

    public EnumSet<State> getMovementStates() {
        return movementStates;
    }

    public EnumSet<State> getCombatStates() {
        return combatStates;
    }

    public EnumSet<State> getJumpStates() {
        return jumpStates;
    }

    public EnumSet<Inputs> getMovementInputs() {
        return movementInputs;
    }
    public EnumSet<Inputs> getCombatInputs() {
        return combatInputs;
    }
    public EnumSet<Inputs> getJumpInputs() {
        return jumpInputs;
    }

}
