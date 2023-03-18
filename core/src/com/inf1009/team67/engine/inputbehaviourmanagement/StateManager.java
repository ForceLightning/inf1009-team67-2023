package com.inf1009.team67.engine.inputbehaviourmanagement;

import java.util.EnumSet;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class StateManager {
    private EnumSet<State> states;
    private StateMap stateMap;
    private HashMap<Integer, Inputs> inputMap;
    private EnumSet<Inputs> activeInputs;

    public StateManager() {
        this(Keys.W, Keys.S, Keys.A, Keys.D, Keys.B, Keys.SPACE, Keys.F, Keys.CONTROL_LEFT);
    }

    public StateManager(Integer forward, Integer backward, Integer left, Integer right, Integer jump, Integer attack, Integer interact, Integer crouch) {
        this.states = EnumSet.of(State.NONE);
        this.stateMap = new StateMap();
        this.inputMap = new HashMap<Integer, Inputs>();
        this.activeInputs = EnumSet.noneOf(Inputs.class);
        associateKey(forward, Inputs.UP);
        associateKey(backward, Inputs.DOWN);
        associateKey(left, Inputs.LEFT);
        associateKey(right, Inputs.RIGHT);
        associateKey(jump, Inputs.JUMP);
        associateKey(attack, Inputs.ATTACK);
        associateKey(interact, Inputs.INTERACT);
        associateKey(crouch, Inputs.CROUCH);
    }

    public void handleInput() {
        activeInputs.clear();
        for (Integer keyVal : inputMap.keySet()) {
            if (Gdx.input.isKeyPressed(keyVal)) {
                activeInputs.add(inputMap.get(keyVal));
            }
        }
        this.states = stateMap.getStates(activeInputs, states);
    }

    public void associateKey(Integer keyVal, Inputs input) {
        inputMap.put(keyVal, input);
    }

    public EnumSet<State> getStates() {
        return states;
    }

    public void setStates(EnumSet<State> states) {
        this.states = states;
    }

    public StateMap getStateMap() {
        return stateMap;
    }

    public void setStateMap(StateMap stateMap) {
        this.stateMap = stateMap;
    }

    public HashMap<Integer, Inputs> getInputMap() {
        return inputMap;
    }

    public void setInputMap(HashMap<Integer, Inputs> inputMap) {
        this.inputMap = inputMap;
    }

    public void addState(State state) {
        this.states.add(state);
    }

    public void removeState(State state) {
        this.states.remove(state);
    }

    public boolean hasState(State state) {
        return this.states.contains(state);
    }

    public void clearStates() {
        this.states.clear();
    }

    public EnumSet<Inputs> getActiveInputs() {
        return activeInputs;
    }

    public EnumSet<Inputs> getMovementInputs() {
        return getInputsOfSet(stateMap.getMovementInputs());
    }

    public EnumSet<Inputs> getCombatInputs() {
        return getInputsOfSet(stateMap.getCombatInputs());
    }

    public EnumSet<Inputs> getJumpInputs() {
        return getInputsOfSet(stateMap.getJumpInputs());
    }

    public EnumSet<Inputs> getInputsOfSet(EnumSet<Inputs> inputSet) {
        EnumSet<Inputs> inputs = EnumSet.noneOf(Inputs.class);
        for (Inputs input : activeInputs) {
            if (inputSet.contains(input)) {
                inputs.add(input);
            }
        }
        return inputs;
    }
}
